package codigocreativo.uy.servidorapp.servicios;
import codigocreativo.uy.servidorapp.dtos.ModelosEquipoDto;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface ModelosEquipoRemote {
    public void crearModelosEquipo(ModelosEquipoDto modelosEquipo);
    public void modificarModelosEquipo(ModelosEquipoDto modelosEquipo);
    public ModelosEquipoDto obtenerModelosEquipo(Long id);
    public List<ModelosEquipoDto> listarModelosEquipo();
}
