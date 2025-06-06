package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.entidades.Funcionalidad;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.UsuarioNoEncontradoException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PermissionService {
    
    @PersistenceContext
    private EntityManager em;
    
    private final UsuarioBean usuarioBean;
    
    public PermissionService(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }
    
    public boolean hasPermission(Long userId, String ruta) {
        UsuarioDto usuarioDto = usuarioBean.obtenerUsuario(userId);
        if (usuarioDto == null) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + userId);
        }
        
        Perfil perfil = em.find(Perfil.class, usuarioDto.getIdPerfil().getId());
        return perfil.getFuncionalidadesPerfiles().stream()
            .anyMatch(fp -> fp.getFuncionalidad().getRuta().equals(ruta) 
                && fp.getFuncionalidad().getEstado().equals(Estados.ACTIVO));
    }
    
    public List<Funcionalidad> getUserMenu(Long userId) {
        UsuarioDto usuarioDto = usuarioBean.obtenerUsuario(userId);
        if (usuarioDto == null) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + userId);
        }
        
        Perfil perfil = em.find(Perfil.class, usuarioDto.getIdPerfil().getId());
        return perfil.getFuncionalidadesPerfiles().stream()
            .map(fp -> fp.getFuncionalidad())
            .filter(f -> f.getEstado().equals(Estados.ACTIVO))
            .toList();
    }
} 