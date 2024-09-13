package codigocreativo.uy.servidorapp.JWT;

import java.io.IOException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtTokenFilter implements ContainerRequestFilter {

    private static final String ADMINISTRADOR = "Administrador";
    private static final String AUX_ADMINISTRATIVO = "Aux administrativo";
    private static final String INGENIERO_BIOMEDICO = "Ingeniero biomédico";
    private static final String TECNICO = "Tecnico";

    private static final String SECRET_KEY = "b0bc1f9b2228b2094f3ba7bdb1b6a58059af6cdaf143127181bd0a17e6d312e2";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();  // Obtener el tipo de método (GET, POST, PUT, DELETE)
        MultivaluedMap<String, String> queryParams = requestContext.getUriInfo().getQueryParameters();  // Obtener los query params

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
            Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
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

            // Verificar permisos basados en el perfil, método HTTP, y query params
            if (!hasPermission(perfil, path, method, queryParams)) {
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

    // Función genérica para verificar permisos según el perfil, endpoint, método HTTP y query params
    private boolean hasPermission(String perfil, String path, String method, MultivaluedMap<String, String> queryParams) {
        // Verificar permisos según el método HTTP (GET, POST, PUT, DELETE)

        boolean todosLosPermisos = perfil.equals(ADMINISTRADOR) || perfil.equals(AUX_ADMINISTRATIVO) || perfil.equals(INGENIERO_BIOMEDICO) || perfil.equals(TECNICO);

        // Endpoints referentes a Usuarios
        if (path.startsWith("/usuarios/ListarTodosLosUsuarios") && method.equals("GET")) {
                return perfil.equals(ADMINISTRADOR) || perfil.equals(AUX_ADMINISTRATIVO);
            }

        if (path.startsWith("/usuarios/modificar") && method.equals("PUT")) {
                return perfil.equals(ADMINISTRADOR) || perfil.equals(AUX_ADMINISTRATIVO);
            }

        if (path.startsWith("/usuarios/Inactivar") && method.equals("PUT")) {
                return perfil.equals(ADMINISTRADOR) || perfil.equals(AUX_ADMINISTRATIVO);
            }

        // Endpoints referentes a Equipos
        if (path.startsWith("/equipos/CrearEquipo") && method.equals("POST")) {
                return todosLosPermisos;
            }

        if (path.startsWith("/equipos/Inactivar") && method.equals("PUT")) {
                return todosLosPermisos;
            }

        if (path.startsWith("/equipos/MoficarEquipo") && method.equals("PUT")) {
                return todosLosPermisos;
            }

        if (path.startsWith("/equipos/ListarTodosLosEquipos") && method.equals("GET")) {
                return todosLosPermisos;
            }

        // Agrega más condiciones según los endpoints y roles necesarios
        // Verificar parámetros de la consulta (query params)
        if (queryParams.containsKey("estado") && queryParams.getFirst("estado").equals("inactivo")) {
            return perfil.equals(ADMINISTRADOR);  // Solo ADMINISTRADOR puede gestionar inactivos, por ejemplo
        }

        return true; // Por defecto permitir el acceso si no se especifica lo contrario
    }
}
