package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.ModelosEquipoDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ModelosEquipoRemote {
    public void crearModelos(ModelosEquipoDto modelosEquipo);
    public void modificarModelos(ModelosEquipoDto modelosEquipo);
    public ModelosEquipoDto obtenerModelos(Long id);
    public List<ModelosEquipoDto> listarModelos();
    public void eliminarModelos(Long id);
}