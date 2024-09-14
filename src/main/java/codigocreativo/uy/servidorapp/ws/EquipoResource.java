package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.servicios.BajaEquipoRemote;
import codigocreativo.uy.servidorapp.servicios.EquipoRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/equipos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EquipoResource {
    @EJB
    private EquipoRemote er;

    @EJB
    private BajaEquipoRemote ber;

    @POST
    @Path("/CrearEquipo")
    public Response crearEquipo(EquipoDto equipo) {
        this.er.crearEquipo(equipo);
        return Response.status(201).build();
    }

    @PUT
    @Path("/ModificarEquipo")
    public Response modificarProducto(EquipoDto equipo){
        this.er.modificarEquipo(equipo);
        return Response.status(200).build();
    }

    @PUT
    @Path("/Inactivar")
    public Response eliminarEquipo(BajaEquipoDto equipo){
        this.er.eliminarEquipo(equipo);
        return Response.status(200).build();
    }

    @GET
    @Path("/ListarTodosLosEquipos")
    public List<EquipoDto> obtenerTodosLosEquipos(){
        return this.er.listarEquipos();
    }

    @GET
    @Path("/BuscarEquipo")
    public EquipoDto buscarEquipo(@QueryParam("id") Long id){
        return this.er.obtenerEquipo(id);
    }

    @GET
    @Path("ListarEquiposFiltrados")
    public List<EquipoDto> obtenerEquiposFiltrados(@QueryParam("filtro")  String filtro, @QueryParam("valor") String valor){
        return this.er.obtenerEquiposFiltrado(filtro,valor);
    }

    @GET
    @Path("/ListarBajaEquipos")
    public List<BajaEquipoDto> obtenerBajasEquipos() {return this.ber.obtenerBajasEquipos();}

    @GET
    @Path("/VerEquipoInactivo")
    public BajaEquipoDto obtenerBaja(@QueryParam("id") Long id) {
        return this.ber.obtenerBajaEquipo(id);
    }
}
