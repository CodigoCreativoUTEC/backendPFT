package codigocreativo.uy.servidorapp.jwt;

import java.security.Key;
import java.util.Base64;

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
                path.startsWith("/usuarios/renovar-token") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/usuarios/google-login") ||
                path.startsWith("/usuarios/crear");
    }

    private boolean hasPermission(String perfil, String path) {
        boolean todosLosPermisos = perfil.equals(ADMINISTRADOR) || perfil.equals(AUX_ADMINISTRATIVO) || perfil.equals(INGENIERO_BIOMEDICO) || perfil.equals(TECNICO);

        // Endpoints referentes a Usuarios
        if (    path.startsWith("/usuarios/listar") ||
                path.startsWith("/usuarios/modificar") ||
                path.startsWith("/usuarios/seleccionar") ||
                path.startsWith("/usuarios/filtrar") ||
                path.startsWith("/usuarios/BuscarUsuarioPorId") ||
                path.startsWith("/usuarios/inactivar")) {
            return perfil.equals(ADMINISTRADOR) || perfil.equals(AUX_ADMINISTRATIVO);
        }

        // enpoint renovador de token

        if (    path.startsWith("/usuarios/renovar-token") ||
                path.startsWith("/usuarios/modificar-propio-usuario") ||
                path.startsWith("/usuarios/obtenerUserEmail")) {
            return todosLosPermisos;
        }
        // Endpoints referentes a Equipos
        if (    path.startsWith("/equipos/crear") ||
                path.startsWith("/equipos/modificar") ||
                path.startsWith("/equipos/inactivar") ||
                path.startsWith("/equipos/listar") ||
                path.startsWith("/equipos/seleccionar") ||
                path.startsWith("/equipos/filtrar") ||
                path.startsWith("/equipos/listarBajas"))
            return todosLosPermisos;

        // Endpoints referentes a Proveedores
        if (    path.startsWith("/proveedores/crear") ||
                path.startsWith("/proveedores/modificar") ||
                path.startsWith("/proveedores/inactivar") ||
                path.startsWith("/proveedores/listar") ||
                path.startsWith("/proveedores/seleccionar") ||
                path.startsWith("/proveedores/buscar"))
            return perfil.equals(AUX_ADMINISTRATIVO);

        // Endpoints referentes a Marcas
        if (    path.startsWith("/marca/crear") ||
                path.startsWith("/marca/modificar") ||
                path.startsWith("/marca/inactivar") ||
                path.startsWith("/marca/listar") ||
                path.startsWith("/marca/seleccionar"))
            return perfil.equals(AUX_ADMINISTRATIVO);

        // Endpoints referentes a Modelos
        if (    path.startsWith("/modelo/crear") ||
                path.startsWith("/modelo/modificar") ||
                path.startsWith("/modelo/inactivar") ||
                path.startsWith("/modelo/listar") ||
                path.startsWith("/modelo/seleccionar"))
            return perfil.equals(AUX_ADMINISTRATIVO);

        // Endpoints referentes a Paises
        if (    path.startsWith("/paises/crear") ||
                path.startsWith("/paises/modificar") ||
                path.startsWith("/paises/inactivar") ||
                path.startsWith("/paises/listar"))
            return perfil.equals(AUX_ADMINISTRATIVO);

        // Endpoints referentes a Tipos de Equipos
        if (    path.startsWith("/tipoEquipos/crear") ||
                path.startsWith("/tipoEquipos/modificar") ||
                path.startsWith("/tipoEquipos/inactivar") ||
                path.startsWith("/tipoEquipos/listar") ||
                path.startsWith("/tipoEquipos/seleccionar"))
            return perfil.equals(AUX_ADMINISTRATIVO);

        // Endpoints referentes a Intervenciones
        if (    path.startsWith("/intervenciones/crear") ||
                path.startsWith("/intervenciones/modificar") ||
                path.startsWith("/intervenciones/listar") ||
                path.startsWith("/intervenciones/seleccionar") ||
                path.startsWith("/intervenciones/reportePorFechas") ||
                path.startsWith("/intervenciones/reportePorTipo"))
            return todosLosPermisos;

        // Enpoints referentes a Perfiles
        if (    path.startsWith("/perfiles/crear") ||
                path.startsWith("/perfiles/modificar") ||
                path.startsWith("/perfiles/inactivar") ||
                path.startsWith("/perfiles/seleccionar") ||
                path.startsWith("/perfiles/listar") ||
                path.startsWith("/perfiles/buscarPorNombre") ||
                path.startsWith("/perfiles/buscarPorEstado"))
            return perfil.equals(ADMINISTRADOR) || perfil.equals(AUX_ADMINISTRATIVO);

        // Enpoints referentes a Tipo de Intervenciones
        if (    path.startsWith("/tipoIntervenciones/crear") ||
                path.startsWith("/tipoIntervenciones/modificar") ||
                path.startsWith("/tipoIntervenciones/inactivar") ||
                path.startsWith("/tipoIntervenciones/listar") ||
                path.startsWith("/tipoIntervenciones/seleccionar"))
            return perfil.equals(AUX_ADMINISTRATIVO);

        // Endpoints referentes a Funcionalidades
        if (    path.startsWith("/funcionalidades/crear") ||
                path.startsWith("/funcionalidades/modificar") ||
                path.startsWith("/funcionalidades/inactivar") ||
                path.startsWith("/funcionalidades/listar") ||
                path.startsWith("/funcionalidades/seleccionar"))
            return perfil.equals(ADMINISTRADOR) || perfil.equals(AUX_ADMINISTRATIVO);

        // Endpoints referentes a Ubicaciones
        if ( path.startsWith("/ubicaciones/listar"))
            return todosLosPermisos;

        return true; // Por defecto permitir el acceso si no se especifica lo contrario
    }
}