package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.PaisDto;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import codigocreativo.uy.servidorapp.entidades.Pais;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface PaisRemote {
    public void crearPais(PaisDto pais);
    public void modificarPais(PaisDto pais);
    List<PaisDto> obtenerpais();
}
