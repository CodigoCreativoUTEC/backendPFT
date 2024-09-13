package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.dtomappers.MarcasModeloMapper;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class MarcasModeloBean implements MarcasModeloRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    private final MarcasModeloMapper marcasModeloMapper;

    @Inject
    public MarcasModeloBean(MarcasModeloMapper marcasModeloMapper) {
        this.marcasModeloMapper = marcasModeloMapper;
    }

    @Override
    public void crearMarcasModelo(MarcasModeloDto marcasModelo) {
        em.persist(marcasModeloMapper.toEntity(marcasModelo));
        em.flush();
    }

    @Override
    public void modificarMarcasModelo(MarcasModeloDto marcasModelo) {
        em.merge(marcasModeloMapper.toEntity(marcasModelo));
        em.flush();
    }

    @Override
    public MarcasModeloDto obtenerMarca(Long id) {
        return marcasModeloMapper.toDto(em.find(MarcasModelo.class, id));
    }


    @Override
    public List<MarcasModeloDto> obtenerMarcasLista() {
        return marcasModeloMapper.toDto(em.createQuery("SELECT marcasModelo FROM MarcasModelo marcasModelo", MarcasModelo.class).getResultList());
    }

    @Override
    public void eliminarMarca(Long id) {
        em.createQuery("UPDATE MarcasModelo marcasModelo SET marcasModelo.estado = 'INACTIVO' WHERE marcasModelo.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        em.flush();
    }
}

