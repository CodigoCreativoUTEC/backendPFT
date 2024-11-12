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

@ExtendWith(MockitoExtension.class) // Agregar esto para usar el MockitoExtension
class OperacionBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private OperacionMapper operacionMapper;

    @InjectMocks
    private OperacionBean operacionBean;

    @Mock
    private OperacionDto operacionDto;

    @BeforeEach
    void setUp() {
        // Inicialización de los mocks
        MockitoAnnotations.openMocks(this); // Si no usas @ExtendWith(MockitoExtension.class)
    }

    @Test
    void testCrearOperacion() {
        OperacionDto operacionDtoT = new OperacionDto();
        Operacion operacionEntity = new Operacion();

        when(operacionMapper.toEntity(operacionDtoT)).thenReturn(operacionEntity);

        operacionBean.crearOperacion(operacionDtoT);

        verify(em, times(1)).persist(operacionEntity); // Verificar que persist fue llamado
        verify(em, times(1)).flush(); // Verificar que flush fue llamado
    }

    @Test
    void testModificarOperacion() {
        OperacionDto operacionDtoT = new OperacionDto();
        Operacion operacionEntity = new Operacion();

        when(operacionMapper.toEntity(operacionDtoT)).thenReturn(operacionEntity);

        operacionBean.modificarOperacion(operacionDtoT);

        verify(em, times(1)).merge(operacionEntity); // Verificar que merge fue llamado
        verify(em, times(1)).flush(); // Verificar que flush fue llamado
    }

    @Test
    void testEliminarOperacion() {
        Operacion operacionEntity = new Operacion();

        // Mockear el comportamiento de 'operacionMapper.toEntity'
        when(operacionMapper.toEntity(operacionDto)).thenReturn(operacionEntity);

        // Llamar al método de la clase OperacionBean
        operacionBean.eliminarOperacion(operacionDto);

        // Verificar que las operaciones sobre el EntityManager se ejecuten
        verify(em, times(1)).remove(operacionEntity); // Verificar que remove fue llamado
        verify(em, times(1)).flush(); // Verificar que flush fue llamado
    }
}
