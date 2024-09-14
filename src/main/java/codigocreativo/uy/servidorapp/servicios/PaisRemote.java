package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.PaisDto;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface PaisRemote {
    public void crearPais(PaisDto pais);
    public void modificarPais(PaisDto pais);
    List<PaisDto> obtenerpais();
}
