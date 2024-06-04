package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.PermisoDto;
import codigocreativo.uy.servidorapp.entidades.Permiso;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface PermisoRemote {
    public void crearPermiso(PermisoDto p);
    public void modificarPermiso(PermisoDto p);
    public void eliminarPermiso(PermisoDto p);
    public PermisoDto obtenerPermiso(Long id);
    public List<PermisoDto> obtenerPermisos();

}
