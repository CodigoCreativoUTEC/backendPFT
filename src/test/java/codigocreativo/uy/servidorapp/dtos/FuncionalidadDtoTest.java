package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FuncionalidadDtoTest {

    @Test
    void testEquals_SameObject() {
        FuncionalidadDto dto = new FuncionalidadDto();
        dto.setId(1L);
        dto.setNombreFuncionalidad("Funcionalidad1");
        dto.setRuta("/ruta1");
        dto.setEstado(Estados.ACTIVO);
        assertEquals(dto, dto);
    }

    @Test
    void testEquals_NullObject() {
        FuncionalidadDto dto = new FuncionalidadDto();
        dto.setId(1L);
        dto.setNombreFuncionalidad("Funcionalidad1");
        dto.setRuta("/ruta1");
        dto.setEstado(Estados.ACTIVO);
        assertNotEquals(null, dto);
    }

    @Test
    void testEquals_DifferentClass() {
        FuncionalidadDto dto = new FuncionalidadDto();
        dto.setId(1L);
        dto.setNombreFuncionalidad("Funcionalidad1");
        dto.setRuta("/ruta1");
        dto.setEstado(Estados.ACTIVO);
        assertNotEquals("some string", dto);
    }

    @Test
    void testEquals_DifferentId() {
        FuncionalidadDto dto1 = new FuncionalidadDto();
        dto1.setId(1L);
        dto1.setNombreFuncionalidad("Funcionalidad1");
        dto1.setRuta("/ruta1");
        dto1.setEstado(Estados.ACTIVO);

        FuncionalidadDto dto2 = new FuncionalidadDto();
        dto2.setId(2L);
        dto2.setNombreFuncionalidad("Funcionalidad1");
        dto2.setRuta("/ruta1");
        dto2.setEstado(Estados.ACTIVO);

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentNombreFuncionalidad() {
        FuncionalidadDto dto1 = new FuncionalidadDto();
        dto1.setId(1L);
        dto1.setNombreFuncionalidad("Funcionalidad1");
        dto1.setRuta("/ruta1");
        dto1.setEstado(Estados.ACTIVO);

        FuncionalidadDto dto2 = new FuncionalidadDto();
        dto2.setId(1L);
        dto2.setNombreFuncionalidad("Funcionalidad2");
        dto2.setRuta("/ruta1");
        dto2.setEstado(Estados.ACTIVO);

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentRuta() {
        FuncionalidadDto dto1 = new FuncionalidadDto();
        dto1.setId(1L);
        dto1.setNombreFuncionalidad("Funcionalidad1");
        dto1.setRuta("/ruta1");
        dto1.setEstado(Estados.ACTIVO);

        FuncionalidadDto dto2 = new FuncionalidadDto();
        dto2.setId(1L);
        dto2.setNombreFuncionalidad("Funcionalidad1");
        dto2.setRuta("/ruta2");
        dto2.setEstado(Estados.ACTIVO);

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testEquals_DifferentEstado() {
        FuncionalidadDto dto1 = new FuncionalidadDto();
        dto1.setId(1L);
        dto1.setNombreFuncionalidad("Funcionalidad1");
        dto1.setRuta("/ruta1");
        dto1.setEstado(Estados.ACTIVO);

        FuncionalidadDto dto2 = new FuncionalidadDto();
        dto2.setId(1L);
        dto2.setNombreFuncionalidad("Funcionalidad1");
        dto2.setRuta("/ruta1");
        dto2.setEstado(Estados.INACTIVO);

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testHashCode_SameObject() {
        FuncionalidadDto dto = new FuncionalidadDto();
        dto.setId(1L);
        dto.setNombreFuncionalidad("Funcionalidad1");
        dto.setRuta("/ruta1");
        dto.setEstado(Estados.ACTIVO);
        assertEquals(dto.hashCode(), dto.hashCode());
    }

    @Test
    void testHashCode_DifferentObjects() {
        FuncionalidadDto dto1 = new FuncionalidadDto();
        dto1.setId(1L);
        dto1.setNombreFuncionalidad("Funcionalidad1");
        dto1.setRuta("/ruta1");
        dto1.setEstado(Estados.ACTIVO);

        FuncionalidadDto dto2 = new FuncionalidadDto();
        dto2.setId(2L);
        dto2.setNombreFuncionalidad("Funcionalidad1");
        dto2.setRuta("/ruta1");
        dto2.setEstado(Estados.ACTIVO);

        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        Long id = 1L;
        String nombreFuncionalidad = "Funcionalidad1";
        String ruta = "/ruta1";
        Estados estado = Estados.ACTIVO;
        List<PerfilDto> perfiles = new ArrayList<>();

        FuncionalidadDto dto = new FuncionalidadDto();
        dto.setId(id);
        dto.setNombreFuncionalidad(nombreFuncionalidad);
        dto.setRuta(ruta);
        dto.setEstado(estado);
        dto.setPerfiles(perfiles);

        assertEquals(id, dto.getId());
        assertEquals(nombreFuncionalidad, dto.getNombreFuncionalidad());
        assertEquals(ruta, dto.getRuta());
        assertEquals(estado, dto.getEstado());
        assertEquals(perfiles, dto.getPerfiles());
    }
}