package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.entidades.ProveedoresEquipo;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ProveedoresEquipoRemote {

    public void CrearProveedoresEquipo(ProveedoresEquipoDto proveedoresEquipo);
    /*public void modificarProveedoresEquipo(ProveedoresEquipo proveedoresEquipo);
    public void obtenerProveedoresEquipo(Long id);*/
    public List<ProveedoresEquipoDto> obtenerProveedoresEquipo();
}
