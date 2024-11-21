package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtomappers.UsuarioMapper;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UsuarioBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioBean usuarioBean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        usuarioBean = new UsuarioBean(usuarioMapper);

        // Inyectar el EntityManager usando reflexión
        Field emField = UsuarioBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(usuarioBean, em);
    }

    @Test
    void testCrearUsuario() {
        UsuarioDto usuarioDto = new UsuarioDto();
        Usuario usuarioEntity = new Usuario();
        usuarioDto.setEstado(Estados.SIN_VALIDAR);

        when(usuarioMapper.toEntity(eq(usuarioDto), any(CycleAvoidingMappingContext.class))).thenReturn(usuarioEntity);

        usuarioBean.crearUsuario(usuarioDto);

        verify(em).merge(usuarioEntity);
    }

    @Test
    void testModificarUsuario() {
        UsuarioDto usuarioDto = new UsuarioDto();
        Usuario usuarioEntity = new Usuario();

        when(usuarioMapper.toEntity(eq(usuarioDto), any(CycleAvoidingMappingContext.class))).thenReturn(usuarioEntity);

        usuarioBean.modificarUsuario(usuarioDto);

        verify(em).merge(usuarioEntity);
        verify(em).flush();
    }

    @Test
    void testEliminarUsuario() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);

        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery("UPDATE Usuario u SET u.estado = 'INACTIVO' WHERE u.id = :id")).thenReturn(query);
        when(query.setParameter("id", usuarioDto.getId())).thenReturn(query);

        usuarioBean.eliminarUsuario(usuarioDto);

        verify(query).executeUpdate();
        verify(em).flush();
    }


    @Test
    void testObtenerUsuario() {
        Long id = 1L;
        Usuario usuarioEntity = new Usuario();
        UsuarioDto usuarioDto = new UsuarioDto();

        when(em.find(Usuario.class, id)).thenReturn(usuarioEntity);
        when(usuarioMapper.toDto(eq(usuarioEntity), any(CycleAvoidingMappingContext.class))).thenReturn(usuarioDto);

        UsuarioDto result = usuarioBean.obtenerUsuario(id);

        assertNotNull(result);
    }

    @Test
    void testObtenerUsuarioPorCI() {
        String ci = "12345678";
        Usuario usuarioEntity = new Usuario();
        UsuarioDto usuarioDto = new UsuarioDto();

        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.cedula = :ci", Usuario.class)).thenReturn(query);
        when(query.setParameter("ci", ci)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(usuarioEntity);
        when(usuarioMapper.toDto(eq(usuarioEntity), any(CycleAvoidingMappingContext.class))).thenReturn(usuarioDto);

        UsuarioDto result = usuarioBean.obtenerUsuarioPorCI(ci);

        assertNotNull(result);
    }

    @Test
    void testObtenerUsuarios() {
        Usuario usuarioEntity = new Usuario();
        List<Usuario> usuarios = Collections.singletonList(usuarioEntity);

        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u", Usuario.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(usuarios);
        when(usuarioMapper.toDto(eq(usuarios), any(CycleAvoidingMappingContext.class))).thenReturn(Collections.singletonList(new UsuarioDto()));

        List<UsuarioDto> result = usuarioBean.obtenerUsuarios();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testLogin() {
        String email = "test@example.com";
        String password = "password";
        Usuario usuarioEntity = new Usuario();
        UsuarioDto usuarioDto = new UsuarioDto();

        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.email = :usuario AND u.contrasenia = :password", Usuario.class)).thenReturn(query);
        when(query.setParameter("usuario", email)).thenReturn(query);
        when(query.setParameter("password", password)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(usuarioEntity);
        when(usuarioMapper.toDto(eq(usuarioEntity), any(CycleAvoidingMappingContext.class))).thenReturn(usuarioDto);

        UsuarioDto result = usuarioBean.login(email, password);

        assertNotNull(result);
    }

    @Test
    void testFindUserByEmail() {
        String email = "test@example.com";
        Usuario usuarioEntity = new Usuario();
        UsuarioDto usuarioDto = new UsuarioDto();

        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)).thenReturn(query);
        when(query.setParameter("email", email)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(usuarioEntity);
        when(usuarioMapper.toDto(eq(usuarioEntity), any(CycleAvoidingMappingContext.class))).thenReturn(usuarioDto);

        UsuarioDto result = usuarioBean.findUserByEmail(email);

        assertNotNull(result);
    }
}
