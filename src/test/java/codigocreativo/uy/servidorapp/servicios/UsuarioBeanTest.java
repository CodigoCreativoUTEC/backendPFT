package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.UsuarioMapper;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import jakarta.persistence.TypedQuery;
import codigocreativo.uy.servidorapp.dtos.dtomappers.CycleAvoidingMappingContext;
import java.lang.reflect.Field;
import jakarta.persistence.Query;

class UsuarioBeanTest {
    @Mock EntityManager em;
    @Mock UsuarioMapper usuarioMapper;
    @InjectMocks UsuarioBean usuarioBean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Field emField = usuarioBean.getClass().getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(usuarioBean, em);
    }

    @Test
    void testCrearUsuario() {
        UsuarioDto dto = new UsuarioDto();
        usuarioBean.crearUsuario(dto);
        assertEquals(Estados.SIN_VALIDAR, dto.getEstado());
        verify(em).merge(any());
    }

    @Test
    void testModificarUsuario() {
        UsuarioDto dto = new UsuarioDto();
        usuarioBean.modificarUsuario(dto);
        verify(em).merge(any());
        verify(em).flush();
    }

    @Test
    void testEliminarUsuario() {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        Query queryMock = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.executeUpdate()).thenReturn(1);
        usuarioBean.eliminarUsuario(dto);
        verify(em).createQuery(anyString());
        verify(em).flush();
    }

    @Test
    void testObtenerUsuario() {
        when(em.find(eq(Usuario.class), anyLong())).thenReturn(new Usuario());
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(new UsuarioDto());
        assertNotNull(usuarioBean.obtenerUsuario(1L));
    }

    @Test
    void testObtenerUsuarioDto() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Usuario());
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(new UsuarioDto());
        assertNotNull(usuarioBean.obtenerUsuarioDto(1L));
    }

    @Test
    void testObtenerUsuarioPorCI() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Usuario());
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(new UsuarioDto());
        assertNotNull(usuarioBean.obtenerUsuarioPorCI("12345678"));
    }

    @Test
    void testObtenerUsuarios() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuarios());
    }

    @Test
    void testObtenerUsuariosFiltrados() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuariosFiltrados("filtro", "valor"));
    }

    @Test
    void testObtenerUsuariosPorEstado() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuariosPorEstado(Estados.ACTIVO));
    }

    @Test
    void testLoginNoResult() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException());
        assertNull(usuarioBean.login("user", "pass"));
    }

    @Test
    void testFindUserByEmailNoResult() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException());
        assertNull(usuarioBean.findUserByEmail("mail"));
    }

    @Test
    void testHasPermissionNoResult() {
        TypedQuery<UsuarioDto> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(UsuarioDto.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException());
        assertFalse(usuarioBean.hasPermission("mail", "role"));
    }

    @Test
    void testObtenerUsuariosFiltrado() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuariosFiltrado(Map.of("nombre", "test")));
    }
} 