package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.dtomappers.BajaEquipoMapper;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtomappers.EquipoMapper;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class EquipoBean implements EquipoRemote {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Inject
    EquipoMapper equipoMapper;

    @Inject
    BajaEquipoMapper bajaEquipoMapper;

    @Override
    public void crearEquipo(EquipoDto equipo) {
        em.merge(equipoMapper.toEntity(equipo, new CycleAvoidingMappingContext()));
        em.flush();
    }

    @Override
    public void modificarEquipo(EquipoDto equipo) {
        em.merge(equipoMapper.toEntity(equipo, new CycleAvoidingMappingContext()));
        em.flush();
    }

    @Override
    public void eliminarEquipo(BajaEquipoDto bajaEquipo) {
        em.merge(bajaEquipoMapper.toEntity(bajaEquipo, new CycleAvoidingMappingContext()));
        em.createQuery("UPDATE Equipo equipo SET equipo.estado = 'INACTIVO' WHERE equipo.id = :id")
                .setParameter("id", bajaEquipo.getIdEquipo().getId())
                .executeUpdate();
        em.flush();
    }

    @Override
    public List<EquipoDto> obtenerEquiposFiltrado(Map<String, String> filtros) {
        StringBuilder queryStr = new StringBuilder("SELECT e FROM Equipo e WHERE 1=1");

        // Añadir condiciones de filtrado
        if (filtros.containsKey("nombre")) {
            queryStr.append(" AND LOWER(e.nombre) LIKE LOWER(:nombre)");
        }
        if (filtros.containsKey("tipo")) {
            queryStr.append(" AND LOWER(e.idTipo.nombreTipo) LIKE LOWER(:tipo)");
        }
        if (filtros.containsKey("marca")) {
            queryStr.append(" AND LOWER(e.idModelo.marca.nombre) LIKE LOWER(:marca)");
        }
        if (filtros.containsKey("modelo")) {
            queryStr.append(" AND LOWER(e.idModelo.nombre) LIKE LOWER(:modelo)");
        }
        if (filtros.containsKey("numeroSerie")) {
            queryStr.append(" AND LOWER(e.nroSerie) LIKE LOWER(:numeroSerie)");
        }
        if (filtros.containsKey("paisOrigen")) {
            queryStr.append(" AND LOWER(e.idPais.nombre) LIKE LOWER(:paisOrigen)");
        }
        if (filtros.containsKey("proveedor")) {
            queryStr.append(" AND LOWER(e.idProveedor.nombre) LIKE LOWER(:proveedor)");
        }
        if (filtros.containsKey("fechaAdquisicion")) {
            queryStr.append(" AND e.fechaAdquisicion = :fechaAdquisicion");
        }
        if (filtros.containsKey("identificacionInterna")) {
            queryStr.append(" AND LOWER(e.idInterno) LIKE LOWER(:identificacionInterna)");
        }
        if (filtros.containsKey("ubicacion")) {
            queryStr.append(" AND LOWER(e.idUbicacion.nombre) LIKE LOWER(:ubicacion)");
        }

        var query = em.createQuery(queryStr.toString(), Equipo.class);

        // Establecer parámetros de la consulta
        filtros.forEach((key, value) -> {
            if (!value.isEmpty()) {
                if (key.equals("fechaAdquisicion")) {
                    query.setParameter(key, LocalDate.parse(value));  // Si es fecha, se parsea
                } else {
                    query.setParameter(key, "%" + value + "%");
                }
            }
        });

        System.out.println("Consulta generada: " + queryStr.toString()); // Depuración
        return equipoMapper.toDto(query.getResultList(), new CycleAvoidingMappingContext());
    }




    @Override
    public EquipoDto obtenerEquipo(Long id) {
        return equipoMapper.toDto(em.find(Equipo.class, id), new CycleAvoidingMappingContext());
    }

    @Override
    public List<EquipoDto> listarEquipos() {
        return equipoMapper.toDto(em.createQuery("SELECT equipo FROM Equipo equipo", Equipo.class).getResultList(), new CycleAvoidingMappingContext());
    }
}