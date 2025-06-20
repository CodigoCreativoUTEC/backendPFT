package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.FuncionalidadMapper;
import codigocreativo.uy.servidorapp.dtos.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.entidades.Funcionalidad;
import codigocreativo.uy.servidorapp.entidades.Perfil;
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
import static org.mockito.Mockito.*;

class FuncionalidadBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private FuncionalidadMapper funcionalidadMapper;

    @InjectMocks
    private FuncionalidadBean funcionalidadBean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        funcionalidadBean = new FuncionalidadBean(funcionalidadMapper);

        // Inyectar el EntityManager usando reflexión
        Field emField = FuncionalidadBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(funcionalidadBean, em);
    }

    @Test
    void testObtenerTodas() {
        List<Funcionalidad> funcionalidades = Collections.singletonList(new Funcionalidad());
        List<FuncionalidadDto> funcionalidadesDto = Collections.singletonList(new FuncionalidadDto());

        TypedQuery<Funcionalidad> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT f FROM Funcionalidad f", Funcionalidad.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(funcionalidades);
        when(funcionalidadMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(funcionalidadesDto);

        List<FuncionalidadDto> result = funcionalidadBean.obtenerTodas();

        assertEquals(funcionalidadesDto, result);
        verify(em).createQuery("SELECT f FROM Funcionalidad f", Funcionalidad.class);
        verify(query).getResultList();
    }

    @Test
    void testCrear() {
        Funcionalidad funcionalidad = new Funcionalidad();
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();

        when(funcionalidadMapper.toEntity(any(FuncionalidadDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(funcionalidad);
        when(funcionalidadMapper.toDto(any(Funcionalidad.class), any(CycleAvoidingMappingContext.class))).thenReturn(funcionalidadDto);

        FuncionalidadDto result = funcionalidadBean.crear(funcionalidadDto);

        verify(em).persist(funcionalidad);
        verify(em).flush();
        assertEquals(funcionalidadDto, result);
    }

    @Test
    void testActualizar() {
        Funcionalidad funcionalidadExistente = new Funcionalidad();
        funcionalidadExistente.setId(1L);
        Funcionalidad funcionalidadActualizada = new Funcionalidad();
        funcionalidadActualizada.setId(1L);

        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        funcionalidadDto.setId(1L);

        PerfilDto perfilDto = new PerfilDto();
        perfilDto.setId(2L);

        funcionalidadDto.setPerfiles(Collections.singletonList(perfilDto));

        Perfil perfil = new Perfil();
        perfil.setId(2L);

        when(em.find(Funcionalidad.class, funcionalidadDto.getId())).thenReturn(funcionalidadExistente);
        when(funcionalidadMapper.toEntity(any(FuncionalidadDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(funcionalidadActualizada);
        when(em.find(Perfil.class, perfilDto.getId())).thenReturn(perfil);
        when(funcionalidadMapper.toDto(any(Funcionalidad.class), any(CycleAvoidingMappingContext.class))).thenReturn(funcionalidadDto);

        FuncionalidadDto result = funcionalidadBean.actualizar(funcionalidadDto);

        assertNotNull(result);
        assertEquals(funcionalidadDto, result);
        verify(em).merge(funcionalidadActualizada);
        verify(em).flush();
    }

    @Test
    void testEliminar() {
        Funcionalidad funcionalidad = new Funcionalidad();
        funcionalidad.setId(1L);

        when(em.find(Funcionalidad.class, funcionalidad.getId())).thenReturn(funcionalidad);

        funcionalidadBean.eliminar(funcionalidad.getId());

        verify(em).remove(funcionalidad);
        verify(em).flush();
    }

    @Test
    void testBuscarPorId() {
        Funcionalidad funcionalidad = new Funcionalidad();
        funcionalidad.setId(1L);
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        funcionalidadDto.setId(1L);

        when(em.find(Funcionalidad.class, funcionalidad.getId())).thenReturn(funcionalidad);
        when(funcionalidadMapper.toDto(any(Funcionalidad.class), any(CycleAvoidingMappingContext.class))).thenReturn(funcionalidadDto);

        FuncionalidadDto result = funcionalidadBean.buscarPorId(funcionalidad.getId());

        assertEquals(funcionalidadDto, result);
        verify(em).find(Funcionalidad.class, funcionalidad.getId());
    }
}
