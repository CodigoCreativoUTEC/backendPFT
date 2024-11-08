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
}