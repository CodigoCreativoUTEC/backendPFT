package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.TipoIntervencioneRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/tipoIntervenciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TipoIntervencionesResource {
    @EJB
    private TipoIntervencioneRemote er;

    @POST
    @Path("/crear")
    public Response crear(TiposIntervencioneDto p) throws ServiciosException {
        this.er.crearTipoIntervencion(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    public Response modificar(TiposIntervencioneDto p) throws ServiciosException {
        this.er.modificarTipoIntervencion(p);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/inactivar")
    public Response eliminar(@QueryParam("id") Long id) throws ServiciosException {
        this.er.eliminarTipoIntervencion(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/listarTodos")
    public List<TiposIntervencioneDto> listarTodos() throws ServiciosException {
        return this.er.obtenerTiposIntervenciones();
    }

    @GET
    @Path("/buscarPorId")
    public TiposIntervencioneDto buscarPorId(@QueryParam("id") Long id) throws ServiciosException {
        return this.er.obtenerTipoIntervencion(id);
    }
}