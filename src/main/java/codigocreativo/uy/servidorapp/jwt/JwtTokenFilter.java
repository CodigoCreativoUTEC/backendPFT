package codigocreativo.uy.servidorapp.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.Set;

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

    private static final String SECRET_KEY = System.getenv("SECRET_KEY");
    private static final Set<String> PUBLIC_PATHS = Set.of(
        "/usuarios/login",
        "/usuarios/google-login",
        "/swagger-ui",
        "/usuarios/crear",
        "/menu"  
    );

    @EJB
    private FuncionalidadRemote funcionalidadService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();

        // Verificar si es un endpoint público
        if (isPublicEndpoint(path)) {
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
            String userId = claims.get("userId", String.class);
            String email = claims.get("email", String.class);
            String perfil = claims.get("perfil", String.class);
            @SuppressWarnings("unchecked")
            List<String> permisos = claims.get("permisos", List.class);

            // Validar información requerida
            if (!isValidUserInfo(userId, email, perfil)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Token inválido: información de usuario incompleta\"}")
                    .build());
                return;
            }

            // Almacenar información en el contexto
            storeUserInfo(requestContext, userId, email, perfil, permisos);

            // Verificar permisos
            if (!hasPermission(permisos, path)) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\":\"No tiene permisos para realizar esta acción\"}")
                    .build());
            }

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\":\"Error al procesar el token: " + e.getMessage() + "\"}")
                .build());
        }
    }

    private String extractAndValidateToken(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\":\"Token no proporcionado o formato inválido\"}")
                .build());
            return null;
        }

        return authorizationHeader.substring("Bearer".length()).trim();
    }

    private Claims validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isValidUserInfo(String userId, String email, String perfil) {
        return userId != null && !userId.isEmpty() &&
                email != null && !email.isEmpty() &&
                perfil != null && !perfil.isEmpty();
    }

    private void storeUserInfo(ContainerRequestContext requestContext, 
                            String userId, String email, 
                            String perfil, List<String> permisos) {
        requestContext.setProperty("userId", userId);
        requestContext.setProperty("email", email);
        requestContext.setProperty("perfil", perfil);
        requestContext.setProperty("permisos", permisos);
    }

    private boolean isPublicEndpoint(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    private boolean hasPermission(List<String> permisos, String path) {
        if (permisos == null || permisos.isEmpty()) {
            return false;
        }
        
        // Primero verificar en los permisos del token
        if (permisos.stream().anyMatch(path::startsWith)) {
            return true;
        }

        // Si no está en los permisos del token, verificar en la base de datos
        List<FuncionalidadDto> funcionalidades = funcionalidadService.obtenerTodas();
        return funcionalidades.stream()
                .anyMatch(funcionalidad -> path.startsWith(funcionalidad.getRuta()) &&
                        funcionalidad.getPerfiles().stream()
                                .anyMatch(perfilDto -> permisos.contains(perfilDto.getNombrePerfil())));
    }
}
