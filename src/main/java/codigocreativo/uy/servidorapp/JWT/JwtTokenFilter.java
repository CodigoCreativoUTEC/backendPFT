package codigocreativo.uy.servidorapp.JWT;

import java.io.IOException;

import codigocreativo.uy.servidorapp.responses.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
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

        // Permitir acceso sin autenticación a ciertos endpoints
        if (path.startsWith("/usuarios/login") || path.startsWith("/usuarios/google-login") || path.startsWith("/usuarios/crear")) {
            return;
        }

        // Verificar la presencia y validez del token
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();
        System.out.println("Token: " + authorizationHeader);

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.get("email", String.class);
            String perfil = claims.get("perfil", String.class);
            System.out.println("Perfil: " + perfil);
            System.out.println("Email: " + email);

            if (email == null || perfil == null || email.isEmpty() || perfil.isEmpty()) {
                System.out.println("Email o perfil vacíos");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            // Almacenar email y perfil en el contexto de la solicitud
            requestContext.setProperty("email", email);
            requestContext.setProperty("perfil", perfil);

            verificarPermiso(requestContext, "/usuarios/ListarTodosLosUsuarios", perfil, ADMINISTRADOR, "No tienes permisos para listar usuarios");
            verificarPermiso(requestContext, "/usuarios/ListarTodosLosUsuarios", perfil, AUX_ADMINISTRATIVO, "No tienes permisos para listar usuarios");
            verificarPermiso(requestContext, "/usuarios/modificar", perfil, ADMINISTRADOR, "No tienes permisos para modificar usuarios");
            verificarPermiso(requestContext, "/usuarios/modificar", perfil, AUX_ADMINISTRATIVO, "No tienes permisos para modificar usuarios");
            verificarPermiso(requestContext, "/usuarios/eliminar", perfil, ADMINISTRADOR, "No tienes permisos para eliminar usuarios");

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private void verificarPermiso(ContainerRequestContext requestContext, String path, String perfil, String requiredRole, String errorMessage) throws IOException {
        if (path.startsWith(path) && !requiredRole.equals(perfil)) {
            ErrorResponse errorResponse = new ErrorResponse(errorMessage);
            String jsonError = new ObjectMapper().writeValueAsString(errorResponse);
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity(jsonError).type("application/json").build());
        }
    }
}
