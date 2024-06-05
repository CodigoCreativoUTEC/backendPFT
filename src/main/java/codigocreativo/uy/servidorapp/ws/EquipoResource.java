package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.DTO.EquipoDto;
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

    @POST
    @Path("")
    public Response crearEquipo(EquipoDto equipo) {
        this.er.crearEquipo(equipo);
        return Response.status(201).build();
    }

    @PUT
    @Path("")
    public Response modificarProducto(EquipoDto equipo){
        this.er.modificarEquipo(equipo);
        return Response.status(200).build();
    }

    //TODO: Muestra Error al intentar obtener todos los equipos.
    // RESTEASY008205: JSON Binding serialization error jakarta.json.bind.JsonbException:
    // Unable to serialize property 'equiposUbicaciones' from codigocreativo.uy.servidorapp.DTO.EquipoDto

    @GET
    @Path("/ListarTodosLosEquipos")
    public List<EquipoDto> obtenerTodosLosEquipos(){
        return this.er.listarEquipos();
    }

}
