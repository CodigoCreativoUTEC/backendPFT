package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Remote;

import java.util.List;

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
