package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.EquiposUbicacioneDto;
import codigocreativo.uy.servidorapp.dtomappers.EquiposUbicacioneMapper;
import codigocreativo.uy.servidorapp.entidades.EquiposUbicacione;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@Stateless
public class EquiposUbicacioneBean implements EquiposUbicacioneRemote {

    private final EntityManager em;
    private final EquiposUbicacioneMapper equiposUbicacioneMapper;

    @Inject
    public EquiposUbicacioneBean(EntityManager em, EquiposUbicacioneMapper equiposUbicacioneMapper) {
        this.em = em;
        this.equiposUbicacioneMapper = equiposUbicacioneMapper;
    }

    @Override
    public void crearEquiposUbicacione(EquiposUbicacioneDto equiposUbicacione) {
        em.persist(equiposUbicacioneMapper.toEntity(equiposUbicacione, new codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext()));
        em.flush();
    }

    @Override
    public List<EquiposUbicacioneDto> obtenerEquiposUbicacione() {
        return equiposUbicacioneMapper.toDto(em.createQuery("SELECT equiposUbicacione FROM EquiposUbicacione equiposUbicacione", EquiposUbicacione.class).getResultList(), new codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext());
    }

    @Override
    public List<EquiposUbicacioneDto> obtenerEquiposUbicacionePorEquipo(Long id) {
        return equiposUbicacioneMapper.toDto(em.createQuery("SELECT equiposUbicacione FROM EquiposUbicacione equiposUbicacione WHERE equiposUbicacione.idEquipo.id = :id", EquiposUbicacione.class)
                .setParameter("id", id)
                .getResultList(), new codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext());
    }
}