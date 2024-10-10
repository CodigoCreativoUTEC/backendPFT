package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.dtomappers.PerfilMapper;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class PerfilBean implements PerfilRemote {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Inject
    private PerfilMapper perfilMapper;



    @Override
    public void crearPerfil(PerfilDto p) {
        Perfil entity = perfilMapper.toEntity(p, new CycleAvoidingMappingContext());
        entity.setEstado(Estados.ACTIVO);
        em.persist(entity);
        em.flush();
    }

    @Override
    public void modificarPerfil(PerfilDto p) {
        em.merge(perfilMapper.toEntity(p, new CycleAvoidingMappingContext()));
        em.flush();
    }

    @Override
    public void eliminarPerfil(PerfilDto p) {
        Perfil entity = perfilMapper.toEntity(p, new CycleAvoidingMappingContext());
        entity.setEstado(Estados.INACTIVO);
        em.merge(entity);
    }

    @Override
    public PerfilDto obtenerPerfil(Long id) {
        return perfilMapper.toDto(em.find(Perfil.class, id), new CycleAvoidingMappingContext());
    }

    @Override
    public List<PerfilDto> obtenerPerfiles() {
        return perfilMapper.toDto(em.createQuery("SELECT p FROM Perfil p", Perfil.class).getResultList(), new CycleAvoidingMappingContext());
    }

    @Override
    public List<PerfilDto> listarPerfilesPorNombre(String nombre) {
        return perfilMapper.toDto(em.createQuery("SELECT p FROM Perfil p WHERE p.nombrePerfil = :nombre", Perfil.class)
                .setParameter("nombre", nombre)
                .getResultList(), new CycleAvoidingMappingContext());
    }

    @Override
    public List<PerfilDto> listarPerfilesPorEstado(Estados estado) {
        return perfilMapper.toDto(em.createQuery("SELECT p FROM Perfil p WHERE p.estado = :estado", Perfil.class)
                .setParameter("estado", estado)
                .getResultList(), new CycleAvoidingMappingContext());
    }


}
