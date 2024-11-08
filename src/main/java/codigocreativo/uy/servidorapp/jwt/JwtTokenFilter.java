package codigocreativo.uy.servidorapp.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.List;

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

    @EJB
    FuncionalidadRemote funcionalidadService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String path = requestContext.getUriInfo().getPath();

        // Permitir acceso sin autenticación a ciertos endpoints
        if (isPublicEndpoint(path)) {
            return;
        }

        // Verificar la presencia y validez del token
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.get("email", String.class);
            String perfil = claims.get("perfil", String.class);

            if (email == null || perfil == null || email.isEmpty() || perfil.isEmpty()) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            // Almacenar email y perfil en el contexto de la solicitud
            requestContext.setProperty("email", email);
            requestContext.setProperty("perfil", perfil);

            if (!hasPermission(perfil, path)) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("{\"error\":\"No tiene permisos para realizar esta acción\"}").build());
            }

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    // Función para identificar los endpoints públicos
    private boolean isPublicEndpoint(String path) {
        return path.startsWith("/usuarios/login") ||
                path.startsWith("/usuarios/google-login") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/usuarios/crear");
    }

    private boolean hasPermission(String perfil, String path) {
        List<FuncionalidadDto> funcionalidades = funcionalidadService.obtenerTodas();
        return funcionalidades.stream()
                .anyMatch(funcionalidad -> path.startsWith(funcionalidad.getRuta()) &&
                        funcionalidad.getPerfiles().stream()
                                .anyMatch(perfilDto -> perfilDto.getNombrePerfil().equals(perfil)));
    }
}
