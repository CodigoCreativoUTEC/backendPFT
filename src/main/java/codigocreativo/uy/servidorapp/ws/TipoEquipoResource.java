package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.dtos.TiposEquipoDto;
import codigocreativo.uy.servidorapp.servicios.MarcasModeloRemote;
import codigocreativo.uy.servidorapp.servicios.TiposEquipoRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

//TODO: Falta agregar filtros del listado
@Path("/tipoEquipos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TipoEquipoResource {
    @EJB
    private TiposEquipoRemote er;

    @POST
    @Path("/crear")
    public Response crear(TiposEquipoDto p) {
        this.er.crearTiposEquipo(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    public Response modificar(TiposEquipoDto p){
        this.er.modificarTiposEquipo(p);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/inactivar")
    public Response eliminar(@QueryParam("id") Long id){
        this.er.eliminarTiposEquipo(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/listarTodos")
    public List<TiposEquipoDto> listar(){
        return this.er.listarTiposEquipo();
    }

    @GET
    @Path("/buscarPorId")
    public TiposEquipoDto buscarPorId(@QueryParam("id") Long id){
        return this.er.obtenerPorId(id);
    }

}
