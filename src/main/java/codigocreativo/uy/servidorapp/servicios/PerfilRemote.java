package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.PerfilDto;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Permiso;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Remote;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;

@Remote
public interface PerfilRemote {
    public void crearPerfil(PerfilDto p);
    public void modificarPerfil(PerfilDto p);
    public void eliminarPerfil(PerfilDto p);
    public PerfilDto obtenerPerfil(Long id);
    public List<PerfilDto> obtenerPerfiles();
    public List<PerfilDto> listarPerfilesPorNombre(String nombre);
    public List<PerfilDto> listarPerfilesPorEstado(Estados estado);

}
