package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.MarcasModeloMapper;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
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
        marcasModelo.setEstado(Estados.ACTIVO);
        em.persist(marcasModeloMapper.toEntity(marcasModelo));
        em.flush();
    }

    @Override
    public void modificarMarcasModelo(MarcasModeloDto marcasModelo) {
        MarcasModelo actual = em.find(MarcasModelo.class, marcasModelo.getId());
        if (actual == null) {
            throw new IllegalArgumentException("Marca no encontrada");
        }
        if (!actual.getNombre().equals(marcasModelo.getNombre())) {
            throw new IllegalArgumentException("No se permite modificar el nombre de la marca.");
        }
        // Solo se permite modificar el estado (u otros campos permitidos)
        actual.setEstado(marcasModelo.getEstado().name());
        em.merge(actual);
        em.flush();
    }

    @Override
    public MarcasModeloDto obtenerMarca(Long id) {
        return marcasModeloMapper.toDto(em.find(MarcasModelo.class, id));
    }


    @Override
    public List<MarcasModeloDto> obtenerMarcasLista() {
        return marcasModeloMapper.toDto(
            em.createQuery("SELECT marcasModelo FROM MarcasModelo marcasModelo ORDER BY marcasModelo.id DESC", MarcasModelo.class)
                .getResultList()
        );
    }

    public List<MarcasModeloDto> obtenerMarcasPorEstadoLista(Estados estado) {
        return marcasModeloMapper.toDto(
            em.createQuery("SELECT marcasModelo FROM MarcasModelo marcasModelo WHERE marcasModelo.estado = :estado ORDER BY marcasModelo.id DESC", MarcasModelo.class)
                .setParameter("estado", estado.name())
                .getResultList()
        );
    }

    @Override
    public void eliminarMarca(Long id) {
        em.createQuery("UPDATE MarcasModelo marcasModelo SET marcasModelo.estado = 'INACTIVO' WHERE marcasModelo.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        em.flush();
    }
}

