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
        String queryStr = "SELECT e FROM Equipo e WHERE 1=1";
        for (String key : filtros.keySet()) {
            queryStr += " AND e." + key + " = :" + key;
        }
        var query = em.createQuery(queryStr, Equipo.class);
        for (Map.Entry<String, String> entry : filtros.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
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