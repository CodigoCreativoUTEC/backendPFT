package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.dtomappers.ProveedoresEquipoMapper;
import codigocreativo.uy.servidorapp.entidades.ProveedoresEquipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ProveedoresEquipoBean implements ProveedoresEquipoRemote{
    @PersistenceContext (unitName = "default")
    private EntityManager em;
    private final ProveedoresEquipoMapper proveedoresEquipoMapper;

    @Inject
    public ProveedoresEquipoBean(ProveedoresEquipoMapper proveedoresEquipoMapper) {
        this.proveedoresEquipoMapper = proveedoresEquipoMapper;
    }

    @Override
    public void crearProveedor(ProveedoresEquipoDto proveedoresEquipo) {
        proveedoresEquipo.setEstado(Estados.ACTIVO);
        ProveedoresEquipo proveedoresEquipoEntity = proveedoresEquipoMapper.toEntity(proveedoresEquipo);
        em.persist(proveedoresEquipoEntity);
        em.flush();
    }

    @Override
    public void modificarProveedor(ProveedoresEquipoDto proveedoresEquipo) {
        ProveedoresEquipo proveedoresEquipoEntity = proveedoresEquipoMapper.toEntity(proveedoresEquipo);
        em.merge(proveedoresEquipoEntity);
        em.flush();
    }

    @Override
    public void obtenerProveedor(Long id) {
        em.find(ProveedoresEquipo.class, id);
    }


    @Override
    public List<ProveedoresEquipoDto> obtenerProveedores() {
        List<ProveedoresEquipo> proveedoresEquipo = em.createQuery("SELECT p FROM ProveedoresEquipo p", ProveedoresEquipo.class).getResultList();
        return proveedoresEquipoMapper.toDto(proveedoresEquipo);// ("UPDATE Equipo equipo SET equipo.estado = 'INACTIVO' WHERE equipo.id = :id")
    }

    @Override
    public void eliminarProveedor(Long id) {
        em.createQuery("UPDATE ProveedoresEquipo p SET p.estado = 'INACTIVO' WHERE p.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        em.flush();
    }
}

