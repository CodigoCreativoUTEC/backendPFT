package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface EquipoRemote {
    public void crearEquipo(EquipoDto equipo);
    public void modificarEquipo(EquipoDto equipo);
    public void eliminarEquipo(BajaEquipoDto bajaEquipo);

    List<EquipoDto> obtenerEquiposFiltrado(String filtro, String valor);

    public EquipoDto obtenerEquipo(Long id);
    public List<EquipoDto> listarEquipos();

}