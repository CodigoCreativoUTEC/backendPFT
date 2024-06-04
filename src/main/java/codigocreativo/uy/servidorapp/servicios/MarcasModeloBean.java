package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.MarcasModeloDto;
import codigocreativo.uy.servidorapp.DTOMappers.MarcasModeloMapper;
import codigocreativo.uy.servidorapp.entidades.Equipo;
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

    @Inject
    private MarcasModeloMapper marcasModeloMapper;

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
}

