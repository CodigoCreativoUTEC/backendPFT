package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.servicios.FuncionalidadRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/funcionalidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FuncionalidadResource {

    @EJB
    private FuncionalidadRemote funcionalidadRemote;

    @POST
    @Path("/crear")
    public Response crear(FuncionalidadDto funcionalidadDto) {
        funcionalidadRemote.crear(funcionalidadDto);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    public Response modificar(FuncionalidadDto funcionalidadDto){
        funcionalidadRemote.actualizar(funcionalidadDto);
        return Response.status(200).build();
    }
    @DELETE
    @Path("/inactivar")
    public Response eliminar(@QueryParam("id") Long id)  {
        funcionalidadRemote.eliminar(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    public List<FuncionalidadDto> listar() {
        return funcionalidadRemote.obtenerTodas();
    }

    @GET
    @Path("/seleccionar")
    public FuncionalidadDto buscarPorId(@QueryParam("id") Long id) {
        return funcionalidadRemote.buscarPorId(id);
    }
}
