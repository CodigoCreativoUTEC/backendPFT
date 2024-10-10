package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.ProveedoresEquipoRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/proveedores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProveedoresResource {
    @EJB
    private ProveedoresEquipoRemote er;

    @POST
    @Path("/crear")
    public Response crearProveedor(ProveedoresEquipoDto p) {
        this.er.crearProveedor(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    public Response modificarProveedor(ProveedoresEquipoDto p){
        this.er.modificarProveedor(p);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/inactivar")
    public Response eliminarProveedor(@QueryParam("id") Long id){
        this.er.eliminarProveedor(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    public List<ProveedoresEquipoDto> listarProveedores(){
        return this.er.obtenerProveedores();
    }

    @GET
    @Path("/seleccionar")
    public ProveedoresEquipoDto buscarPorId(@QueryParam("id") Long id){
        return this.er.obtenerProveedor(id);
    }

    @GET
    @Path("/buscar")
    public List<ProveedoresEquipoDto> buscarProveedores(@QueryParam("nombre") String nombre, @QueryParam("estado") Estados estado) {
        return this.er.buscarProveedores(nombre, estado);
    }
}