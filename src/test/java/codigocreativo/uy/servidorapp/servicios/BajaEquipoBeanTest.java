package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.BajaEquipoMapper;
import codigocreativo.uy.servidorapp.dtos.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BajaEquipoBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private BajaEquipoMapper bajaEquipoMapper;

    @Mock
    private Query query;

    @InjectMocks
    private BajaEquipoBean bajaEquipoBean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        
        // Crear la instancia manualmente con el mapper mockeado
        bajaEquipoBean = new BajaEquipoBean(bajaEquipoMapper);

        // Inyectar el EntityManager usando reflexión
        Field emField = BajaEquipoBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(bajaEquipoBean, em);
    }

    @Test
    void testCrearBajaEquipo_Success() throws ServiciosException {
        BajaEquipoDto bajaEquipoDto = crearBajaEquipoDtoValido();
        BajaEquipo bajaEquipoEntity = new BajaEquipo();
        bajaEquipoEntity.setId(1L);
        bajaEquipoEntity.setRazon("Equipo obsoleto");
        bajaEquipoEntity.setFecha(LocalDate.now());
        bajaEquipoEntity.setEstado("INACTIVO");
        
        Equipo equipo = new Equipo();
        equipo.setId(1L);
        
        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");
        
        // Crear un perfil para el usuario
        Perfil perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNombrePerfil("Usuario");
        usuario.setIdPerfil(perfil);

        // Mock para verificar que no existe baja previa
        Query countQuery = mock(Query.class);
        when(em.createQuery("SELECT COUNT(b) FROM BajaEquipo b WHERE b.idEquipo.id = :idEquipo")).thenReturn(countQuery);
        when(countQuery.setParameter("idEquipo", 1L)).thenReturn(countQuery);
        when(countQuery.getSingleResult()).thenReturn(0L);

        // Mock para obtener equipo
        when(em.find(Equipo.class, 1L)).thenReturn(equipo);

        // Mock para obtener usuario
        Query userQuery = mock(Query.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email")).thenReturn(userQuery);
        when(userQuery.setParameter("email", "test@test.com")).thenReturn(userQuery);
        when(userQuery.getSingleResult()).thenReturn(usuario);

        // Mock para mapper - retornar una entidad válida
        when(bajaEquipoMapper.toEntity(any(BajaEquipoDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(bajaEquipoEntity);

        assertDoesNotThrow(() -> bajaEquipoBean.crearBajaEquipo(bajaEquipoDto, "test@test.com"));

        verify(em).persist(bajaEquipoEntity);
        verify(em, times(2)).flush();
        verify(em).merge(equipo);
    }

    @Test
    void testCrearBajaEquipo_WithNullBajaEquipo() {
        assertThrows(ServiciosException.class, () -> bajaEquipoBean.crearBajaEquipo(null, "test@test.com"));
        verify(em, never()).persist(any());
        verify(em, never()).flush();
    }

    @Test
    void testCrearBajaEquipo_WithNullEmailUsuario() {
        BajaEquipoDto bajaEquipoDto = crearBajaEquipoDtoValido();

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> bajaEquipoBean.crearBajaEquipo(bajaEquipoDto, null));
        
        assertEquals("El email del usuario es obligatorio", exception.getMessage());
        verify(em, never()).persist(any());
        verify(em, never()).flush();
    }

    @Test
    void testCrearBajaEquipo_WithEmptyEmailUsuario() {
        BajaEquipoDto bajaEquipoDto = crearBajaEquipoDtoValido();

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> bajaEquipoBean.crearBajaEquipo(bajaEquipoDto, "   "));
        
        assertEquals("El email del usuario es obligatorio", exception.getMessage());
        verify(em, never()).persist(any());
        verify(em, never()).flush();
    }

    @Test
    void testCrearBajaEquipo_WithNullIdEquipo() {
        BajaEquipoDto bajaEquipoDto = crearBajaEquipoDtoValido();
        bajaEquipoDto.getIdEquipo().setId(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> bajaEquipoBean.crearBajaEquipo(bajaEquipoDto, "test@test.com"));
        
        assertEquals("El ID del equipo es obligatorio", exception.getMessage());
        verify(em, never()).persist(any());
        verify(em, never()).flush();
    }

    @Test
    void testCrearBajaEquipo_WithEquipoNotFound() throws ServiciosException {
        BajaEquipoDto bajaEquipoDto = crearBajaEquipoDtoValido();

        // Mock para verificar que no existe baja previa
        Query countQuery = mock(Query.class);
        when(em.createQuery("SELECT COUNT(b) FROM BajaEquipo b WHERE b.idEquipo.id = :idEquipo")).thenReturn(countQuery);
        when(countQuery.setParameter("idEquipo", 1L)).thenReturn(countQuery);
        when(countQuery.getSingleResult()).thenReturn(0L);

        // Mock para equipo no encontrado
        when(em.find(Equipo.class, 1L)).thenReturn(null);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> bajaEquipoBean.crearBajaEquipo(bajaEquipoDto, "test@test.com"));
        
        assertEquals("No se encontró el equipo con ID: 1", exception.getMessage());
        verify(em, never()).persist(any());
        verify(em, never()).flush();
    }

    @Test
    void testCrearBajaEquipo_WithUsuarioNotFound() throws ServiciosException {
        BajaEquipoDto bajaEquipoDto = crearBajaEquipoDtoValido();
        Equipo equipo = new Equipo();
        equipo.setId(1L);

        // Mock para verificar que no existe baja previa
        Query countQuery = mock(Query.class);
        when(em.createQuery("SELECT COUNT(b) FROM BajaEquipo b WHERE b.idEquipo.id = :idEquipo")).thenReturn(countQuery);
        when(countQuery.setParameter("idEquipo", 1L)).thenReturn(countQuery);
        when(countQuery.getSingleResult()).thenReturn(0L);

        // Mock para obtener equipo
        when(em.find(Equipo.class, 1L)).thenReturn(equipo);

        // Mock para usuario no encontrado - usar query separado
        Query userQuery = mock(Query.class);
        when(em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email")).thenReturn(userQuery);
        when(userQuery.setParameter("email", "test@test.com")).thenReturn(userQuery);
        when(userQuery.getSingleResult()).thenThrow(new NoResultException("Usuario no encontrado"));

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> bajaEquipoBean.crearBajaEquipo(bajaEquipoDto, "test@test.com"));
        
        assertEquals("No se encontró el usuario con email: test@test.com", exception.getMessage());
        verify(em, never()).persist(any());
        verify(em, never()).flush();
    }

    @Test
    void testCrearBajaEquipo_WithDuplicateEquipo() throws ServiciosException {
        BajaEquipoDto bajaEquipoDto = crearBajaEquipoDtoValido();

        // Mock para verificar que ya existe una baja
        Query countQuery = mock(Query.class);
        when(em.createQuery("SELECT COUNT(b) FROM BajaEquipo b WHERE b.idEquipo.id = :idEquipo")).thenReturn(countQuery);
        when(countQuery.setParameter("idEquipo", 1L)).thenReturn(countQuery);
        when(countQuery.getSingleResult()).thenReturn(1L);

        ServiciosException exception = assertThrows(ServiciosException.class, 
            () -> bajaEquipoBean.crearBajaEquipo(bajaEquipoDto, "test@test.com"));
        
        assertEquals("El equipo con ID 1 ya ha sido dado de baja", exception.getMessage());
        verify(em, never()).persist(any());
        verify(em, never()).flush();
    }

    @Test
    void testObtenerBajasEquipos() {
        BajaEquipo bajaEquipo = new BajaEquipo();
        bajaEquipo.setId(1L);
        bajaEquipo.setRazon("Test");
        bajaEquipo.setFecha(LocalDate.of(2025, 7, 12));
        bajaEquipo.setEstado("INACTIVO");
        
        BajaEquipoDto dto = new BajaEquipoDto();
        dto.setId(1L);
        dto.setRazon("Test");
        dto.setFecha(LocalDate.of(2025, 7, 12));
        dto.setEstado("INACTIVO");
        
        List<BajaEquipo> bajas = Collections.singletonList(bajaEquipo);
        List<BajaEquipoDto> dtos = Collections.singletonList(dto);

        @SuppressWarnings("unchecked")
        jakarta.persistence.TypedQuery<BajaEquipo> typedQuery = mock(jakarta.persistence.TypedQuery.class);
        when(em.createQuery("SELECT b FROM BajaEquipo b ORDER BY b.fecha DESC", BajaEquipo.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(bajas);
        when(bajaEquipoMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(dtos);

        List<BajaEquipoDto> result = bajaEquipoBean.obtenerBajasEquipos();

        assertEquals(dtos, result);
        verify(typedQuery).getResultList();
        verify(bajaEquipoMapper).toDto(anyList(), any(CycleAvoidingMappingContext.class));
    }

    @Test
    void testObtenerBajaEquipo() {
        Long id = 1L;
        BajaEquipo bajaEquipo = new BajaEquipo();
        bajaEquipo.setId(1L);
        bajaEquipo.setRazon("Test");
        bajaEquipo.setFecha(LocalDate.of(2025, 7, 12));
        bajaEquipo.setEstado("INACTIVO");
        
        BajaEquipoDto dto = new BajaEquipoDto();
        dto.setId(1L);
        dto.setRazon("Test");
        dto.setFecha(LocalDate.of(2025, 7, 12));
        dto.setEstado("INACTIVO");

        when(em.find(BajaEquipo.class, id)).thenReturn(bajaEquipo);
        when(bajaEquipoMapper.toDto(any(BajaEquipo.class), any(CycleAvoidingMappingContext.class))).thenReturn(dto);

        BajaEquipoDto result = bajaEquipoBean.obtenerBajaEquipo(id);

        assertEquals(dto, result);
        verify(em).find(BajaEquipo.class, id);
        verify(bajaEquipoMapper).toDto(any(BajaEquipo.class), any(CycleAvoidingMappingContext.class));
    }

    @Test
    void testObtenerBajaEquipo_WithNullId() {
        BajaEquipoDto result = bajaEquipoBean.obtenerBajaEquipo(null);
        assertNull(result);
    }

    @Test
    void testObtenerBajaEquipo_NotFound() {
        Long id = 999L;
        when(em.find(BajaEquipo.class, id)).thenReturn(null);

        BajaEquipoDto result = bajaEquipoBean.obtenerBajaEquipo(id);
        assertNull(result);
    }

    private BajaEquipoDto crearBajaEquipoDtoValido() {
        BajaEquipoDto dto = new BajaEquipoDto();
        dto.setRazon("Equipo obsoleto");
        dto.setFecha(LocalDate.now());
        dto.setEstado("INACTIVO");
        
        EquipoDto equipoDto = new EquipoDto();
        equipoDto.setId(1L);
        dto.setIdEquipo(equipoDto);
        
        return dto;
    }
}
