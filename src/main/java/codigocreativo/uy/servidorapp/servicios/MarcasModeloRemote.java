package codigocreativo.uy.servidorapp.servicios;
import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface MarcasModeloRemote {
    public void crearMarcasModelo(MarcasModeloDto marcasModelo);
    public void modificarMarcasModelo(MarcasModeloDto marcasModelo);
    public MarcasModeloDto obtenerMarca(Long id);
    public List<MarcasModeloDto> obtenerMarcasLista();
    public void eliminarMarca(Long id);
}
