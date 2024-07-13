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
        System.out.println("Ejecutando JwtTokenFilter...");
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        String path = requestContext.getUriInfo().getPath();
        System.out.println("obtener path "+path);

        // Permitir acceso sin token JWT a endpoints específicos
        if (path.startsWith("/usuarios/login") || path.startsWith("/usuarios/google-login") || path.startsWith("/usuarios/crear")) {
            return;  // Permitir acceso sin token JWT
        }


        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.out.println("No se encontró encabezado de autorización o no comienza con 'Bearer'");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            if (!isTokenValid(token)) {
                System.out.println("Token inválido");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception e) {
            System.out.println("Error al validar el token: " + e.getMessage());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
            return true; // Si el token es válido, retorna true
        } catch (Exception e) {
            return false; // Si el token no es válido, retorna false
        }
    }
}
