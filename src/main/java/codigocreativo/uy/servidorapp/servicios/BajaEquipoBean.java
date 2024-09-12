package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtomappers.BajaEquipoMapper;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class BajaEquipoBean implements BajaEquipoRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;

    private final BajaEquipoMapper bajaEquipoMapper;

    @Inject
    public BajaEquipoBean(BajaEquipoMapper bajaEquipoMapper) {
        this.bajaEquipoMapper = bajaEquipoMapper;
    }

    @Override
    public void crearBajaEquipo(BajaEquipoDto bajaEquipo) {
        BajaEquipo entity = bajaEquipoMapper.toEntity(bajaEquipo, new CycleAvoidingMappingContext());
        if (entity.getId() != null) {
            entity = em.merge(entity);
        }
        em.persist(entity);
    }


    @Override
    public List<BajaEquipoDto> obtenerBajasEquipos() {
        return bajaEquipoMapper.toDto(em.createQuery("SELECT bajaEquipo FROM BajaEquipo bajaEquipo", BajaEquipo.class).getResultList(), new CycleAvoidingMappingContext());
    }

    @Override
    public BajaEquipoDto obtenerBajaEquipo(Long id) {
        return bajaEquipoMapper.toDto(em.find(BajaEquipo.class, id), new CycleAvoidingMappingContext());
    }


}