package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.TiposEquipoDto;
import codigocreativo.uy.servidorapp.entidades.TiposEquipo;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TiposEquipoRemote {
    public void crearTiposEquipo(TiposEquipoDto tiposEquipo);
    public void modificarTiposEquipo(TiposEquipoDto tiposEquipo);
    public List<TiposEquipoDto> listarTiposEquipo();
}
