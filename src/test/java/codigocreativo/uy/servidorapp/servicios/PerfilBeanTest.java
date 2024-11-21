package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtomappers.PerfilMapper;
import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

class PerfilBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private PerfilMapper perfilMapper;

    @InjectMocks
    private PerfilBean perfilBean;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        perfilBean = new PerfilBean(perfilMapper);

        // Use reflection to set the private EntityManager field
        Field emField = PerfilBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(perfilBean, em);
    }

    @Test
    void testCrearPerfil() {
        PerfilDto perfilDto = new PerfilDto();
        perfilDto.setEstado(Estados.ACTIVO);
        Perfil perfilEntity = new Perfil();

        when(perfilMapper.toEntity(perfilDto)).thenReturn(perfilEntity);

        perfilBean.crearPerfil(perfilDto);

        verify(em, times(1)).persist(perfilEntity);
        verify(em, times(1)).flush();
    }

    @Test
    void testModificarPerfil() {
        PerfilDto perfilDto = new PerfilDto();
        Perfil perfilEntity = new Perfil();

        when(perfilMapper.toEntity(perfilDto)).thenReturn(perfilEntity);

        perfilBean.modificarPerfil(perfilDto);

        verify(em, times(1)).merge(perfilEntity);
        verify(em, times(1)).flush();
    }

    @Test
    void testEliminarPerfil() {
        PerfilDto perfilDto = new PerfilDto();
        Perfil perfilEntity = new Perfil();

        when(perfilMapper.toEntity(perfilDto)).thenReturn(perfilEntity);

        perfilBean.eliminarPerfil(perfilDto);

        assertEquals(Estados.INACTIVO, perfilEntity.getEstado());
        verify(em, times(1)).merge(perfilEntity);
    }

    @Test
    void testObtenerPerfil() {
        Long id = 1L;
        Perfil perfilEntity = new Perfil();
        PerfilDto perfilDto = new PerfilDto();

        when(em.find(Perfil.class, id)).thenReturn(perfilEntity);
        when(perfilMapper.toDto(perfilEntity)).thenReturn(perfilDto);

        PerfilDto result = perfilBean.obtenerPerfil(id);

        assertEquals(perfilDto, result);
        verify(em, times(1)).find(Perfil.class, id);
    }

    @Test
    void testObtenerPerfiles() {
        List<Perfil> perfilesEntity = Arrays.asList(new Perfil(), new Perfil());
        List<PerfilDto> perfilesDto = Arrays.asList(new PerfilDto(), new PerfilDto());

        TypedQuery<Perfil> query = mock(TypedQuery.class);

        when(em.createQuery("SELECT p FROM Perfil p", Perfil.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(perfilesEntity);
        when(perfilMapper.toDtoList(perfilesEntity)).thenReturn(perfilesDto);

        List<PerfilDto> result = perfilBean.obtenerPerfiles();

        assertEquals(perfilesDto, result);
        verify(em, times(1)).createQuery("SELECT p FROM Perfil p", Perfil.class);
        verify(query, times(1)).getResultList();
    }

    @Test
    void testListarPerfilesPorNombre() {
        String nombre = "Admin";
        List<Perfil> perfilesEntity = Arrays.asList(new Perfil(), new Perfil());
        List<PerfilDto> perfilesDto = Arrays.asList(new PerfilDto(), new PerfilDto());

        TypedQuery<Perfil> query = mock(TypedQuery.class);

        when(em.createQuery("SELECT p FROM Perfil p WHERE p.nombrePerfil LIKE :nombre", Perfil.class)).thenReturn(query);
        when(query.setParameter("nombre", "%" + nombre + "%")).thenReturn(query);
        when(query.getResultList()).thenReturn(perfilesEntity);
        when(perfilMapper.toDtoList(perfilesEntity)).thenReturn(perfilesDto);

        List<PerfilDto> result = perfilBean.listarPerfilesPorNombre(nombre);

        assertEquals(perfilesDto, result);
    }

    @Test
    void testListarPerfilesPorEstado() {
        Estados estado = Estados.ACTIVO;
        List<Perfil> perfilesEntity = Arrays.asList(new Perfil(), new Perfil());
        List<PerfilDto> perfilesDto = Arrays.asList(new PerfilDto(), new PerfilDto());

        TypedQuery<Perfil> query = mock(TypedQuery.class);

        when(em.createQuery("SELECT p FROM Perfil p WHERE p.estado = :estado", Perfil.class)).thenReturn(query);
        when(query.setParameter("estado", estado)).thenReturn(query);
        when(query.getResultList()).thenReturn(perfilesEntity);
        when(perfilMapper.toDtoList(perfilesEntity)).thenReturn(perfilesDto);

        List<PerfilDto> result = perfilBean.listarPerfilesPorEstado(estado);

        assertEquals(perfilesDto, result);
    }
}