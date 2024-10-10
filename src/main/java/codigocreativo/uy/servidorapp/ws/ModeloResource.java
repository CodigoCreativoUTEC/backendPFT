package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.ModelosEquipoRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

//TODO: Falta agregar filtros del listado
@Path("/modelo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ModeloResource {
    @EJB
    private ModelosEquipoRemote er;

    @POST
    @Path("/crear")
    public Response crearModelo(ModelosEquipoDto p) {
        p.setEstado(Estados.ACTIVO);
        this.er.crearModelos(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    public Response modificarModelo(ModelosEquipoDto p){
        this.er.modificarModelos(p);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/inactivar")
    public Response eliminarModelo(@QueryParam("id") Long id){
        this.er.eliminarModelos(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    public List<ModelosEquipoDto> listarTodos(){
        return this.er.listarModelos();
    }

    @GET
    @Path("/seleccionar")
    public ModelosEquipoDto buscarPorId(@QueryParam("id") Long id){
        return this.er.obtenerModelos(id);
    }
}