package codigocreativo.uy.servidorapp.filtros;

import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import codigocreativo.uy.servidorapp.servicios.FuncionalidadRemote;
import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtTokenFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtTokenFilter.class.getName());
    private static final String SECRET_KEY = System.getenv("SECRET_KEY");

    public static class TokenValidationException extends Exception {
        public TokenValidationException(String message) {
            super(message);
        }
    }

    private static final Set<String> PUBLIC_PATHS = Set.of(
        "/usuarios/login",
        "/usuarios/google-login",
        "/perfiles/listar",
        "/swagger-ui",
        "/usuarios/crear",
        "/menu",
        "/api/login",
        "/api/openapi.json",
        "/api/swagger-ui",
        "/openapi.json",
        "/swagger-ui/index.html"
    );

    @EJB
    private FuncionalidadRemote funcionalidadService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        LOGGER.info("Procesando petición para el path: " + path);

        // Verificar si es un endpoint público
        if (isPublicEndpoint(path)) {
            LOGGER.info("Endpoint público detectado, permitiendo acceso sin autenticación");
            return;
        }

        // Verificar autenticación
        String token = extractAndValidateToken(requestContext);
        if (token == null) {
            return; // La respuesta de error ya fue enviada
        }

        try {
            // Validar token y extraer claims
            Claims claims = validateToken(token);
            if (claims == null) {
                return;
            }

            // Extraer información del token
            String email = claims.get("email", String.class);
            String perfil = claims.get("perfil", String.class);
            LOGGER.info("Token válido para usuario: " + email + " con perfil: " + perfil);

            // Validar información requerida
            if (!isValidUserInfo(email, perfil)) {
                String errorMsg = "Token inválido: información de usuario incompleta - Email: " + 
                                (email == null ? "null" : email) + 
                                ", Perfil: " + (perfil == null ? "null" : perfil);
                LOGGER.warning(errorMsg);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"" + errorMsg + "\"}")
                    .build());
                return;
            }

            // Almacenar información en el contexto
            storeUserInfo(requestContext, email, perfil);

            try {
                // Verificar permisos basados en el rol
                if (!hasPermission(perfil, path)) {
                    String errorMsg;
                    if (path.equals("/usuarios/modificar")) {
                        errorMsg = "No tiene permisos para modificar usuarios. Solo los administradores pueden realizar esta acción.";
                    } else {
                        errorMsg = String.format(
                            "Acceso denegado - Usuario: %s, Perfil: %s, Path: %s - No tiene los permisos necesarios",
                            email, perfil, path
                        );
                    }
                    LOGGER.warning(errorMsg);
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("{\"error\":\"" + errorMsg + "\"}")
                        .build());
                } else {
                    LOGGER.info("Permisos validados correctamente para el usuario: " + email);
                }
            } catch (Exception e) {
                String errorMsg = "Error al verificar permisos: " + e.getMessage();
                LOGGER.log(Level.SEVERE, errorMsg, e);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"" + errorMsg + "\"}")
                    .build());
            }

        } catch (Exception e) {
            String errorMsg = "Error al procesar el token: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\":\"" + errorMsg + "\"}")
                .build());
        }
    }

    private String extractAndValidateToken(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            String errorMsg = "Token no proporcionado o formato inválido - Header: " + 
                            (authorizationHeader == null ? "null" : authorizationHeader);
            LOGGER.warning(errorMsg);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\":\"" + errorMsg + "\"}")
                .build());
            return null;
        }

        return authorizationHeader.substring("Bearer".length()).trim();
    }

    protected Claims validateToken(String token) throws TokenValidationException {
        try {
            Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            String errorMsg = "Token inválido: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            throw new TokenValidationException(errorMsg);
        }
    }

    protected boolean isValidUserInfo(String email, String perfil) {
        return email != null && !email.isEmpty() &&
               perfil != null && !perfil.isEmpty();
    }

    private void storeUserInfo(ContainerRequestContext requestContext, 
                            String email, 
                            String perfil) {
        requestContext.setProperty("email", email);
        requestContext.setProperty("perfil", perfil);
    }

    private boolean isPublicEndpoint(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    protected boolean hasPermission(String perfil, String path) {
        if (perfil == null || perfil.isEmpty()) {
            return false;
        }

        // Verificar permisos en la base de datos
        List<FuncionalidadDto> funcionalidades = funcionalidadService.obtenerTodas();

        // Verificar si es una modificación de usuario
        if (path.equals("/usuarios/modificar")) {
            LOGGER.info("Verificando permisos para modificación de usuario");
            // Solo permitir que administradores modifiquen a otros administradores
            if (perfil.equals("Administrador")) {
                LOGGER.info("Usuario con perfil Administrador intentando modificar - Permitiendo acceso");
                return true;
            } else {
                LOGGER.log(Level.WARNING, "Usuario con perfil {0} intentando modificar - Acceso denegado", perfil);
                return false;
            }
        }

        boolean hasPermission = funcionalidades.stream()
                .anyMatch(funcionalidad -> {
                    boolean pathMatches = path.startsWith(funcionalidad.getRuta());
                    boolean profileMatches = funcionalidad.getPerfiles().stream()
                            .anyMatch(perfilDto -> perfilDto.getNombrePerfil().equals(perfil));
                    
                    if (pathMatches) {
                        LOGGER.info("Path coincide con funcionalidad: " + funcionalidad.getRuta());
                        LOGGER.info("Perfiles permitidos: " + funcionalidad.getPerfiles());
                    }
                    
                    return pathMatches && profileMatches;
                });

        if (!hasPermission) {
            LOGGER.log(Level.WARNING, "Permiso denegado - Perfil: {0}, Path: {1}", new Object[]{perfil, path});
        }

        return hasPermission;
    }
}
