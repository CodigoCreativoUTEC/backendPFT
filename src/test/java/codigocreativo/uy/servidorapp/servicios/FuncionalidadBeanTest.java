package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtomappers.FuncionalidadMapper;
import codigocreativo.uy.servidorapp.entidades.Funcionalidad;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncionalidadBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private FuncionalidadMapper funcionalidadMapper;

    @InjectMocks
    private FuncionalidadBean funcionalidadBean;

    private FuncionalidadDto funcionalidadDto;
    private Funcionalidad funcionalidad;
    private PerfilDto perfilDto;
    private Perfil perfil;

    @BeforeEach
    void setUp() {
        perfilDto = new PerfilDto();
        perfilDto.setId(1L);
        perfilDto.setNombrePerfil("ADMIN");

        perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNombrePerfil("ADMIN");

        funcionalidadDto = new FuncionalidadDto();
        funcionalidadDto.setId(1L);
        funcionalidadDto.setNombreFuncionalidad("Funcionalidad Test");
        funcionalidadDto.setPerfiles(Collections.singletonList(perfilDto));

        funcionalidad = new Funcionalidad();
        funcionalidad.setId(1L);
        funcionalidad.setNombreFuncionalidad("Funcionalidad Test");
        funcionalidad.setFuncionalidadesPerfiles(new ArrayList<>());
    }

    @Test
void testObtenerTodas() {
    TypedQuery<Funcionalidad> mockedQuery = mock(TypedQuery.class);
    when(em.createQuery("SELECT f FROM Funcionalidad f", Funcionalidad.class)).thenReturn(mockedQuery);
    when(mockedQuery.getResultList()).thenReturn(Collections.singletonList(funcionalidad));
    when(funcionalidadMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(Collections.singletonList(funcionalidadDto));

    List<FuncionalidadDto> result = funcionalidadBean.obtenerTodas();

    assertEquals(1, result.size());
    assertEquals(funcionalidadDto, result.get(0));
}

    @Test
    void testCrear() {
        when(funcionalidadMapper.toEntity(any(FuncionalidadDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(funcionalidad);
        when(funcionalidadMapper.toDto(any(Funcionalidad.class), any(CycleAvoidingMappingContext.class))).thenReturn(funcionalidadDto);

        FuncionalidadDto result = funcionalidadBean.crear(funcionalidadDto);

        verify(em, times(1)).persist(funcionalidad);
        verify(em, times(1)).flush();
        assertEquals(funcionalidadDto, result);
    }

    @Test
    void testActualizar() {
        when(funcionalidadMapper.toEntity(any(FuncionalidadDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(funcionalidad);
        when(em.find(Funcionalidad.class, funcionalidad.getId())).thenReturn(funcionalidad);
        when(em.find(Perfil.class, perfil.getId())).thenReturn(perfil);
        when(funcionalidadMapper.toDto(any(Funcionalidad.class), any(CycleAvoidingMappingContext.class))).thenReturn(funcionalidadDto);

        FuncionalidadDto result = funcionalidadBean.actualizar(funcionalidadDto);

        verify(em, times(1)).merge(funcionalidad);
        verify(em, times(1)).flush();
        assertEquals(funcionalidadDto, result);
    }

    @Test
    void testEliminar() {
        when(em.find(Funcionalidad.class, funcionalidad.getId())).thenReturn(funcionalidad);

        funcionalidadBean.eliminar(funcionalidad.getId());

        verify(em, times(1)).remove(funcionalidad);
        verify(em, times(1)).flush();
    }

    @Test
    void testBuscarPorId() {
        when(em.find(Funcionalidad.class, funcionalidad.getId())).thenReturn(funcionalidad);
        when(funcionalidadMapper.toDto(any(Funcionalidad.class), any(CycleAvoidingMappingContext.class))).thenReturn(funcionalidadDto);

        FuncionalidadDto result = funcionalidadBean.buscarPorId(funcionalidad.getId());

        assertEquals(funcionalidadDto, result);
    }
}