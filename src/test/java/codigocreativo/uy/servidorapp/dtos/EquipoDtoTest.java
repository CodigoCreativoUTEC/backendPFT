package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

class EquipoDtoTest {

    @Test
    void testBuilder() {
        EquipoDto equipoDto = new EquipoDto.Builder()
                .id(1L)
                .idInterno("idInterno")
                .nroSerie("nroSerie")
                .garantia("garantia")
                .idTipo(new TiposEquipoDto())
                .idProveedor(new ProveedoresEquipoDto())
                .idPais(new PaisDto())
                .idModelo(new ModelosEquipoDto())
                .equiposUbicaciones(new LinkedHashSet<>())
                .idUbicacion(new UbicacionDto())
                .nombre("nombre")
                .imagen("imagen")
                .fechaAdquisicion(LocalDate.now())
                .estado(Estados.ACTIVO)
                .build();

        assertEquals(1L, equipoDto.getId());
        assertEquals("idInterno", equipoDto.getIdInterno());
        assertEquals(new TiposEquipoDto(), equipoDto.getIdTipo());
        assertEquals(new ProveedoresEquipoDto(), equipoDto.getIdProveedor());
        assertEquals(new PaisDto(), equipoDto.getIdPais());
        assertEquals(new ModelosEquipoDto(), equipoDto.getIdModelo());
        assertEquals(new LinkedHashSet<>(), equipoDto.getEquiposUbicaciones());
        assertEquals(new UbicacionDto(), equipoDto.getIdUbicacion());
        assertEquals("garantia", equipoDto.getGarantia());
        assertEquals("nroSerie", equipoDto.getNroSerie());
        assertEquals("garantia", equipoDto.getGarantia());
        assertNotNull(equipoDto.getIdTipo());
        assertNotNull(equipoDto.getIdProveedor());
        assertNotNull(equipoDto.getIdPais());
        assertNotNull(equipoDto.getIdModelo());
        assertNotNull(equipoDto.getEquiposUbicaciones());
        assertNotNull(equipoDto.getIdUbicacion());
        assertEquals("nombre", equipoDto.getNombre());
        assertEquals("imagen", equipoDto.getImagen());
        assertEquals(LocalDate.now(), equipoDto.getFechaAdquisicion());
        assertEquals(Estados.ACTIVO, equipoDto.getEstado());
    }

    @Test
    void testSetters() {
        EquipoDto equipoDto = new EquipoDto();
        equipoDto.setIdInterno("idInterno")
                .setNroSerie("nroSerie")
                .setNombre("nombre")
                .setImagen("imagen")
                .setFechaAdquisicion(LocalDate.now())
                .setEstado(Estados.ACTIVO)
                .setIdTipo(new TiposEquipoDto())
                .setIdProveedor(new ProveedoresEquipoDto())
                .setIdPais(new PaisDto())
                .setIdModelo(new ModelosEquipoDto())
                .setEquiposUbicaciones(new LinkedHashSet<>())
                .setIdUbicacion(new UbicacionDto())
                .setGarantia("garantia");
        EquipoDto equipoDto2 = new EquipoDto();
        equipoDto2.setIdInterno("idInterno")
                .setNroSerie("nroSerie")
                .setNombre("nombre")
                .setImagen("imagen")
                .setFechaAdquisicion(LocalDate.now())
                .setEstado(Estados.ACTIVO);

        assertEquals("idInterno", equipoDto2.getIdInterno());
        assertEquals("nroSerie", equipoDto2.getNroSerie());
        assertEquals("nombre", equipoDto2.getNombre());
        assertEquals("imagen", equipoDto2.getImagen());
        assertEquals(LocalDate.now(), equipoDto2.getFechaAdquisicion());
        assertEquals(Estados.ACTIVO, equipoDto2.getEstado());
    }

    @Test
    void testEqualsAndHashCode() {
        EquipoDto equipoDto1 = new EquipoDto.Builder()
                .id(1L)
                .idInterno("idInterno")
                .nroSerie("nroSerie")
                .nombre("nombre")
                .imagen("imagen")
                .fechaAdquisicion(LocalDate.now())
                .estado(Estados.ACTIVO)
                .build();

        EquipoDto equipoDto2 = new EquipoDto.Builder()
                .id(1L)
                .idInterno("idInterno")
                .nroSerie("nroSerie")
                .nombre("nombre")
                .imagen("imagen")
                .fechaAdquisicion(LocalDate.now())
                .estado(Estados.ACTIVO)
                .build();

        EquipoDto equipoDto3 = new EquipoDto.Builder()
                .id(2L)
                .idInterno("diferenteIdInterno")
                .nroSerie("diferenteNroSerie")
                .nombre("otroNombre")
                .imagen("otraImagen")
                .fechaAdquisicion(LocalDate.now().minusDays(1))
                .estado(Estados.INACTIVO)
                .build();

        assertEquals(equipoDto1, equipoDto2);
        assertEquals(equipoDto1.hashCode(), equipoDto2.hashCode());
        assertNotEquals(equipoDto1, equipoDto3);
    }

    @Test
    void testToString() {
        EquipoDto equipoDto = new EquipoDto.Builder()
                .idInterno("idInterno")
                .nombre("nombre")
                .build();

        assertEquals("idInterno - nombre", equipoDto.toString());
    }
}
