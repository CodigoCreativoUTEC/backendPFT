package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.ProveedoresEquipoMapper;
import codigocreativo.uy.servidorapp.entidades.ProveedoresEquipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.lang.reflect.Field;

class ProveedoresEquipoBeanTest {
    @Mock EntityManager em;
    @Mock ProveedoresEquipoMapper proveedoresEquipoMapper;
    @InjectMocks ProveedoresEquipoBean bean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Field emField = bean.getClass().getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(bean, em);
    }

    @Test
    void testCrearProveedor() {
        ProveedoresEquipoDto dto = new ProveedoresEquipoDto();
        when(proveedoresEquipoMapper.toEntity(dto)).thenReturn(new ProveedoresEquipo());
        bean.crearProveedor(dto);
        assertEquals(Estados.ACTIVO, dto.getEstado());
        verify(em).persist(any());
        verify(em).flush();
    }

    @Test
    void testModificarProveedor() {
        ProveedoresEquipoDto dto = new ProveedoresEquipoDto();
        when(proveedoresEquipoMapper.toEntity(dto)).thenReturn(new ProveedoresEquipo());
        bean.modificarProveedor(dto);
        verify(em).merge(any());
        verify(em).flush();
    }

    @Test
    void testObtenerProveedor() {
        when(em.find(eq(ProveedoresEquipo.class), anyLong())).thenReturn(new ProveedoresEquipo());
        when(proveedoresEquipoMapper.toDto(any(ProveedoresEquipo.class))).thenReturn(new ProveedoresEquipoDto());
        assertNotNull(bean.obtenerProveedor(1L));
    }

    @Test
    void testObtenerProveedores() {
        TypedQuery<ProveedoresEquipo> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ProveedoresEquipo.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new ProveedoresEquipo()));
        when(proveedoresEquipoMapper.toDto(anyList())).thenReturn(List.of(new ProveedoresEquipoDto()));
        assertNotNull(bean.obtenerProveedores());
    }

    @Test
    void testEliminarProveedor() {
        Query queryMock = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.executeUpdate()).thenReturn(1);
        bean.eliminarProveedor(1L);
        verify(em).createQuery(anyString());
        verify(em).flush();
    }

    @Test
    void testBuscarProveedores() {
        TypedQuery<ProveedoresEquipo> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(ProveedoresEquipo.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new ProveedoresEquipo()));
        when(proveedoresEquipoMapper.toDto(anyList())).thenReturn(List.of(new ProveedoresEquipoDto()));
        assertNotNull(bean.buscarProveedores("nombre", Estados.ACTIVO));
    }
} 