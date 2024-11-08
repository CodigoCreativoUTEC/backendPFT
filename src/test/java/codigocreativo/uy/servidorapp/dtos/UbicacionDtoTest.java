package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UbicacionDtoTest {

    @Test
    void testConstructor() {
        UbicacionDto ubicacionDto = new UbicacionDto(1L, "Nombre", "Sector", 2L, 3L, 4L, Estados.ACTIVO);

        assertEquals(1L, ubicacionDto.getId());
        assertEquals("Nombre", ubicacionDto.getNombre());
        assertEquals("Sector", ubicacionDto.getSector());
        assertEquals(2L, ubicacionDto.getPiso());
        assertEquals(3L, ubicacionDto.getNumero());
        assertEquals(4L, ubicacionDto.getCama());
        assertEquals(Estados.ACTIVO, ubicacionDto.getEstado());
    }

    @Test
    void testSetters() {
        UbicacionDto ubicacionDto = new UbicacionDto();
        ubicacionDto.setId(1L)
                .setNombre("Nombre")
                .setSector("Sector")
                .setPiso(2L)
                .setNumero(3L)
                .setCama(4L)
                .setEstado(Estados.ACTIVO);

        assertEquals(1L, ubicacionDto.getId());
        assertEquals("Nombre", ubicacionDto.getNombre());
        assertEquals("Sector", ubicacionDto.getSector());
        assertEquals(2L, ubicacionDto.getPiso());
        assertEquals(3L, ubicacionDto.getNumero());
        assertEquals(4L, ubicacionDto.getCama());
        assertEquals(Estados.ACTIVO, ubicacionDto.getEstado());
    }

    @Test
    void testEqualsAndHashCode() {
        UbicacionDto ubicacionDto1 = new UbicacionDto(1L, "Nombre", "Sector", 2L, 3L, 4L, Estados.ACTIVO);
        UbicacionDto ubicacionDto2 = new UbicacionDto(1L, "Nombre", "Sector", 2L, 3L, 4L, Estados.ACTIVO);

        assertTrue(ubicacionDto1.equals(ubicacionDto2));
        assertEquals(ubicacionDto1.hashCode(), ubicacionDto2.hashCode());
    }

    @Test
    void testToString() {
        UbicacionDto ubicacionDto = new UbicacionDto();
        ubicacionDto.setNombre("Nombre").setSector("Sector");

        assertEquals("Nombre - Sector", ubicacionDto.toString());
    }
}
