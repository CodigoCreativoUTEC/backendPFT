package codigocreativo.uy.servidorapp.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.Key;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtTokenFilterTest {

    @Mock
    private ContainerRequestContext requestContext;

    @Mock
    private UriInfo uriInfo;

    private JwtTokenFilter jwtTokenFilter;

    private Key key;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtTokenFilter = new JwtTokenFilter();
        when(requestContext.getUriInfo()).thenReturn(uriInfo);
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(System.getenv("SECRET_KEY"))); // Clave secreta de ejemplo
    }

    @Test
    void testFilter_PublicEndpoint_NoAuthenticationRequired() {
        when(uriInfo.getPath()).thenReturn("/usuarios/login");

        jwtTokenFilter.filter(requestContext);

        verify(requestContext, never()).abortWith(any(Response.class));
    }

    @Test
    void testFilter_NoAuthorizationHeader_ShouldAbortWithUnauthorized() {
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        when(uriInfo.getPath()).thenReturn("/equipos/crear");

        jwtTokenFilter.filter(requestContext);

        ArgumentCaptor<Response> captor = ArgumentCaptor.forClass(Response.class);
        verify(requestContext).abortWith(captor.capture());
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), captor.getValue().getStatus());
    }

    @Test
    void testFilter_InvalidAuthorizationHeader_ShouldAbortWithUnauthorized() {
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("InvalidToken");
        when(uriInfo.getPath()).thenReturn("/equipos/crear");

        jwtTokenFilter.filter(requestContext);

        ArgumentCaptor<Response> captor = ArgumentCaptor.forClass(Response.class);
        verify(requestContext).abortWith(captor.capture());
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), captor.getValue().getStatus());
    }

    @Test
    void testFilter_ValidTokenWithNoPermissions_ShouldAbortWithForbidden() {
        String token = Jwts.builder()
                .setSubject("testUser")
                .claim("perfil", "Usuario")
                .claim("email", "test@test.com")
                .signWith(key)
                .compact();

        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        when(uriInfo.getPath()).thenReturn("/proveedores/crear");

        jwtTokenFilter.filter(requestContext);

        ArgumentCaptor<Response> captor = ArgumentCaptor.forClass(Response.class);
        verify(requestContext).abortWith(captor.capture());
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), captor.getValue().getStatus());
        assertEquals("{\"error\":\"No tiene permisos para realizar esta acción\"}", captor.getValue().getEntity());
    }

    @Test
    void testFilter_ValidTokenWithPermissions_ShouldPass() {
        String token = Jwts.builder()
                .setSubject("testUser")
                .claim("perfil", "Aux administrativo")
                .claim("email", "test@example.com")
                .signWith(key)
                .compact();

        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
        when(uriInfo.getPath()).thenReturn("/usuarios/listar");

        jwtTokenFilter.filter(requestContext);

        verify(requestContext, never()).abortWith(any(Response.class));
    }

    @Test
    void testFilter_InvalidToken_ShouldAbortWithUnauthorized() {
        String token = "Bearer invalidToken";
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        when(uriInfo.getPath()).thenReturn("/usuarios/listar");

        jwtTokenFilter.filter(requestContext);

        ArgumentCaptor<Response> captor = ArgumentCaptor.forClass(Response.class);
        verify(requestContext).abortWith(captor.capture());
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), captor.getValue().getStatus());
    }
}
