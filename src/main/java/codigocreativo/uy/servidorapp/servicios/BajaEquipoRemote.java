package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.BajaEquipoDto;
import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface BajaEquipoRemote {
    public void crearBajaEquipo(BajaEquipoDto bajaEquipoequipo);
    public List<BajaEquipoDto> obtenerBajasEquipos();
    public BajaEquipoDto obtenerBajaEquipo(Long id);


}
