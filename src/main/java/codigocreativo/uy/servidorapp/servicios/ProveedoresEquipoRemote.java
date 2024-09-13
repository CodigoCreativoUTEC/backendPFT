package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ProveedoresEquipoRemote {

    public void crearProveedor(ProveedoresEquipoDto proveedoresEquipo);
    public void modificarProveedor(ProveedoresEquipoDto proveedoresEquipo);
    public void obtenerProveedor(Long id);
    public List<ProveedoresEquipoDto> obtenerProveedores();
    public void eliminarProveedor(Long id);
}
