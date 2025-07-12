package codigocreativo.uy.servidorapp.filtros;

import codigocreativo.uy.servidorapp.jwt.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebFilter;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Provider
@PreMatching
@WebFilter(urlPatterns = "/*")
public class JwtTokenFilter implements ContainerRequestFilter {

    @EJB
    private JwtService jwtService;

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
        "/usuarios/crear",
        "/usuarios/login",
        "/usuarios/google-login",
        "/usuarios/listar",
        "/usuarios/filtrar",
        "/usuarios/modificar-propio-usuario",
        "/perfiles/listar",
        "/equipos/listar",
        "/equipos/filtrar",
        "/equipos/listarBajas",
        "/intervenciones/listar",
        "/intervenciones/filtrar",
        "/tipos-equipo/listar",
        "/tipos-equipo/filtrar",
        "/marcas/listar",
        "/marcas/filtrar",
        "/ubicaciones/listar",
        "/ubicaciones/filtrar",
        "/modelos/listar",
        "/modelos/filtrar",
        "/proveedores/listar",
        "/proveedores/filtrar",
        "/paises/listar",
        "/paises/filtrar",
        "/funcionalidades/listar",
        "/funcionalidades/filtrar",
        "/tipoIntervenciones/listar",
        "/tipoIntervenciones/filtrar",
        "/api/openapi.json",
        "/api/swagger-ui",
        "/openapi.json",
        "/swagger-ui",
        "/swagger-ui/index.html"
    );

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        // Permitir rutas públicas
        if (isPublicPath(path)) {
            return;
        }

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\": \"Token JWT requerido\"}")
                .build());
            return;
        }

        try {
            String token = authorizationHeader.substring("Bearer ".length()).trim();
            Claims claims = jwtService.parseToken(token);
            
            String email = claims.get("email", String.class);
            String perfil = claims.get("perfil", String.class);

            if (email == null || perfil == null) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Token JWT inválido\"}")
                    .build());
                return;
            }

            // Almacenar información del usuario en el contexto
            requestContext.setProperty("email", email);
            requestContext.setProperty("perfil", perfil);

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\": \"Token JWT inválido\"}")
                .build());
        }
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
}
