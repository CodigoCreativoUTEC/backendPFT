package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.IntervencionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Path("/intervenciones")
@Tag(name = "Intervenciones", description = "Gestión de intervenciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class IntervencionesResource {
    @EJB
    private IntervencionRemote er;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear una intervención", description = "Crea una nueva intervención en la base de datos", tags = { "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Intervención creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crear(@Parameter(description = "Token de autorización JWT", required = true, in = ParameterIn.HEADER) @HeaderParam("Authorization") String authorization, IntervencionDto p) throws ServiciosException {
        this.er.crear(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar una intervención", description = "Modifica una intervención existente en la base de datos", tags = { "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intervención modificada correctamente"),
            @ApiResponse(responseCode = "404", description = "Intervención no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificar(@Parameter(description = "Token de autorización JWT", required = true, in = ParameterIn.HEADER) @HeaderParam("Authorization") String authorization, IntervencionDto p) throws ServiciosException {
        this.er.actualizar(p);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar intervenciones", description = "Obtiene una lista de todas las intervenciones", tags = { "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de intervenciones obtenida correctamente", content = @Content(schema = @Schema(implementation = IntervencionDto.class)))
    })
    public List<IntervencionDto> listar(@Parameter(description = "Token de autorización JWT", required = true, in = ParameterIn.HEADER) @HeaderParam("Authorization") String authorization) throws ServiciosException {
        return this.er.obtenerTodas();
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar una intervención por ID", description = "Obtiene la información de una intervención específica por su ID", tags = { "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intervención encontrada", content = @Content(schema = @Schema(implementation = IntervencionDto.class))),
            @ApiResponse(responseCode = "404", description = "Intervención no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public IntervencionDto buscarPorId(@Parameter(description = "Token de autorización JWT", required = true, in = ParameterIn.HEADER) @HeaderParam("Authorization") String authorization, @Parameter(description = "ID de la intervención a buscar", required = true) @QueryParam("id") Long id) throws ServiciosException {
        return this.er.buscarId(id);
    }

    @GET
    @Path("/reportePorFechas")
    @Operation(summary = "Obtener intervenciones por rango de fechas", description = "Obtiene una lista de intervenciones en un rango de fechas especificado", tags = { "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de intervenciones obtenida correctamente", content = @Content(schema = @Schema(implementation = IntervencionDto.class)))
    })
    public List<IntervencionDto> obtenerPorRangoDeFecha(
            @Parameter(description = "Token de autorización JWT", required = true, in = ParameterIn.HEADER) @HeaderParam("Authorization") String authorization,
            @Parameter(description = "Fecha desde en formato ISO8601", required = true) @QueryParam("fechaDesde") String fechaDesde,
            @Parameter(description = "Fecha hasta en formato ISO8601", required = true) @QueryParam("fechaHasta") String fechaHasta,
            @Parameter(description = "ID del equipo") @QueryParam("idEquipo") Long idEquipo) throws ServiciosException {
        LocalDateTime desde = LocalDateTime.parse(fechaDesde);
        LocalDateTime hasta = LocalDateTime.parse(fechaHasta);
        return this.er.obtenerPorRangoDeFecha(desde, hasta, idEquipo);
    }

    @GET
    @Path("/reportePorTipo")
    @Operation(summary = "Obtener cantidad de intervenciones por tipo", description = "Obtiene un reporte de la cantidad de intervenciones por tipo en un rango de fechas especificado", tags = { "Intervenciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte obtenido correctamente", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    public Map<String, Long> obtenerCantidadPorTipo(
            @Parameter(description = "Token de autorización JWT", required = true, in = ParameterIn.HEADER) @HeaderParam("Authorization") String authorization,
            @Parameter(description = "Fecha desde en formato ISO8601") @QueryParam("fechaDesde") String fechaDesde,
            @Parameter(description = "Fecha hasta en formato ISO8601") @QueryParam("fechaHasta") String fechaHasta,
            @Parameter(description = "ID del tipo de intervención") @QueryParam("idTipo") Long idTipo) throws ServiciosException {
        LocalDateTime desde = fechaDesde != null ? LocalDateTime.parse(fechaDesde) : null;
        LocalDateTime hasta = fechaHasta != null ? LocalDateTime.parse(fechaHasta) : null;
        return this.er.obtenerCantidadPorTipo(desde, hasta, idTipo);
    }
}
