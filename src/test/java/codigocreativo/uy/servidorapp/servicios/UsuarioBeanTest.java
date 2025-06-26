package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.UsuarioMapper;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import com.fabdelgado.ciuy.Validator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import jakarta.persistence.TypedQuery;
import codigocreativo.uy.servidorapp.dtos.dtomappers.CycleAvoidingMappingContext;
import java.lang.reflect.Field;
import jakarta.persistence.Query;
import java.time.LocalDate;

class UsuarioBeanTest {
    @Mock EntityManager em;
    @Mock UsuarioMapper usuarioMapper;
    @InjectMocks UsuarioBean usuarioBean;
    private Validator validator;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        Field emField = usuarioBean.getClass().getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(usuarioBean, em);
        validator = new Validator();
    }

    // ========== TESTS PARA CREAR USUARIO ==========

    @Test
    void testCrearUsuarioNulo() {
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(null));
        assertEquals("Usuario nulo", exception.getMessage());
    }

    @Test
    void testCrearUsuarioExitoso() {
        UsuarioDto dto = crearUsuarioDtoValido();
        
        // Mock para todas las validaciones exitosas
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException()); // No existe email ni cédula
        
        // Mock para el merge final
        when(usuarioMapper.toEntity(any(UsuarioDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(new Usuario());
        
        // Ejecutar
        assertDoesNotThrow(() -> usuarioBean.crearUsuario(dto));
        
        // Verificar
        assertEquals(Estados.SIN_VALIDAR, dto.getEstado());
        verify(em).merge(any());
    }

    // ========== TESTS DE VALIDACIÓN DE MAYOR DE EDAD ==========

    @Test
    void testCrearUsuarioFechaNacimientoNula() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setFechaNacimiento(null);
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("La fecha de nacimiento es obligatoria", exception.getMessage());
    }

    @Test
    void testCrearUsuarioMenorDeEdad() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setFechaNacimiento(LocalDate.now().minusYears(17)); // 17 años
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("El usuario debe ser mayor de edad (mínimo 18 años)", exception.getMessage());
    }

    // ========== TESTS DE VALIDACIÓN DE EMAIL ==========

    @Test
    void testCrearUsuarioEmailNulo() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setEmail(null);
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("El email es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearUsuarioEmailVacio() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setEmail("");
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("El email es obligatorio", exception.getMessage());
    }

    @Test
    void testCrearUsuarioEmailDuplicado() {
        UsuarioDto dto = crearUsuarioDtoValido();
        
        // Mock para simular que el email ya existe
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(new Usuario()); // Usuario encontrado
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("Ya existe un usuario con el email: " + dto.getEmail(), exception.getMessage());
    }

    // ========== TESTS DE VALIDACIÓN DE CÉDULA ==========

    @Test
    void testCrearUsuarioCedulaNula() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setCedula(null);
        
        // Mock para email único
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("La cédula es obligatoria", exception.getMessage());
    }

    @Test
    void testCrearUsuarioCedulaVacia() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setCedula("");
        
        // Mock para email único
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("La cédula es obligatoria", exception.getMessage());
    }

    @Test
    void testCrearUsuarioCedulaInvalida() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setCedula("12345"); // Cédula con formato incorrecto (menos de 8 dígitos)
        
        // Mock para email único
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("La cédula no es válida: 12345", exception.getMessage());
    }

    @Test
    void testCrearUsuarioCedulaConLetras() {
        UsuarioDto dto = crearUsuarioDtoValido();
        dto.setCedula("1234567A"); // Cédula con letras (formato incorrecto)
        
        // Mock para email único
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException());
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("La cédula no es válida: 1234567A", exception.getMessage());
    }

    @Test
    void testCrearUsuarioCedulaDuplicada() {
        UsuarioDto dto = crearUsuarioDtoValido();
        
        // Mock para email único
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException()); // Email no existe
        
        // Mock para cédula duplicada
        TypedQuery<Usuario> queryCedulaMock = mock(TypedQuery.class);
        when(em.createQuery(contains("cedula"), eq(Usuario.class))).thenReturn(queryCedulaMock);
        when(queryCedulaMock.setParameter(anyString(), any())).thenReturn(queryCedulaMock);
        when(queryCedulaMock.getSingleResult()).thenReturn(new Usuario()); // Cédula encontrada
        
        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> usuarioBean.crearUsuario(dto));
        assertEquals("Ya existe un usuario con la cédula: " + dto.getCedula(), exception.getMessage());
    }

    @Test
    void testCrearUsuarioCedulaValida() {
        UsuarioDto dto = crearUsuarioDtoValido();
        // Usar una cédula válida generada por la librería CIUY
        String cedulaValida = validator.randomCi();
        dto.setCedula(cedulaValida);
        
        // Mock para todas las validaciones exitosas
        TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(new NoResultException()); // No existe email ni cédula
        
        // Mock para el merge final
        when(usuarioMapper.toEntity(any(UsuarioDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(new Usuario());
        
        // Ejecutar - ahora debería funcionar siempre ya que usamos una cédula válida
        assertDoesNotThrow(() -> usuarioBean.crearUsuario(dto));
        
        // Verificar
        assertEquals(Estados.SIN_VALIDAR, dto.getEstado());
        verify(em).merge(any());
    }

    // ========== TESTS DE MÚLTIPLES CÉDULAS VÁLIDAS ==========

    @Test
    void testCrearUsuarioConDiferentesCedulasValidas() {
        // Probar con múltiples cédulas válidas generadas aleatoriamente
        for (int i = 0; i < 3; i++) { // Reducido de 5 a 3 para optimizar
            UsuarioDto dto = crearUsuarioDtoValido();
            String cedulaValida = validator.randomCi();
            dto.setCedula(cedulaValida);
            
            // Crear nuevos mocks para cada iteración
            TypedQuery<Usuario> queryMock = mock(TypedQuery.class);
            when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(queryMock);
            when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
            when(queryMock.getSingleResult()).thenThrow(new NoResultException());
            
            // Mock para el merge final
            when(usuarioMapper.toEntity(any(UsuarioDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(new Usuario());
            
            // Verificar que la cédula generada es válida
            assertTrue(validator.validateCi(cedulaValida), "La cédula generada debería ser válida: " + cedulaValida);
            
            // Ejecutar
            assertDoesNotThrow(() -> usuarioBean.crearUsuario(dto));
            
            // Verificar
            assertEquals(Estados.SIN_VALIDAR, dto.getEstado());
            verify(em, times(1)).merge(any());
            
            // Reset mocks para la siguiente iteración
            reset(em, usuarioMapper);
        }
    }

    // ========== MÉTODO HELPER ==========

    private UsuarioDto crearUsuarioDtoValido() {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        dto.setNombre("Juan");
        dto.setApellido("Pérez");
        dto.setEmail("juan.perez@test.com");
        dto.setCedula(validator.randomCi()); // Usar cédula válida generada por CIUY
        dto.setFechaNacimiento(LocalDate.now().minusYears(25)); // 25 años
        dto.setContrasenia("password123");
        dto.setEstado(Estados.ACTIVO);
        return dto;
    }

    // ========== TESTS DE OTROS MÉTODOS (MANTENER) ==========

    @Test
    void testModificarUsuario() {
        UsuarioDto dto = new UsuarioDto();
        usuarioBean.modificarUsuario(dto);
        verify(em).merge(any());
        verify(em).flush();
    }

    @Test
    void testEliminarUsuario() {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(1L);
        Query queryMock = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(queryMock);
        when(queryMock.setParameter(anyString(), any())).thenReturn(queryMock);
        when(queryMock.executeUpdate()).thenReturn(1);
        usuarioBean.eliminarUsuario(dto);
        verify(em).createQuery(anyString());
        verify(em).flush();
    }

    @Test
    void testObtenerUsuario() {
        when(em.find(eq(Usuario.class), anyLong())).thenReturn(new Usuario());
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(new UsuarioDto());
        assertNotNull(usuarioBean.obtenerUsuario(1L));
    }

    @Test
    void testObtenerUsuarioDto() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Usuario());
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(new UsuarioDto());
        assertNotNull(usuarioBean.obtenerUsuarioDto(1L));
    }

    @Test
    void testObtenerUsuarioPorCI() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(new Usuario());
        when(usuarioMapper.toDto(any(Usuario.class), any(CycleAvoidingMappingContext.class))).thenReturn(new UsuarioDto());
        assertNotNull(usuarioBean.obtenerUsuarioPorCI("12345678"));
    }

    @Test
    void testObtenerUsuarios() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuarios());
    }

    @Test
    void testObtenerUsuariosFiltrados() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuariosFiltrados("filtro", "valor"));
    }

    @Test
    void testObtenerUsuariosPorEstado() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuariosPorEstado(Estados.ACTIVO));
    }

    @Test
    void testLoginNoResult() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException());
        assertNull(usuarioBean.login("user", "pass"));
    }

    @Test
    void testFindUserByEmailNoResult() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException());
        assertNull(usuarioBean.findUserByEmail("mail"));
    }

    @Test
    void testHasPermissionNoResult() {
        TypedQuery<UsuarioDto> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(UsuarioDto.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(new jakarta.persistence.NoResultException());
        assertFalse(usuarioBean.hasPermission("mail", "role"));
    }

    @Test
    void testObtenerUsuariosFiltrado() {
        TypedQuery<Usuario> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Usuario.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Usuario()));
        when(usuarioMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(List.of(new UsuarioDto()));
        assertNotNull(usuarioBean.obtenerUsuariosFiltrado(Map.of("nombre", "test")));
    }
} 