package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.OperacionDto;
import codigocreativo.uy.servidorapp.entidades.Operacion;
import jakarta.ejb.Remote;

@Remote
public interface OperacionRemote {
    public void crearOperacion(OperacionDto o);
    public void modificarOperacion (OperacionDto o);
    
    public void eliminarOperacion (OperacionDto o);
    
}
