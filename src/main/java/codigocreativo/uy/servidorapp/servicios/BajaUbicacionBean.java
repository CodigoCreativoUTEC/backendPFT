package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaUbicacionDto;
import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.dtomappers.BajaUbicacionMapper;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtomappers.UbicacionMapper;
import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@TransactionManagement(TransactionManagementType.BEAN)
@Stateless
public class BajaUbicacionBean implements BajaUbicacionRemote {
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    private final BajaUbicacionMapper bajaUbicacionMapper;
    private final UbicacionMapper ubicacionMapper;

    public BajaUbicacionBean(BajaUbicacionMapper bajaUbicacionMapper, UbicacionMapper ubicacionMapper) {
        this.bajaUbicacionMapper = bajaUbicacionMapper;
        this.ubicacionMapper = ubicacionMapper;
    }

    @EJB
    UbicacionRemote ubicacionBean;

    @Transactional
    @Override
    public void crearBajaUbicacion(BajaUbicacionDto bajaUbicacion) throws ServiciosException {
        try {
            // Persistir la baja de ubicación
            em.persist(bajaUbicacionMapper.toEntity(bajaUbicacion, new CycleAvoidingMappingContext()));
            // Dar de baja lógica a la ubicación
        } catch (Exception e) {
            throw new ServiciosException(e.getMessage());
        }
    }

    @Override
    public void borrarUbicacion(Long id) throws ServiciosException {
        try {
            Ubicacion ubi = em.find(Ubicacion.class, id);
            em.remove(ubi);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException("No se pudo borrar la ubicacion");
        }
    }

    @Override
    public List<BajaUbicacionDto> listarBajaUbicaciones() throws ServiciosException {
        try {
            return bajaUbicacionMapper.toDto(em.createQuery("SELECT bajaUbicacion FROM BajaUbicacion bajaUbicacion", BajaUbicacion.class).getResultList(), new CycleAvoidingMappingContext());
        } catch (Exception e) {
            throw new ServiciosException("No se pudo listar las bajas de ubicaciones");
        }
    }

    @Override
    public void bajaLogicaUbicacion(UbicacionDto ub) throws ServiciosException {
        Ubicacion ubicacion = ubicacionMapper.toEntity(ub);
        try {
            ubicacion.setEstado(Estados.INACTIVO);
            em.merge(ubicacion);
            em.flush();
        } catch (Exception e) {
            throw new ServiciosException(e.getMessage());
        }
    }
}
