package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtomappers.FuncionalidadMapper;
import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.entidades.Funcionalidad;
import codigocreativo.uy.servidorapp.entidades.FuncionalidadesPerfiles;
import codigocreativo.uy.servidorapp.entidades.FuncionalidadesPerfilesId;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class FuncionalidadBean implements FuncionalidadRemote {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Inject
    private FuncionalidadMapper funcionalidadMapper;

    @Override
    public List<FuncionalidadDto> obtenerTodas() {
        List<Funcionalidad> funcionalidades = em.createQuery("SELECT f FROM Funcionalidad f", Funcionalidad.class).getResultList();
        return funcionalidadMapper.toDto(funcionalidades, new CycleAvoidingMappingContext());
    }

    @Override
    public FuncionalidadDto crear(FuncionalidadDto funcionalidadDto) {
        Funcionalidad funcionalidad = funcionalidadMapper.toEntity(funcionalidadDto, new CycleAvoidingMappingContext());
        em.persist(funcionalidad);
        em.flush();
        return funcionalidadMapper.toDto(funcionalidad, new CycleAvoidingMappingContext());
    }

    @Override
    public FuncionalidadDto actualizar(FuncionalidadDto funcionalidadDto) {
        Funcionalidad funcionalidad = funcionalidadMapper.toEntity(funcionalidadDto, new CycleAvoidingMappingContext());

        // Guardar los cambios
        funcionalidad = em.merge(funcionalidad);
        em.flush();

        return funcionalidadMapper.toDto(funcionalidad, new CycleAvoidingMappingContext());
    }


    @Override
    public void eliminar(Long id) {
        Funcionalidad funcionalidad = em.find(Funcionalidad.class, id);
        if (funcionalidad != null) {
            em.remove(funcionalidad);
            em.flush();
        }
    }

    @Override
    public FuncionalidadDto buscarPorId(Long id) {
        Funcionalidad funcionalidad = em.find(Funcionalidad.class, id);
        return funcionalidadMapper.toDto(funcionalidad, new CycleAvoidingMappingContext());
    }
}
