package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDtoTest {

    @Test
    void testBuilder() {
        UsuarioDto usuarioDto = new UsuarioDto.Builder()
                .setId(1L)
                .setCedula("12345678")
                .setEmail("test@example.com")
                .setContrasenia("password")
                .setFechaNacimiento(LocalDate.of(1990, 1, 1))
                .setEstado(Estados.ACTIVO)
                .setNombre("John")
                .setApellido("Doe")
                .setNombreUsuario("johndoe")
                .setIdInstitucion(new InstitucionDto())
                .setIdPerfil(new PerfilDto())
                .setUsuariosTelefonos(new LinkedHashSet<>())
                .build();

        assertEquals(1L, usuarioDto.getId());
        assertEquals("12345678", usuarioDto.getCedula());
        assertEquals("test@example.com", usuarioDto.getEmail());
        assertEquals("password", usuarioDto.getContrasenia());
        assertEquals(LocalDate.of(1990, 1, 1), usuarioDto.getFechaNacimiento());
        assertEquals(Estados.ACTIVO, usuarioDto.getEstado());
        assertEquals("John", usuarioDto.getNombre());
        assertEquals("Doe", usuarioDto.getApellido());
        assertEquals("johndoe", usuarioDto.getNombreUsuario());
        assertNotNull(usuarioDto.getIdInstitucion());
        assertNotNull(usuarioDto.getIdPerfil());
        assertNotNull(usuarioDto.getUsuariosTelefonos());
    }

    @Test
    void testEqualsAndHashCode() {
        UsuarioDto usuarioDto1 = new UsuarioDto.Builder()
                .setId(1L)
                .setCedula("12345678")
                .setEmail("test@example.com")
                .setContrasenia("password123")
                .setFechaNacimiento(LocalDate.of(1990, 1, 1))
                .setEstado(Estados.ACTIVO)
                .setNombre("John")
                .setApellido("Doe")
                .setNombreUsuario("johndoe")
                .build();

        UsuarioDto usuarioDto2 = new UsuarioDto.Builder()
                .setId(1L)
                .setCedula("12345678")
                .setEmail("test@example.com")
                .setContrasenia("password123")
                .setFechaNacimiento(LocalDate.of(1990, 1, 1))
                .setEstado(Estados.ACTIVO)
                .setNombre("John")
                .setApellido("Doe")
                .setNombreUsuario("johndoe")
                .build();

        assertEquals(usuarioDto1, usuarioDto2);
        assertEquals(usuarioDto1.hashCode(), usuarioDto2.hashCode());
    }

    @Test
    void testSetters() {
        UsuarioDto usuarioDto = getUsuarioDto();

        assertEquals(1L, usuarioDto.getId());
        assertEquals("12345678", usuarioDto.getCedula());
        assertEquals("test@example.com", usuarioDto.getEmail());
        assertEquals("password", usuarioDto.getContrasenia());
        assertEquals(LocalDate.of(1990, 1, 1), usuarioDto.getFechaNacimiento());
        assertEquals(Estados.ACTIVO, usuarioDto.getEstado());
        assertEquals("John", usuarioDto.getNombre());
        assertEquals("Doe", usuarioDto.getApellido());
        assertEquals("johndoe", usuarioDto.getNombreUsuario());
        assertNotNull(usuarioDto.getIdInstitucion());
        assertNotNull(usuarioDto.getIdPerfil());
        assertNotNull(usuarioDto.getUsuariosTelefonos());
    }

    private static UsuarioDto getUsuarioDto() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);
        usuarioDto.setEmail("test@example.com");
        usuarioDto.setContrasenia("password");
        usuarioDto.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        usuarioDto.setEstado(Estados.ACTIVO);
        usuarioDto.setNombre("John");
        usuarioDto.setApellido("Doe");
        usuarioDto.setNombreUsuario("johndoe");
        usuarioDto.setIdInstitucion(new InstitucionDto());
        usuarioDto.setIdPerfil(new PerfilDto());
        usuarioDto.setUsuariosTelefonos(new LinkedHashSet<>());
        usuarioDto.setCedula("12345678");
        return usuarioDto;
    }

    @Test
    void testToString() {
        UsuarioDto usuarioDto = new UsuarioDto.Builder()
                .setNombre("John")
                .setApellido("Doe")
                .build();

        assertEquals("John Doe", usuarioDto.toString());
    }
}
