package codigocreativo.uy.servidorapp.ws;


import codigocreativo.uy.servidorapp.dtos.PaisDto;
import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.servicios.PaisRemote;
import codigocreativo.uy.servidorapp.servicios.ProveedoresEquipoRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

//TODO: Falta agregar filtros del listado
@Path("/paises")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaisesResource {
    @EJB
    private PaisRemote er;

    @POST
    @Path("/crear")
    public Response crearPais(PaisDto p) {
        this.er.crearPais(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    public Response modificarPais(PaisDto p){
        this.er.modificarPais(p);
        return Response.status(200).build();
    }

/*  @DELETE
    @Path("/inactivar")
    public Response eliminarPais(@QueryParam("id") Long id){
        this.er.(id);
        return Response.status(200).build();
    }*/

    @GET
    @Path("/listar")
    public List<PaisDto> listarProveedores(){
        return this.er.obtenerpais();
    }

}