package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.InstitucionDto;
import codigocreativo.uy.servidorapp.dtomappers.InstitucionMapper;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class InstitucionBean implements InstitucionRemote{

    @PersistenceContext (unitName = "default")
    private final EntityManager em;
    private final InstitucionMapper institucionMapper;

    @Inject //Se inyecta el mapper
    public InstitucionBean(EntityManager em, InstitucionMapper institucionMapper) {
        this.em = em;
        this.institucionMapper = institucionMapper;
    }

    @Override
    public void agregar(InstitucionDto i) {
        Institucion institucionEntity = institucionMapper.toEntity(i);
        em.persist(institucionEntity);
        em.flush();
    }

    @Override
    public void eliminarInstitucion(InstitucionDto i) {
        Institucion institucionEntity = institucionMapper.toEntity(i);
        em.remove(institucionEntity);
        em.flush();

    }
    @Override
    public void modificar(InstitucionDto i) {
        Institucion institucionEntity = institucionMapper.toEntity(i);
        em.merge(institucionEntity);
        em.flush();

    }

    @Override
    public List obtenerUbicaciones() {
        return em.createQuery("SELECT Ubicacion FROM Institucion i, Institucion.class").getResultList();
    }

    @Override
    public List<InstitucionDto> obtenerInstituciones() {
        List<Institucion> instituciones = em.createQuery("SELECT i FROM Institucion i", Institucion.class).getResultList();
        return institucionMapper.toDto(instituciones);
    }

    @Override
    public InstitucionDto obtenerInstitucionPorNombre(String nombre) {
        Institucion institucion = em.createQuery("SELECT i FROM Institucion i WHERE i.nombre = :nombre", Institucion.class)
                .setParameter("nombre", nombre)
                .getSingleResult();
        return institucionMapper.toDto(institucion);
    }

    @Override
    public InstitucionDto obtenerInstitucionPorId(Long id) {
        Institucion institucion = em.createQuery("SELECT i FROM Institucion i WHERE i.id = :id", Institucion.class)
                .setParameter("id", id)
                .getSingleResult();
        return institucionMapper.toDto(institucion);
    }
}
