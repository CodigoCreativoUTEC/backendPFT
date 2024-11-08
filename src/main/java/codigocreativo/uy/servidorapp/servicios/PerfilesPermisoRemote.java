package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.PerfilesPermisoDto;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface PerfilesPermisoRemote {
    public void crearPerfilesPermiso(PerfilesPermisoDto perfilesPermiso);
    public void modificarPerfilesPermiso(PerfilesPermisoDto perfilesPermiso);
    public List<PerfilesPermisoDto> obtenerPerfilesPermiso();
}
