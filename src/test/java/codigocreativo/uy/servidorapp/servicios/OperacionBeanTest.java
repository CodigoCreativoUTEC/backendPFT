package codigocreativo.uy.servidorapp.servicios;

import static org.mockito.Mockito.*;
import codigocreativo.uy.servidorapp.dtomappers.OperacionMapper;
import codigocreativo.uy.servidorapp.dtos.OperacionDto;
import codigocreativo.uy.servidorapp.entidades.Operacion;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OperacionBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private OperacionMapper operacionMapper;

    @InjectMocks
    private OperacionBean operacionBean;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearOperacion() {
        OperacionDto operacionDto = new OperacionDto();
        Operacion operacionEntity = new Operacion();

        when(operacionMapper.toEntity(operacionDto)).thenReturn(operacionEntity);

        operacionBean.crearOperacion(operacionDto);

        verify(em, times(1)).persist(eq(operacionEntity));
        verify(em, times(1)).flush();
    }

    @Test
    void testModificarOperacion() {
        OperacionDto operacionDto = new OperacionDto();
        Operacion operacionEntity = new Operacion();

        when(operacionMapper.toEntity(operacionDto)).thenReturn(operacionEntity);

        operacionBean.modificarOperacion(operacionDto);

        verify(em, times(1)).merge(eq(operacionEntity));
        verify(em, times(1)).flush();
    }

    @Test
    void testEliminarOperacion() {
        OperacionDto operacionDto = new OperacionDto();
        Operacion operacionEntity = new Operacion();

        when(operacionMapper.toEntity(operacionDto)).thenReturn(operacionEntity);

        operacionBean.eliminarOperacion(operacionDto);

        verify(em, times(1)).remove(eq(operacionEntity));
        verify(em, times(1)).flush();
    }
}