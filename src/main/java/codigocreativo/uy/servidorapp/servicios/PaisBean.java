package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.PaisDto;
import codigocreativo.uy.servidorapp.dtomappers.PaisMapper;
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
    private final PaisMapper paisMapper;

    @Inject //Se inyecta el mapper
    public PaisBean(PaisMapper paisMapper) {
        this.paisMapper = paisMapper;
    }

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
