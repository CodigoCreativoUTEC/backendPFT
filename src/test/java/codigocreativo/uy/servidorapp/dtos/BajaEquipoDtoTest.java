package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class BajaEquipoDtoTest {

    @Test
    void testConstructor() {
        BajaEquipoDto bajaEquipoDto = new BajaEquipoDto(1L, "Razon", LocalDate.of(2023, 1, 1), new UsuarioDto(), new EquipoDto(), Estados.ACTIVO, "Comentarios");

        assertEquals(1L, bajaEquipoDto.getId());
        assertEquals("Razon", bajaEquipoDto.getRazon());
        assertEquals(LocalDate.of(2023, 1, 1), bajaEquipoDto.getFecha());
        assertNotNull(bajaEquipoDto.getIdUsuario());
        assertNotNull(bajaEquipoDto.getIdEquipo());
        assertEquals(Estados.ACTIVO, bajaEquipoDto.getEstado());
        assertEquals("Comentarios", bajaEquipoDto.getComentarios());
    }

    @Test
    void testSetters() {
        BajaEquipoDto bajaEquipoDto = new BajaEquipoDto();
        bajaEquipoDto.setId(1L)
                .setRazon("Razon")
                .setFecha(LocalDate.of(2023, 1, 1))
                .setIdUsuario(new UsuarioDto())
                .setIdEquipo(new EquipoDto())
                .setEstado(Estados.ACTIVO)
                .setComentarios("Comentarios");

        assertEquals(1L, bajaEquipoDto.getId());
        assertEquals("Razon", bajaEquipoDto.getRazon());
        assertEquals(LocalDate.of(2023, 1, 1), bajaEquipoDto.getFecha());
        assertNotNull(bajaEquipoDto.getIdUsuario());
        assertNotNull(bajaEquipoDto.getIdEquipo());
        assertEquals(Estados.ACTIVO, bajaEquipoDto.getEstado());
        assertEquals("Comentarios", bajaEquipoDto.getComentarios());
    }

    @Test
    void testEqualsAndHashCode() {
        BajaEquipoDto bajaEquipoDto1 = new BajaEquipoDto(1L, "Razon", LocalDate.of(2023, 1, 1), new UsuarioDto(), new EquipoDto(), Estados.ACTIVO, "Comentarios");
        BajaEquipoDto bajaEquipoDto2 = new BajaEquipoDto(1L, "Razon", LocalDate.of(2023, 1, 1), new UsuarioDto(), new EquipoDto(), Estados.ACTIVO, "Comentarios");

        assertTrue(bajaEquipoDto1.equals(bajaEquipoDto2));
        assertEquals(bajaEquipoDto1.hashCode(), bajaEquipoDto2.hashCode());
    }

    @Test
    void testToString() {
        BajaEquipoDto bajaEquipoDto = new BajaEquipoDto();
        bajaEquipoDto.setId(1L).setRazon("Razon").setFecha(LocalDate.of(2023, 1, 1)).setEstado(Estados.ACTIVO).setComentarios("Comentarios");

        String expected = "BajaEquipoDto(id = 1, razon = Razon, fecha = 2023-01-01, estado = Activo, comentarios = Comentarios)";
        assertEquals(expected, bajaEquipoDto.toString());
    }
}
