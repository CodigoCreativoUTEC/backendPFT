package codigocreativo.uy.servidorapp.filtros;

import codigocreativo.uy.servidorapp.jwt.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.io.IOException;

class JwtTokenFilterTest {
    @Mock JwtService jwtService;
    @Mock ContainerRequestContext requestContext;
    JwtTokenFilter filter;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        filter = new JwtTokenFilter();
        
        // Inyectar el mock de jwtService usando reflexión
        var field = JwtTokenFilter.class.getDeclaredField("jwtService");
        field.setAccessible(true);
        field.set(filter, jwtService);
    }

    @Test
    void testPublicEndpoint() throws IOException {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/usuarios/login");
        filter.filter(requestContext);
        verify(requestContext, never()).abortWith(any());
    }

    @Test
    void testTokenAusente() throws IOException {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/api/privado");
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        filter.filter(requestContext);
        verify(requestContext).abortWith(argThat(resp -> resp.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()));
    }

    @Test
    void testTokenInvalido() throws IOException {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/api/privado");
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer tokenInvalido");
        when(jwtService.parseToken("tokenInvalido")).thenThrow(new RuntimeException("Token inválido"));
        filter.filter(requestContext);
        verify(requestContext).abortWith(argThat(resp -> resp.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()));
    }

    @Test
    void testTokenSinEmailOPerfil() throws IOException {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/api/privado");
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer tokenValido");
        Claims claims = mock(Claims.class);
        when(claims.get("email", String.class)).thenReturn(null);
        when(claims.get("perfil", String.class)).thenReturn(null);
        when(jwtService.parseToken("tokenValido")).thenReturn(claims);
        filter.filter(requestContext);
        verify(requestContext).abortWith(argThat(resp -> resp.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()));
    }

    @Test
    void testTokenValido() throws IOException {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/api/usuarios/listar");
        when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer tokenValido");
        Claims claims = mock(Claims.class);
        when(claims.get("email", String.class)).thenReturn("admin@mail.com");
        when(claims.get("perfil", String.class)).thenReturn("Administrador");
        when(jwtService.parseToken("tokenValido")).thenReturn(claims);
        filter.filter(requestContext);
        verify(requestContext, never()).abortWith(any());
        verify(requestContext).setProperty("email", "admin@mail.com");
        verify(requestContext).setProperty("perfil", "Administrador");
    }

    @Test
    void testSwaggerUiPublicPath() throws IOException {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/swagger-ui");
        filter.filter(requestContext);
        verify(requestContext, never()).abortWith(any());
    }

    @Test
    void testOpenApiPublicPath() throws IOException {
        when(requestContext.getUriInfo()).thenReturn(mock(jakarta.ws.rs.core.UriInfo.class));
        when(requestContext.getUriInfo().getPath()).thenReturn("/openapi.json");
        filter.filter(requestContext);
        verify(requestContext, never()).abortWith(any());
    }
} 