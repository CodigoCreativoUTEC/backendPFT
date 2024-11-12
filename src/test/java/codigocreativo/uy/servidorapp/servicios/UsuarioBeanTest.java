package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtomappers.UsuarioMapper;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioBean usuarioBean;

    private UsuarioDto usuarioDto;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setEmail("test@example.com");
        usuarioDto.setEstado(Estados.ACTIVO);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@example.com");
        usuario.setEstado(Estados.ACTIVO);
    }

    @Test
    void testCrearUsuario() {
        when(usuarioMapper.toEntity(any(UsuarioDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(usuario);

        usuarioBean.crearUsuario(usuarioDto);

        verify(em, times(1)).merge(usuario);
        assertEquals(Estados.SIN_VALIDAR, usuarioDto.getEstado());
    }

    @Test
    void testModificarUsuario() {
        when(usuarioMapper.toEntity(any(UsuarioDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(usuario);

        usuarioBean.modificarUsuario(usuarioDto);

        verify(em, times(1)).merge(usuario);
        verify(em, times(1)).flush();
    }

    @Test
    void testEliminarUsuario() {
        Query mockedQuery = mock(Query.class);
        when(em.createQuery("UPDATE Usuario u SET u.estado = 'INACTIVO' WHERE u.id = :id")).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("id", usuarioDto.getId())).thenReturn(mockedQuery);

        usuarioBean.eliminarUsuario(usuarioDto);

        verify(em, times(1)).createQuery("UPDATE Usuario u SET u.estado = 'INACTIVO' WHERE u.id = :id");
        verify(mockedQuery, times(1)).setParameter("id", usuarioDto.getId());
        verify(mockedQuery, times(1)).executeUpdate();
        verify(em, times(1)).flush();
    }

    @Test
    void testObtenerUsuario() {
        when(em.find(Usuario.class, 1L)).thenReturn(usuario);
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(usuarioDto);

        UsuarioDto result = usuarioBean.obtenerUsuario(1L);

        assertEquals(usuarioDto, result);
    }

    @Test
    void testObtenerUsuarioDto() {
        TypedQuery<Usuario> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.id = :id", Usuario.class)).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("id", 1L)).thenReturn(mockedQuery);
        when(mockedQuery.getSingleResult()).thenReturn(usuario);
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(usuarioDto);

        UsuarioDto result = usuarioBean.obtenerUsuarioDto(1L);

        assertEquals(usuarioDto, result);
    }

    @Test
    void testObtenerUsuarioPorCI() {
        TypedQuery<Usuario> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.cedula = :ci", Usuario.class)).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("ci", "12345678")).thenReturn(mockedQuery);
        when(mockedQuery.getSingleResult()).thenReturn(usuario);
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(usuarioDto);

        UsuarioDto result = usuarioBean.obtenerUsuarioPorCI("12345678");

        assertEquals(usuarioDto, result);
    }

    @Test
    void testObtenerUsuarios() {
        TypedQuery<Usuario> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u", Usuario.class)).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.singletonList(usuario));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(Collections.singletonList(usuarioDto));

        List<UsuarioDto> result = usuarioBean.obtenerUsuarios();

        assertEquals(1, result.size());
        assertEquals(usuarioDto, result.get(0));
    }

    @Test
    void testObtenerUsuariosFiltrados() {
        TypedQuery<Usuario> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.email = :valor", Usuario.class)).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("valor", "test@example.com")).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.singletonList(usuario));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(Collections.singletonList(usuarioDto));

        List<UsuarioDto> result = usuarioBean.obtenerUsuariosFiltrados("email", "test@example.com");

        assertEquals(1, result.size());
        assertEquals(usuarioDto, result.get(0));
    }

    @Test
    void testObtenerUsuariosPorEstado() {
        TypedQuery<Usuario> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.estado = :estado", Usuario.class)).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("estado", Estados.ACTIVO)).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.singletonList(usuario));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(Collections.singletonList(usuarioDto));

        List<UsuarioDto> result = usuarioBean.obtenerUsuariosPorEstado(Estados.ACTIVO);

        assertEquals(1, result.size());
        assertEquals(usuarioDto, result.get(0));
    }

    @Test
    void testLogin() {
        TypedQuery<Usuario> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.email = :usuario AND u.contrasenia = :password", Usuario.class)).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("usuario", "test@example.com")).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("password", "password")).thenReturn(mockedQuery);
        when(mockedQuery.getSingleResult()).thenReturn(usuario);
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(usuarioDto);

        UsuarioDto result = usuarioBean.login("test@example.com", "password");

        assertEquals(usuarioDto, result);
    }

    @Test
    void testFindUserByEmail() {
        TypedQuery<Usuario> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("email", "test@example.com")).thenReturn(mockedQuery);
        when(mockedQuery.getSingleResult()).thenReturn(usuario);
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(usuarioDto);

        UsuarioDto result = usuarioBean.findUserByEmail("test@example.com");

        assertEquals(usuarioDto, result);
    }

    @Test
    void testHasPermission() {
        TypedQuery<UsuarioDto> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", UsuarioDto.class)).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("email", "test@example.com")).thenReturn(mockedQuery);
        when(mockedQuery.getSingleResult()).thenReturn(usuarioDto);
        usuarioDto.setIdPerfil(new PerfilDto(1L, "ADMIN", Estados.ACTIVO));

        boolean result = usuarioBean.hasPermission("test@example.com", "ADMIN");

        assertTrue(result);
    }
    @Test
    void testObtenerUsuariosFiltrado() {
        TypedQuery<Usuario> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.singletonList(usuario));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(Collections.singletonList(usuarioDto));

        Map<String, String> filtros = Map.of("nombre", "test", "apellido", "example");
        List<UsuarioDto> result = usuarioBean.obtenerUsuariosFiltrado(filtros);

        assertEquals(1, result.size());
        assertEquals(usuarioDto, result.get(0));
    }
}