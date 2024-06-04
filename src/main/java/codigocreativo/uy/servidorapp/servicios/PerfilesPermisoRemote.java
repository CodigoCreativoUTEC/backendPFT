package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.PerfilesPermisoDto;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.PerfilesPermiso;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface PerfilesPermisoRemote {
    public void CrearPerfilesPermiso(PerfilesPermisoDto perfilesPermiso);
    public void modificarPerfilesPermiso(PerfilesPermisoDto perfilesPermiso);
    public List<PerfilesPermisoDto> obtenerPerfilesPermiso();
}
