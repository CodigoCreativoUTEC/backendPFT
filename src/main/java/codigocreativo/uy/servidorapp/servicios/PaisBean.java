package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.PaisDto;
import codigocreativo.uy.servidorapp.DTOMappers.PaisMapper;
import codigocreativo.uy.servidorapp.entidades.Pais;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class PaisBean implements PaisRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    @Inject
    private PaisMapper paisMapper;

    @Override
    public void crearPais(PaisDto pais) {
        Pais paisEntity = paisMapper.toEntity(pais);
        em.persist(paisEntity);
        em.flush();
    }

    @Override
    public void modificarPais(PaisDto pais) {
        em.merge(paisMapper.toEntity(pais));
        em.flush();
    }

    @Override
    public List<PaisDto> obtenerpais() {
        List<Pais> paises = em.createQuery("SELECT p FROM Pais p", Pais.class).getResultList();
        return paisMapper.toDto(paises);
    }
}
