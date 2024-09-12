// src/main/java/codigocreativo/uy/servidorapp/JWT/JwtTokenFilter.java
package codigocreativo.uy.servidorapp.JWT;

import java.io.IOException;
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

    private static final String SECRET_KEY = "b0bc1f9b2228b2094f3ba7bdb1b6a58059af6cdaf143127181bd0a17e6d312e2";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String path = requestContext.getUriInfo().getPath();

        if (path.startsWith("/usuarios/login") || path.startsWith("/usuarios/google-login") || path.startsWith("/usuarios/crear")) {
            return;
        }

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

            String email = claims.getSubject();
            String role = claims.get("role", String.class);

            if (email == null || role == null || !isAuthorized(email, role, path)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthorized(String email, String role, String path) {
        // Implementar lógica de verificación de roles y permisos
        if (role.equals("ADMIN") && path.startsWith("/admin")) {
            return true;
        }
        // Agregar más lógica según sea necesario
        return false;
    }
}