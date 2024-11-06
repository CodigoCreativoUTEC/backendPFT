package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.servicios.BajaEquipoRemote;
import codigocreativo.uy.servidorapp.servicios.EquipoRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/equipos")
@Tag(name = "Mi Endpoint", description = "Gestión de mi endpoint")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EquipoResource {
    @EJB
    private EquipoRemote er;

    @EJB
    private BajaEquipoRemote ber;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un equipo", description = "Crea un equipo en la base de datos")
    public Response crearEquipo(EquipoDto equipo) {
        this.er.crearEquipo(equipo);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    public Response modificarProducto(EquipoDto equipo){
        this.er.modificarEquipo(equipo);
        return Response.status(200).build();
    }

    @PUT
    @Path("/inactivar")
    public Response eliminarEquipo(BajaEquipoDto equipo){
        this.er.eliminarEquipo(equipo);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    public List<EquipoDto> obtenerTodosLosEquipos(){
        return this.er.listarEquipos();
    }

    @GET
    @Path("/seleccionar")
    public EquipoDto buscarEquipo(@QueryParam("id") Long id){
        return this.er.obtenerEquipo(id);
    }

    @GET
    @Path("/filtrar")
    public List<EquipoDto> filtrar(@QueryParam("nombre") String nombre,
                                   @QueryParam("tipo") String tipo,
                                   @QueryParam("marca") String marca,
                                   @QueryParam("modelo") String modelo,
                                   @QueryParam("numeroSerie") String numeroSerie,
                                   @QueryParam("paisOrigen") String paisOrigen,
                                   @QueryParam("proveedor") String proveedor,
                                   @QueryParam("fechaAdquisicion") String fechaAdquisicion,
                                   @QueryParam("identificacionInterna") String identificacionInterna,
                                   @QueryParam("ubicacion") String ubicacion) {
        Map<String, String> filtros = new HashMap<>();
        if (nombre != null) filtros.put("nombre", nombre);
        if (tipo != null) filtros.put("tipo", tipo);
        if (marca != null) filtros.put("marca", marca);
        if (modelo != null) filtros.put("modelo", modelo);
        if (numeroSerie != null) filtros.put("numeroSerie", numeroSerie);
        if (paisOrigen != null) filtros.put("paisOrigen", paisOrigen);
        if (proveedor != null) filtros.put("proveedor", proveedor);
        if (fechaAdquisicion != null) filtros.put("fechaAdquisicion", fechaAdquisicion);
        if (identificacionInterna != null) filtros.put("identificacionInterna", identificacionInterna);
        if (ubicacion != null) filtros.put("ubicacion", ubicacion);

        return this.er.obtenerEquiposFiltrado(filtros);
    }


    @GET
    @Path("/listarBajas")
    public List<BajaEquipoDto> obtenerBajasEquipos() {return this.ber.obtenerBajasEquipos();}

    @GET
    @Path("/VerEquipoInactivo")
    public BajaEquipoDto obtenerBaja(@QueryParam("id") Long id) {
        return this.ber.obtenerBajaEquipo(id);
    }
}
