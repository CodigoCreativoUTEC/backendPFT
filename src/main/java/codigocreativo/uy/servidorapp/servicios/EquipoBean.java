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
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
        em.persist(equipoMapper.toEntity(equipo, new CycleAvoidingMappingContext()));
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
        agregarCondicionesDeFiltrado(queryStr, filtros);

        var query = em.createQuery(queryStr.toString(), Equipo.class);

        // Establecer parámetros de la consulta
        establecerParametrosDeConsulta(query, filtros);

        return equipoMapper.toDto(query.getResultList(), new CycleAvoidingMappingContext());
    }

    // Método auxiliar para agregar condiciones de filtrado
    private void agregarCondicionesDeFiltrado(StringBuilder queryStr, Map<String, String> filtros) {
        agregarCondicion(queryStr, filtros, "nombre", "LOWER(e.nombre) LIKE LOWER(:nombre)");
        agregarCondicion(queryStr, filtros, "tipo", "LOWER(e.idTipo.nombreTipo) LIKE LOWER(:tipo)");
        agregarCondicion(queryStr, filtros, "marca", "LOWER(e.idModelo.marca.nombre) LIKE LOWER(:marca)");
        agregarCondicion(queryStr, filtros, "modelo", "LOWER(e.idModelo.nombre) LIKE LOWER(:modelo)");
        agregarCondicion(queryStr, filtros, "numeroSerie", "LOWER(e.nroSerie) LIKE LOWER(:numeroSerie)");
        agregarCondicion(queryStr, filtros, "paisOrigen", "LOWER(e.idPais.nombre) LIKE LOWER(:paisOrigen)");
        agregarCondicion(queryStr, filtros, "proveedor", "LOWER(e.idProveedor.nombre) LIKE LOWER(:proveedor)");
        agregarCondicion(queryStr, filtros, "fechaAdquisicion", "e.fechaAdquisicion = :fechaAdquisicion");
        agregarCondicion(queryStr, filtros, "identificacionInterna", "LOWER(e.idInterno) LIKE LOWER(:identificacionInterna)");
        agregarCondicion(queryStr, filtros, "ubicacion", "LOWER(e.idUbicacion.nombre) LIKE LOWER(:ubicacion)");
    }

    // Método auxiliar para agregar una condición individualmente
    private void agregarCondicion(StringBuilder queryStr, Map<String, String> filtros, String filtroClave, String condicion) {
        if (filtros.containsKey(filtroClave)) {
            queryStr.append(" AND ").append(condicion);
        }
    }

    // Método auxiliar para establecer los parámetros de consulta
    private void establecerParametrosDeConsulta(Query query, Map<String, String> filtros) {
        filtros.forEach((key, value) -> {
            if (!value.isEmpty()) {
                if (key.equals("fechaAdquisicion")) {
                    query.setParameter(key, LocalDate.parse(value));  // Si es fecha, se parsea
                } else {
                    query.setParameter(key, "%" + value + "%");
                }
            }
        });
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