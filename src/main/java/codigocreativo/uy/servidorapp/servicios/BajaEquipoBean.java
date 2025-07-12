package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.BajaEquipoMapper;
import codigocreativo.uy.servidorapp.dtos.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.NoResultException;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class BajaEquipoBean implements BajaEquipoRemote {
    @PersistenceContext(unitName = "default")
    private EntityManager em;
    private final BajaEquipoMapper bajaEquipoMapper;

    @Inject
    public BajaEquipoBean(BajaEquipoMapper bajaEquipoMapper) {
        this.bajaEquipoMapper = bajaEquipoMapper;
    }

    @Override
    public void crearBajaEquipo(BajaEquipoDto bajaEquipoDto, String emailUsuario) throws ServiciosException {
        if (bajaEquipoDto == null) {
            throw new ServiciosException("Los datos de baja son obligatorios");
        }

        if (emailUsuario == null || emailUsuario.trim().isEmpty()) {
            throw new ServiciosException("El email del usuario es obligatorio");
        }

        Long idEquipo = bajaEquipoDto.getIdEquipo().getId();
        if (idEquipo == null) {
            throw new ServiciosException("El ID del equipo es obligatorio");
        }

        // Verificar si ya existe una baja para este equipo
        verificarBajaExistente(idEquipo);

        try {
            // Obtener el equipo
            Equipo equipo = em.find(Equipo.class, idEquipo);
            if (equipo == null) {
                throw new ServiciosException("No se encontró el equipo con ID: " + idEquipo);
            }

            // Obtener el usuario
            Usuario usuario = obtenerUsuarioPorEmail(emailUsuario);
            if (usuario == null) {
                throw new ServiciosException("No se encontró el usuario con email: " + emailUsuario);
            }

            // Crear la entidad de baja
            BajaEquipo bajaEquipo = bajaEquipoMapper.toEntity(bajaEquipoDto, new CycleAvoidingMappingContext());
            bajaEquipo.setIdEquipo(equipo);
            bajaEquipo.setIdUsuario(usuario);
            bajaEquipo.setFecha(LocalDate.now());

            // Persistir la baja
            em.persist(bajaEquipo);
            em.flush();

            // Cambiar el estado del equipo a INACTIVO
            equipo.setEstado(Estados.INACTIVO);
            em.merge(equipo);
            em.flush();

        } catch (ServiciosException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al crear la baja del equipo: " + e.getMessage());
        }
    }

    private void verificarBajaExistente(Long idEquipo) throws ServiciosException {
        try {
            Query query = em.createQuery("SELECT COUNT(b) FROM BajaEquipo b WHERE b.idEquipo.id = :idEquipo");
            query.setParameter("idEquipo", idEquipo);
            Object result = query.getSingleResult();
            
            // Handle different return types from COUNT query
            Long count;
            if (result instanceof Number) {
                count = ((Number) result).longValue();
            } else {
                count = Long.valueOf(result.toString());
            }

            if (count > 0) {
                throw new ServiciosException("El equipo con ID " + idEquipo + " ya ha sido dado de baja");
            }
        } catch (ServiciosException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiciosException("Error al verificar si existe baja para el equipo: " + e.getMessage());
        }
    }

    private Usuario obtenerUsuarioPorEmail(String email) throws ServiciosException {
        try {
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
            query.setParameter("email", email);
            return (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            throw new ServiciosException("No se encontró el usuario con email: " + email);
        } catch (Exception e) {
            throw new ServiciosException("Error al buscar el usuario: " + e.getMessage());
        }
    }

    @Override
    public List<BajaEquipoDto> obtenerBajasEquipos() {
        List<BajaEquipo> bajas = em.createQuery("SELECT b FROM BajaEquipo b ORDER BY b.fecha DESC", BajaEquipo.class).getResultList();
        return bajaEquipoMapper.toDto(bajas, new CycleAvoidingMappingContext());
    }

    @Override
    public BajaEquipoDto obtenerBajaEquipo(Long id) {
        if (id == null) {
            return null;
        }

        BajaEquipo bajaEquipo = em.find(BajaEquipo.class, id);
        if (bajaEquipo == null) {
            return null;
        }

        return bajaEquipoMapper.toDto(bajaEquipo, new CycleAvoidingMappingContext());
    }
}