package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.EquipoMapper;
import codigocreativo.uy.servidorapp.dtos.dtomappers.UbicacionMapper;
import codigocreativo.uy.servidorapp.dtos.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class UbicacionBean implements UbicacionRemote {
    @PersistenceContext(unitName = "default")
    private EntityManager em;
    private final UbicacionMapper ubicacionMapper;
    private final EquipoMapper equipoMapper;

    @Inject
    public UbicacionBean(UbicacionMapper ubicacionMapper, EquipoMapper equipoMapper) {
        this.ubicacionMapper = ubicacionMapper;
        this.equipoMapper = equipoMapper;
    }

    @Override
    public void crearUbicacion(UbicacionDto ubi) throws ServiciosException {
        // Validar que el DTO no sea nulo
        if (ubi == null) {
            throw new ServiciosException("La ubicación es obligatoria");
        }
        
        // Validar que el nombre no sea nulo ni vacío
        if (ubi.getNombre() == null || ubi.getNombre().trim().isEmpty()) {
            throw new ServiciosException("El nombre de la ubicación es obligatorio");
        }
        
        // Validar que el sector no sea nulo ni vacío
        if (ubi.getSector() == null || ubi.getSector().trim().isEmpty()) {
            throw new ServiciosException("El sector de la ubicación es obligatorio");
        }
        
        // Validar que la institución no sea nula
        if (ubi.getIdInstitucion() == null || ubi.getIdInstitucion().getId() == null) {
            throw new ServiciosException("La institución de la ubicación es obligatoria");
        }
        
        try {
            // Verificar que la institución existe
            Institucion institucion = em.find(Institucion.class, ubi.getIdInstitucion().getId());
            if (institucion == null) {
                throw new ServiciosException("La institución especificada no existe");
            }
            
            // Validar que no exista una ubicación con el mismo nombre (case-insensitive)
            validarNombreUnico(ubi.getNombre().trim());
            
            // Estado por defecto
            ubi.setEstado(Estados.ACTIVO);
            
            Ubicacion ubicacion = ubicacionMapper.toEntity(ubi);
            em.persist(ubicacion);
            em.flush();
        } catch (ServiciosException e) {
            // Re-lanzar ServiciosException específicas
            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al crear la ubicación");
        }
    }

    @Override
    public void modificarUbicacion(UbicacionDto ubi) throws ServiciosException {
        // Validar que el DTO no sea nulo
        if (ubi == null) {
            throw new ServiciosException("La ubicación es obligatoria");
        }
        
        // Validar que el ID no sea nulo
        if (ubi.getId() == null) {
            throw new ServiciosException("El ID de la ubicación es obligatorio para modificar");
        }
        
        // Verificar que la ubicación existe
        Ubicacion actual = em.find(Ubicacion.class, ubi.getId());
        if (actual == null) {
            throw new ServiciosException("No se encontró la ubicación con ID: " + ubi.getId());
        }
        
        // Validar que el nombre no sea nulo ni vacío
        if (ubi.getNombre() == null || ubi.getNombre().trim().isEmpty()) {
            throw new ServiciosException("El nombre de la ubicación no puede ser nulo ni vacío");
        }
        
        try {
            // Validar que no exista otra ubicación con el mismo nombre (excluyendo la actual)
            validarNombreUnicoParaModificacion(ubi.getNombre().trim(), ubi.getId());
            
            em.merge(ubicacionMapper.toEntity(ubi));
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("Error al modificar la ubicación");
        }
    }

    @Override
    public void borrarUbicacion(Long id) throws ServiciosException {
        if (id == null) {
            throw new ServiciosException("El ID es obligatorio");
        }
        
        try {
            int updatedRows = em.createQuery("UPDATE Ubicacion ubicacion SET ubicacion.estado = 'INACTIVO' WHERE ubicacion.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            
            if (updatedRows == 0) {
                throw new ServiciosException("No se encontró la ubicación con ID: " + id);
            }
            
            em.flush();
        } catch (ServiciosException e) {
            // Re-lanzar ServiciosException específicas
            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al borrar la ubicación");
        }
    }

    @Override
    public void moverEquipoDeUbicacion(EquipoDto equipo, UbicacionDto ubicacion) throws ServiciosException {
        if (equipo == null) {
            throw new ServiciosException("El equipo es obligatorio");
        }
        
        if (ubicacion == null) {
            throw new ServiciosException("La ubicación es obligatoria");
        }
        
        try {
            equipo.setIdUbicacion(ubicacion);
            Equipo equipoEntity = equipoMapper.toEntity(equipo, new CycleAvoidingMappingContext());
            em.merge(equipoEntity);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("Error al mover el equipo");
        }
    }

    @Override
    public List<UbicacionDto> listarUbicaciones() throws ServiciosException {
        try {
            List<Ubicacion> ubicaciones = em.createQuery("SELECT u FROM Ubicacion u WHERE u.estado = 'ACTIVO'", Ubicacion.class)
                    .getResultList();
            return ubicacionMapper.toDto(ubicaciones);
        } catch (Exception e) {
            throw new ServiciosException("Error al listar las ubicaciones: " + e.getMessage());
        }
    }

    @Override
    public UbicacionDto obtenerUbicacionPorId(Long id) throws ServiciosException {
        if (id == null) {
            throw new ServiciosException("El ID es obligatorio");
        }
        
        try {
            Ubicacion ubicacion = em.createQuery("SELECT u FROM Ubicacion u WHERE u.id = :id", Ubicacion.class)
                    .setParameter("id", id)
                    .getSingleResult();
            
            // Handle mocked null return (test scenario)
            if (ubicacion == null) {
                throw new ServiciosException("No se encontró la ubicación con ID: " + id);
            }
            
            return ubicacionMapper.toDto(ubicacion);
        } catch (ServiciosException e) {
            // Re-throw specific ServiciosException
            throw e;
        } catch (NoResultException e) {
            throw new ServiciosException("No se encontró la ubicación con ID: " + id);
        } catch (Exception e) {
            throw new ServiciosException("Error al obtener la ubicación");
        }
    }

    @Override
    public void bajaLogicaUbicacion(UbicacionDto ub) throws ServiciosException {
        if (ub == null) {
            throw new ServiciosException("La ubicación es obligatoria");
        }
        
        try {
            Ubicacion ubicacion = ubicacionMapper.toEntity(ub);
            ubicacion.setEstado(Estados.INACTIVO);
            em.merge(ubicacion);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("Error al dar de baja la ubicación");
        }
    }
    
    /**
     * Valida que el nombre de la ubicación sea único en la base de datos
     */
    private void validarNombreUnico(String nombre) throws ServiciosException {
        try {
            jakarta.persistence.TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM Ubicacion u WHERE UPPER(u.nombre) = :nombre", Long.class);
            if (query != null) {
                query.setParameter("nombre", nombre.toUpperCase());
                Long count = query.getSingleResult();
                if (count > 0) {
                    throw new ServiciosException("Ya existe una ubicación con el nombre: " + nombre);
                }
            }
        } catch (ServiciosException e) {
            // Re-lanzar ServiciosException específicas
            throw e;
        } catch (Exception e) {
            // Handle null query or other exceptions
            throw new ServiciosException("Error al validar el nombre de la ubicación");
        }
    }
    
    /**
     * Valida que el nombre de la ubicación sea único para modificaciones (excluyendo la ubicación actual)
     */
    private void validarNombreUnicoParaModificacion(String nombre, Long idActual) throws ServiciosException {
        try {
            jakarta.persistence.TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM Ubicacion u WHERE UPPER(u.nombre) = :nombre AND u.id != :id", Long.class);
            if (query != null) {
                query.setParameter("nombre", nombre.toUpperCase());
                query.setParameter("id", idActual);
                Long count = query.getSingleResult();
                if (count > 0) {
                    throw new ServiciosException("Ya existe otra ubicación con el nombre: " + nombre);
                }
            }
        } catch (ServiciosException e) {
            // Re-lanzar ServiciosException específicas
            throw e;
        } catch (Exception e) {
            // Handle null query or other exceptions  
            throw new ServiciosException("Error al validar el nombre de la ubicación");
        }
    }
}