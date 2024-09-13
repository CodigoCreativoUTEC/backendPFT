package codigocreativo.uy.servidorapp.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.stream.Stream;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtTokenFilter implements ContainerRequestFilter {

    private static final String ADMINISTRADOR = "Administrador";
    private static final String AUX_ADMINISTRATIVO = "Aux administrativo";
    private static final String INGENIERO_BIOMEDICO = "Ingeniero biomédico";
    private static final String TECNICO = "Tecnico";

    private static final String SECRET_KEY = System.getenv("SECRET_KEY");

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
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
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
               path.startsWith("/usuarios/crear");
    }


    private boolean hasPermission(String perfil, String path) {

        boolean todosLosPermisos = perfil.equals(ADMINISTRADOR) || perfil.equals(AUX_ADMINISTRATIVO) || perfil.equals(INGENIERO_BIOMEDICO) || perfil.equals(TECNICO);

        // Endpoints referentes a Usuarios
        if (Stream.of("/usuarios/ListarTodosLosUsuarios",
                "/usuarios/modificar",
                "/usuarios/Inactivar").anyMatch(path::startsWith))
            return perfil.equals(ADMINISTRADOR) || perfil.equals(AUX_ADMINISTRATIVO);

        // Endpoints referentes a Equipos
        if (path.startsWith("/equipos/CrearEquipo") ||
                path.startsWith("/equipos/Inactivar") ||
                path.startsWith("/equipos/MoficarEquipo") ||
                path.startsWith("/equipos/ListarTodosLosEquipos"))
            return todosLosPermisos;

        return true; // Por defecto permitir el acceso si no se especifica lo contrario
    }
}