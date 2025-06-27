package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.PaisDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.PaisMapper;
import codigocreativo.uy.servidorapp.entidades.Pais;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.lang.reflect.Field;

class PaisBeanTest {
    @Mock EntityManager em;
    @Mock PaisMapper paisMapper;
    @InjectMocks PaisBean paisBean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Field emField = paisBean.getClass().getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(paisBean, em);
    }

    @Test
    void testCrearPais() {
        PaisDto dto = new PaisDto();
        when(paisMapper.toEntity(dto)).thenReturn(new Pais());
        paisBean.crearPais(dto);
        verify(em).persist(any());
        verify(em).flush();
    }

    @Test
    void testModificarPais() {
        PaisDto dto = new PaisDto();
        when(paisMapper.toEntity(dto)).thenReturn(new Pais());
        paisBean.modificarPais(dto);
        verify(em).merge(any());
        verify(em).flush();
    }

    @Test
    void testObtenerPais() {
        @SuppressWarnings("unchecked")
        TypedQuery<Pais> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Pais.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Pais()));
        when(paisMapper.toDto(anyList())).thenReturn(List.of(new PaisDto()));
        assertNotNull(paisBean.obtenerpais());
    }
} 