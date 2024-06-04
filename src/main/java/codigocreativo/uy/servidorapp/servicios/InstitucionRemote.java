package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.DTO.UbicacionDto;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface InstitucionRemote {
    public void agregarInstitucion(InstitucionDto i);
    public void eliminarInstitucion(InstitucionDto i);
    public void modificarInstitucion(InstitucionDto i);
    public List<UbicacionDto> obtenerUbicaciones();
    public List<InstitucionDto> obtenerInstituciones();
    public InstitucionDto obtenerInstitucionPorNombre(String nombre);
    public InstitucionDto obtenerInstitucionPorId(Long id);
}
