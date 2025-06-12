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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/equipos")
@Tag(name = "Equipos", description = "Gestión de equipos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EquipoResource {
    @EJB
    private EquipoRemote er;

    @EJB
    private BajaEquipoRemote ber;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un equipo", description = "Crea un equipo en la base de datos", tags = { "Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Equipo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crearEquipo(EquipoDto equipo) {
        this.er.crearEquipo(equipo);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar un equipo", description = "Modifica la información de un equipo existente en la base de datos", tags = { "Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo modificado correctamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificarProducto(EquipoDto equipo) {
        this.er.modificarEquipo(equipo);
        return Response.status(200).build();
    }

    @PUT
    @Path("/inactivar")
    @Operation(summary = "Inactivar un equipo", description = "Inactiva un equipo en la base de datos", tags = { "Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo inactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response eliminarEquipo(BajaEquipoDto equipo) {
        this.er.eliminarEquipo(equipo);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar equipos", description = "Obtiene una lista de todos los equipos", tags = { "Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipos obtenida correctamente", content = @Content(schema = @Schema(implementation = EquipoDto.class))),
    @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content(schema = @Schema(implementation = String.class)))
        })
    public List<EquipoDto> obtenerTodosLosEquipos() {
        return this.er.listarEquipos();
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar un equipo", description = "Obtiene la información de un equipo específico por su ID", tags = { "Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo encontrado", content = @Content(schema = @Schema(implementation = EquipoDto.class))),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public EquipoDto buscarEquipo(@Parameter(description = "ID del equipo a buscar", required = true) @QueryParam("id") Long id) {
        return this.er.obtenerEquipo(id);
    }

    @GET
    @Path("/filtrar")
    @Operation(summary = "Filtrar equipos", description = "Filtra los equipos según los criterios proporcionados", tags = { "Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipos filtrada correctamente", content = @Content(schema = @Schema(implementation = EquipoDto.class)))
    })
    public List<EquipoDto> filtrar(
            @Parameter(description = "Nombre del equipo") @QueryParam("nombre") String nombre,
            @Parameter(description = "Tipo del equipo") @QueryParam("tipo") String tipo,
            @Parameter(description = "Marca del equipo") @QueryParam("marca") String marca,
            @Parameter(description = "Modelo del equipo") @QueryParam("modelo") String modelo,
            @Parameter(description = "Número de serie del equipo") @QueryParam("numeroSerie") String numeroSerie,
            @Parameter(description = "País de origen del equipo") @QueryParam("paisOrigen") String paisOrigen,
            @Parameter(description = "Proveedor del equipo") @QueryParam("proveedor") String proveedor,
            @Parameter(description = "Fecha de adquisición del equipo") @QueryParam("fechaAdquisicion") String fechaAdquisicion,
            @Parameter(description = "Identificación interna del equipo") @QueryParam("identificacionInterna") String identificacionInterna,
            @Parameter(description = "Ubicación del equipo") @QueryParam("ubicacion") String ubicacion) {
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
    @Operation(summary = "Listar equipos dados de baja", description = "Obtiene una lista de todos los equipos dados de baja", tags = { "Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipos dados de baja obtenida correctamente", content = @Content(schema = @Schema(implementation = BajaEquipoDto.class)))
    })
    public List<BajaEquipoDto> obtenerBajasEquipos() {
        return this.ber.obtenerBajasEquipos();
    }

    @GET
    @Path("/VerEquipoInactivo")
    @Operation(summary = "Obtener un equipo inactivo", description = "Obtiene la información de un equipo dado de baja por su ID", tags = { "Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipo dado de baja encontrado", content = @Content(schema = @Schema(implementation = BajaEquipoDto.class))),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public BajaEquipoDto obtenerBaja(@Parameter(description = "ID del equipo dado de baja a buscar", required = true) @QueryParam("id") Long id) {
        return this.ber.obtenerBajaEquipo(id);
    }
}