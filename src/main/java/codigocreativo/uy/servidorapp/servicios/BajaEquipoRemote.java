package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface BajaEquipoRemote {
    public void crearBajaEquipo(BajaEquipoDto bajaEquipoequipo);
    public List<BajaEquipoDto> obtenerBajasEquipos();
    public BajaEquipoDto obtenerBajaEquipo(Long id);


}
