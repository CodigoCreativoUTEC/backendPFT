package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TipoIntervencioneRemote {
    public List<TiposIntervencioneDto> obtenerTiposIntervenciones();
    public TiposIntervencioneDto obtenerTipoIntervencion(Long id);
    public void crearTipoIntervencion(TiposIntervencioneDto tipoIntervencion);
    public void modificarTipoIntervencion(TiposIntervencioneDto tipoIntervencion);
    public void eliminarTipoIntervencion(Long id);
}
