package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.InstitucionDto;
import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.jwt.JwtService;
import codigocreativo.uy.servidorapp.servicios.UsuarioRemote;
import codigocreativo.uy.servidorapp.PasswordUtils;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.auth.oauth2.TokenVerifier;
import io.jsonwebtoken.Claims;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioResourceTest {

    @InjectMocks
    private UsuarioResource usuarioResource;

    @Mock
    private UsuarioRemote usuarioRemote;

    @Mock
    private JwtService jwtService;

    @Mock
    private TokenVerifier tokenVerifier;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuario() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setContrasenia("password123");

        doNothing().when(usuarioRemote).crearUsuario(any(UsuarioDto.class));

        Response response = usuarioResource.crearUsuario(usuario);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(usuarioRemote, times(1)).crearUsuario(any(UsuarioDto.class));
    }

    @Test
    void testModificarUsuario() {
        UsuarioDto usuario = new UsuarioDto();

        doNothing().when(usuarioRemote).modificarUsuario(any(UsuarioDto.class));

        try (Response response = usuarioResource.modificarUsuario(usuario)) {

            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        }
        verify(usuarioRemote, times(1)).modificarUsuario(any(UsuarioDto.class));
    }

    @Test
void testModificarPropioUsuario() {
    UsuarioDto usuario = new UsuarioDto();
    usuario.setId(1L); // Set the user ID
    usuario.setEmail("test@example.com");
    usuario.setContrasenia("newPassword123");
    usuario.setNombreUsuario("testUser");
    usuario.setIdPerfil(new PerfilDto());
    usuario.setEstado(Estados.ACTIVO);
    usuario.setIdInstitucion(new InstitucionDto());
    usuario.setCedula("12345678");

    Claims claims = mock(Claims.class);
    when(claims.getSubject()).thenReturn("test@example.com");
    when(claims.get("perfil", String.class)).thenReturn("Usuario");
    when(claims.get("email", String.class)).thenReturn("test@example.com");
    when(jwtService.parseToken(anyString())).thenReturn(claims);

    UsuarioDto usuarioActual = new UsuarioDto();
    usuarioActual.setId(1L);
    usuarioActual.setEmail("test@example.com");
    usuarioActual.setContrasenia("oldPassword123");
    usuarioActual.setNombreUsuario("testUser");
    usuarioActual.setIdPerfil(new PerfilDto());
    usuarioActual.setEstado(Estados.ACTIVO);
    usuarioActual.setIdInstitucion(new InstitucionDto());
    usuarioActual.setCedula("12345678");

    when(usuarioRemote.obtenerUsuario(anyLong())).thenReturn(usuarioActual);
    doNothing().when(usuarioRemote).modificarUsuario(any(UsuarioDto.class));

    Response response = usuarioResource.modificarPropioUsuario(usuario, "Bearer token");

    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    verify(usuarioRemote, times(1)).modificarUsuario(any(UsuarioDto.class));
}

    @Test
    void testInactivarUsuario() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setCedula("12345678");

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("admin@example.com");
        when(claims.get("perfil", String.class)).thenReturn("Administrador");
        when(jwtService.parseToken(anyString())).thenReturn(claims);

        UsuarioDto usuarioAInactivar = new UsuarioDto();
        usuarioAInactivar.setEmail("user@example.com");
        PerfilDto perfil = new PerfilDto();
        perfil.setNombrePerfil("Usuario");
        usuarioAInactivar.setIdPerfil(perfil);
        when(usuarioRemote.obtenerUsuarioPorCI(anyString())).thenReturn(usuarioAInactivar);

        doNothing().when(usuarioRemote).eliminarUsuario(any(UsuarioDto.class));

        Response response = usuarioResource.inactivarUsuario(usuario, "Bearer token");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(usuarioRemote, times(1)).eliminarUsuario(any(UsuarioDto.class));
    }

    @Test
    void testFiltrarUsuarios() {
        List<UsuarioDto> usuarios = List.of(new UsuarioDto(), new UsuarioDto());
        when(usuarioRemote.obtenerUsuariosFiltrado(anyMap())).thenReturn(usuarios);

        List<UsuarioDto> result = usuarioResource.filtrarUsuarios("John", "Doe", null, null, null, null);

        assertEquals(2, result.size());
        verify(usuarioRemote, times(1)).obtenerUsuariosFiltrado(anyMap());
    }

    @Test
    void testBuscarUsuarioPorCI() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setCedula("12345678");
        when(usuarioRemote.obtenerUsuarioPorCI("12345678")).thenReturn(usuario);

        UsuarioDto result = usuarioResource.buscarUsuario("12345678");

        assertNotNull(result);
        assertEquals("12345678", result.getCedula());
    }

    @Test
    void testBuscarUsuarioPorId() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setId(1L);
        when(usuarioRemote.obtenerUsuario(1L)).thenReturn(usuario);

        UsuarioDto result = usuarioResource.buscarUsuario(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testObtenerUsuarioPorEstado() {
        List<UsuarioDto> usuarios = List.of(new UsuarioDto(), new UsuarioDto());
        when(usuarioRemote.obtenerUsuariosPorEstado(Estados.ACTIVO)).thenReturn(usuarios);

        List<UsuarioDto> result = usuarioResource.obtenerUsuarioPorEstado(Estados.ACTIVO);

        assertEquals(2, result.size());
    }

    @Test
    void testObtenerTodosLosUsuarios() {
        List<UsuarioDto> usuarios = List.of(new UsuarioDto(), new UsuarioDto());
        when(usuarioRemote.obtenerUsuarios()).thenReturn(usuarios);

        List<UsuarioDto> result = usuarioResource.obtenerTodosLosUsuarios();

        assertEquals(2, result.size());
    }

    @Test
    void testGetUserByEmail() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setEmail("test@example.com");
        when(usuarioRemote.findUserByEmail("test@example.com")).thenReturn(usuario);

        Response response = usuarioResource.getUserByEmail("test@example.com");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(usuario, response.getEntity());
    }

    @Test
    void testGetUserByEmailNotFound() {
        when(usuarioRemote.findUserByEmail("unknown@example.com")).thenReturn(null);

        Response response = usuarioResource.getUserByEmail("unknown@example.com");

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
void testLoginSuccess() {
    // Create a mock user with a valid password hash
    UsuarioDto user = new UsuarioDto();
    user.setIdPerfil(new PerfilDto());
    user.setEmail("test@example.com");
    user.setContrasenia("salt:aeh85yn4lPSAfQnpRexaF2W9+fDqr5UXGuur95mZv6g="); // Set a valid salted hash
    user.setEstado(Estados.ACTIVO);

    // Mock the behavior of UsuarioRemote
    when(usuarioRemote.findUserByEmail("test@example.com")).thenReturn(user);

    // Mock the behavior of JwtService
    when(jwtService.generateToken(anyString(), anyString())).thenReturn("mock-token");

    // Create a login request
    UsuarioResource.LoginRequest loginRequest = new UsuarioResource.LoginRequest();
    loginRequest.setEmail("test@example.com");
    loginRequest.setPassword("password123");

    // Call the login method
    Response response = usuarioResource.login(loginRequest);

    // Assert the response
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    assertNotNull(response.getEntity());
}

    @Test
    void testLoginUserNotFound() {
        UsuarioResource.LoginRequest loginRequest = new UsuarioResource.LoginRequest();
        loginRequest.setEmail("unknown@example.com");
        loginRequest.setPassword("password123");

        when(usuarioRemote.findUserByEmail("unknown@example.com")).thenReturn(null);

        Response response = usuarioResource.login(loginRequest);

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        assertEquals("{\"error\":\"Credenciales incorrectas o cuenta inactiva\"}", response.getEntity());
    }

    @Test
    void testRenovarTokenSuccess() {
        String token = "Bearer validToken";
        Claims claims = mock(Claims.class);
        when(claims.getExpiration()).thenReturn(new Date(System.currentTimeMillis() + 10000)); // Token válido
        when(claims.get("email", String.class)).thenReturn("test@example.com");
        when(claims.get("perfil", String.class)).thenReturn("Usuario");
        when(jwtService.parseToken(anyString())).thenReturn(claims);
        when(jwtService.generateToken(anyString(), anyString())).thenReturn("newJwtToken");

        Response response = usuarioResource.renovarToken(token);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    void testRenovarTokenExpired() {
        String token = "Bearer expiredToken";
        Claims claims = mock(Claims.class);
        when(claims.getExpiration()).thenReturn(new Date(System.currentTimeMillis() - 10000)); // Token expirado
        when(jwtService.parseToken(anyString())).thenReturn(claims);

        Response response = usuarioResource.renovarToken(token);

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        assertEquals("{\"error\": \"El token ha expirado.\"}", response.getEntity());
    }
}