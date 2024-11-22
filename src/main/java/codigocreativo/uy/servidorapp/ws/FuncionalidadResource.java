package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.servicios.FuncionalidadRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Path("/funcionalidades")
@Tag(name = "Funcionalidades", description = "Gestión de funcionalidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FuncionalidadResource {
    @EJB
    private FuncionalidadRemote funcionalidadRemote;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear una funcionalidad", description = "Crea una nueva funcionalidad en la base de datos", tags = { "Funcionalidades" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionalidad creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crear(FuncionalidadDto funcionalidad) {
        this.funcionalidadRemote.crear(funcionalidad);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar una funcionalidad", description = "Modifica una funcionalidad existente en la base de datos", tags = { "Funcionalidades" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionalidad modificada correctamente"),
            @ApiResponse(responseCode = "404", description = "Funcionalidad no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificar(FuncionalidadDto funcionalidad) {
        this.funcionalidadRemote.actualizar(funcionalidad);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/eliminar/{id}")
    @Operation(summary = "Eliminar una funcionalidad", description = "Elimina una funcionalidad existente por su ID", tags = { "Funcionalidades" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionalidad eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Funcionalidad no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response eliminar(@Parameter(description = "ID de la funcionalidad a eliminar", required = true) @PathParam("id") Long id) {
        this.funcionalidadRemote.eliminar(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar funcionalidades", description = "Obtiene una lista de todas las funcionalidades", tags = { "Funcionalidades" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de funcionalidades obtenida correctamente", content = @Content(schema = @Schema(implementation = FuncionalidadDto.class)))
    })
    public List<FuncionalidadDto> listar() {
        return this.funcionalidadRemote.obtenerTodas();
    }

    @GET
    @Path("/buscar/{id}")
    @Operation(summary = "Buscar una funcionalidad por ID", description = "Obtiene la información de una funcionalidad específica por su ID", tags = { "Funcionalidades" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionalidad encontrada", content = @Content(schema = @Schema(implementation = FuncionalidadDto.class))),
            @ApiResponse(responseCode = "404", description = "Funcionalidad no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public FuncionalidadDto buscarPorId(@Parameter(description = "ID de la funcionalidad a buscar", required = true) @PathParam("id") Long id) {
        return this.funcionalidadRemote.buscarPorId(id);
    }
}
