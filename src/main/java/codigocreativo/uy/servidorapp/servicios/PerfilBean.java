package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.PerfilMapper;
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
    private final PerfilMapper perfilMapper;

    @Inject
    public PerfilBean(PerfilMapper perfilMapper) {
        this.perfilMapper = perfilMapper;
    }

    @Override
    public void crearPerfil(PerfilDto p) {
        p.setEstado(Estados.ACTIVO);
        em.persist(perfilMapper.toEntity(p));
        em.flush();
    }

    @Override
    public void modificarPerfil(PerfilDto p) {
        em.merge(perfilMapper.toEntity(p));
        em.flush();
    }

    @Override
    public void eliminarPerfil(PerfilDto p) {
        Perfil entity = perfilMapper.toEntity(p);
        entity.setEstado(Estados.INACTIVO);
        em.merge(entity);
    }

    @Override
    public PerfilDto obtenerPerfil(Long id) {
        return perfilMapper.toDto(em.find(Perfil.class, id));
    }

    @Override
    public List<PerfilDto> obtenerPerfiles() {
        // Asegúrate de usar el método correcto para mapear una lista
        return perfilMapper.toDtoList(em.createQuery("SELECT p FROM Perfil p", Perfil.class).getResultList());
    }

    @Override
    public List<PerfilDto> listarPerfilesPorNombre(String nombre) {
        // Asegúrate de mapear correctamente una lista de resultados
        return perfilMapper.toDtoList(em.createQuery("SELECT p FROM Perfil p WHERE p.nombrePerfil LIKE :nombre", Perfil.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList());
    }

    @Override
    public List<PerfilDto> listarPerfilesPorEstado(Estados estado) {
        // Asegúrate de mapear correctamente una lista de resultados
        return perfilMapper.toDtoList(em.createQuery("SELECT p FROM Perfil p WHERE p.estado = :estado", Perfil.class)
                .setParameter("estado", estado)
                .getResultList());
    }


}
