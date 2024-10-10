package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.servicios.MarcasModeloRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
//TODO: Falta agregar filtros del listado
@Path("/marca")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarcaResource {
    @EJB
    private MarcasModeloRemote er;

    @POST
    @Path("/crear")
    public Response crearMarca(MarcasModeloDto p) {
        this.er.crearMarcasModelo(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    public Response modificarMarca(MarcasModeloDto p){
        this.er.modificarMarcasModelo(p);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/inactivar")
    public Response eliminarMarca(@QueryParam("id") Long id){
        this.er.eliminarMarca(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    public List<MarcasModeloDto> listarTodas(){
        return this.er.obtenerMarcasLista();
    }

    @GET
    @Path("/seleccionar")
    public MarcasModeloDto buscarPorId(@QueryParam("id") Long id){
        return this.er.obtenerMarca(id);
    }

}
