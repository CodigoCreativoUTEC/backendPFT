package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.TiposEquipoDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TiposEquipoRemote {
    public void crearTiposEquipo(TiposEquipoDto tiposEquipo);
    public void modificarTiposEquipo(TiposEquipoDto tiposEquipo);
    public List<TiposEquipoDto> listarTiposEquipo();
}
