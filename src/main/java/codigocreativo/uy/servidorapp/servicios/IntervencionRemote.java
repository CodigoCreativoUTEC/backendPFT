package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.IntervencionDto;
import codigocreativo.uy.servidorapp.entidades.Intervencion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.ejb.Remote;

import java.util.List;
@Remote
public interface IntervencionRemote {
    // CRUD para intervenciones
    public void crear(IntervencionDto intervencion) throws ServiciosException;
    public void actualizar(IntervencionDto intervencion) throws ServiciosException;
    public List<IntervencionDto> obtenerTodas() throws ServiciosException;

}
