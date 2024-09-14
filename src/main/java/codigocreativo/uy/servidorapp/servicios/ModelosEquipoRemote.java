package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.ModelosEquipoDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface ModelosEquipoRemote {
    void crearModelos(ModelosEquipoDto modelosEquipo);
    void modificarModelos(ModelosEquipoDto modelosEquipo);
    ModelosEquipoDto obtenerModelos(Long id);
    List<ModelosEquipoDto> listarModelos();
    void eliminarModelos(Long id);
}