package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.jwt.JwtService;
import codigocreativo.uy.servidorapp.servicios.UsuarioRemote;
import io.jsonwebtoken.Claims;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioResourceTest {

    @InjectMocks
    private UsuarioResource usuarioResource;

    @Mock
    private UsuarioRemote usuarioRemote;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuario() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setContrasenia("password123");

        doNothing().when(usuarioRemote).crearUsuario(usuario);

        Response response = usuarioResource.crearUsuario(usuario);

        assertEquals(201, response.getStatus());
        assertEquals("{\"message\":\"Usuario creado correctamente\"}", response.getEntity());
    }

    @Test
    void testCrearUsuarioError() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setContrasenia("password123");

        doThrow(new RuntimeException()).when(usuarioRemote).crearUsuario(usuario);

        Response response = usuarioResource.crearUsuario(usuario);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("{\"message\":\"Error al crear el usuario\"}", response.getEntity());
    }

    @Test
    void testModificarPropioUsuario() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setId(1L); // Establecer un ID válido
        usuario.setEmail("test@example.com");
        usuario.setContrasenia("newPassword123");

        // Mockear el Claims para simular la autenticación
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("test@example.com");
        when(jwtService.parseToken(anyString())).thenReturn(claims);

        // Mockear usuarioRemote para devolver un objeto usuarioActual válido
        when(usuarioRemote.obtenerUsuario(1L)).thenReturn(usuario);

        Response response = usuarioResource.modificarPropioUsuario(usuario, "Bearer token");

        // Verificar que el estado de respuesta sea 200
        assertEquals(200, response.getStatus());
        assertEquals("{\"message\":\"Usuario modificado correctamente\"}", response.getEntity());
    }


    @Test
    void testModificarPropioUsuarioUnauthorized() {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setEmail("wrong@example.com");

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("test@example.com");
        when(jwtService.parseToken(anyString())).thenReturn(claims);

        Response response = usuarioResource.modificarPropioUsuario(usuario, "Bearer token");

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        assertEquals("{\"message\":\"No autorizado para modificar este usuario\"}", response.getEntity());
    }

    @Test
    void testInactivarUsuario() {
        UsuarioDto usuarioAInactivar = new UsuarioDto();
        usuarioAInactivar.setEmail("user@example.com");
        usuarioAInactivar.setCedula("12345678");
        usuarioAInactivar.setIdPerfil(new PerfilDto(null, "Usuario", Estados.ACTIVO));

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("admin@example.com");
        when(claims.get("perfil", String.class)).thenReturn("Administrador");
        when(jwtService.parseToken(anyString())).thenReturn(claims);
        when(usuarioRemote.obtenerUsuarioPorCI("12345678")).thenReturn(usuarioAInactivar);

        Response response = usuarioResource.inactivarUsuario(usuarioAInactivar, "Bearer token");

        assertEquals(200, response.getStatus());
        assertEquals("{\"message\":\"Usuario inactivado correctamente\"}", response.getEntity());
    }

    @Test
    void testInactivarUsuarioUnauthorized() {
        UsuarioDto usuarioAInactivar = new UsuarioDto();
        usuarioAInactivar.setEmail("admin@example.com");
        usuarioAInactivar.setCedula("12345678");
        usuarioAInactivar.setIdPerfil(new PerfilDto(null, "Administrador", Estados.ACTIVO));

        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("admin@example.com");
        when(claims.get("perfil", String.class)).thenReturn("Usuario");
        when(jwtService.parseToken(anyString())).thenReturn(claims);
        when(usuarioRemote.obtenerUsuarioPorCI("12345678")).thenReturn(usuarioAInactivar);

        Response response = usuarioResource.inactivarUsuario(usuarioAInactivar, "Bearer token");

        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
        assertEquals("{\"message\":\"Requiere ser Administrador para inactivar usuarios\"}", response.getEntity());
    }

    @Test
    void testObtenerTodosLosUsuarios() {
        List<UsuarioDto> usuarios = List.of(new UsuarioDto(), new UsuarioDto());
        when(usuarioRemote.obtenerUsuarios()).thenReturn(usuarios);

        List<UsuarioDto> result = usuarioResource.obtenerTodosLosUsuarios();

        assertEquals(2, result.size());
    }

    // Agregar más tests según la funcionalidad
}
