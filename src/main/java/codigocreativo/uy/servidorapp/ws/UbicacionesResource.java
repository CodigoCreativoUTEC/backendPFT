package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.UbicacionRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/ubicaciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UbicacionesResource {
    @EJB
    private UbicacionRemote er;

    @GET
    @Path("/listar")
    public List<UbicacionDto> listarUbicaciones(){
        try {
            return this.er.listarUbicaciones();
        } catch (ServiciosException e) {
            throw new RuntimeException(e);
        }
    }

}
