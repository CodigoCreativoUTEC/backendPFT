package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.PasswordUtils;
import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.jwt.JwtService;
import codigocreativo.uy.servidorapp.servicios.UsuarioRemote;
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

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static jakarta.security.enterprise.authentication.mechanism.http.openid.OpenIdConstant.EMAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuario() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setEmail("test@example.com");
        usuario.setCedula("12345678");
        usuario.setContrasenia("password123");

        doNothing().when(usuarioRemote).crearUsuario(any(UsuarioDto.class));

        Response response = usuarioResource.crearUsuario(usuario);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals("{\"message\":\"Usuario creado correctamente\"}", response.getEntity());
        verify(usuarioRemote, times(1)).crearUsuario(any(UsuarioDto.class));
    }

    @Test
    void testModificarUsuario() {
        UsuarioDto usuario = new UsuarioDto();

        doNothing().when(usuarioRemote).modificarUsuario(any(UsuarioDto.class));

        Response response = usuarioResource.modificarUsuario(usuario);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(usuarioRemote, times(1)).modificarUsuario(any(UsuarioDto.class));
    }

    @Test
    void testModificarPropioUsuarioNoAutorizado() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setEmail("unauthorized@example.com");
        String token = "Bearer token";

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("authorized@example.com");
        when(jwtService.parseToken(anyString())).thenReturn(claims);

        Response response = usuarioResource.modificarPropioUsuario(usuario, token);

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        assertEquals("{\"message\":\"No autorizado para modificar este usuario\"}", response.getEntity());
    }

    @Test
    void testInactivarUsuario() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setCedula("12345678");
        String token = "Bearer token";

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("admin@example.com");
        when(claims.get("perfil", String.class)).thenReturn("Administrador");
        when(jwtService.parseToken(anyString())).thenReturn(claims);

        UsuarioDto usuarioAInactivar = new UsuarioDto();
        usuarioAInactivar.setEmail("user@example.com");
        usuarioAInactivar.setIdPerfil(new PerfilDto(1L, "Usuario", Estados.ACTIVO));

        when(usuarioRemote.obtenerUsuarioPorCI(anyString())).thenReturn(usuarioAInactivar);
        doNothing().when(usuarioRemote).eliminarUsuario(any(UsuarioDto.class));

        Response response = usuarioResource.inactivarUsuario(usuario, token);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("{\"message\":\"Usuario inactivado correctamente\"}", response.getEntity());
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
    void testBuscarUsuarioPorId() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setId(1L);
        when(usuarioRemote.obtenerUsuario(anyLong())).thenReturn(usuario);

        UsuarioDto result = usuarioResource.buscarUsuario(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
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
    void testLoginSuccess() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Crear un usuario válido con estado ACTIVO
        UsuarioDto usuario = new UsuarioDto();
        usuario.setEmail("test@example.com");
        usuario.setContrasenia(PasswordUtils.generateSaltedHash("password123")); // Generar un hash válido para la contraseña
        usuario.setEstado(Estados.ACTIVO);
        usuario.setIdPerfil(new PerfilDto(1L,"Usuario",Estados.ACTIVO));

        // Mockear el comportamiento del servicio remoto para encontrar al usuario por email
        when(usuarioRemote.findUserByEmail("test@example.com")).thenReturn(usuario);

        // Mockear la generación de un token JWT
        when(jwtService.generateToken(anyString(), anyString())).thenReturn("mock-token");

        // Crear una solicitud de login válida
        UsuarioResource.LoginRequest loginRequest = new UsuarioResource.LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        // Llamar al método de login del recurso
        Response response = usuarioResource.login(loginRequest);

        // Verificar que la respuesta es exitosa
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "El estado de la respuesta debería ser 200 OK");

        // Verificar que la entidad de la respuesta no sea nula
        assertNotNull(response.getEntity(), "La entidad de la respuesta no debería ser nula");

        // Verificar el contenido del token en la respuesta
        UsuarioResource.LoginResponse loginResponse = (UsuarioResource.LoginResponse) response.getEntity();
        assertEquals("mock-token", loginResponse.getToken(), "El token de la respuesta no coincide con el esperado");
    }



    @Test
    void testRenovarToken() {
        String token = "Bearer validToken";
        Claims claims = mock(Claims.class);
        when(claims.getExpiration()).thenReturn(new Date(System.currentTimeMillis() + 10000));
        when(claims.get(EMAIL, String.class)).thenReturn("test@example.com");
        when(claims.get("perfil", String.class)).thenReturn("Usuario");
        when(jwtService.parseToken(anyString())).thenReturn(claims);
        when(jwtService.generateToken(anyString(), anyString())).thenReturn("newJwtToken");

        Response response = usuarioResource.renovarToken(token);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

}
