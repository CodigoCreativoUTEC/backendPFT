package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.ModelosEquipoRemote;
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

import java.util.List;

@Path("/modelo")
@Tag(name = "Modelos", description = "Gestión de modelos de equipos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class ModeloResource {
    @EJB
    private ModelosEquipoRemote er;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un modelo", description = "Crea un nuevo modelo de equipo en la base de datos", tags = { "Modelos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Modelo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crearModelo(ModelosEquipoDto p) {
        p.setEstado(Estados.ACTIVO);
        this.er.crearModelos(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar un modelo", description = "Modifica la información de un modelo existente en la base de datos", tags = { "Modelos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modelo modificado correctamente"),
            @ApiResponse(responseCode = "404", description = "Modelo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificarModelo(ModelosEquipoDto p) {
        this.er.modificarModelos(p);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/inactivar")
    @Operation(summary = "Inactivar un modelo", description = "Inactiva un modelo en la base de datos", tags = { "Modelos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modelo inactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Modelo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response eliminarModelo(@Parameter(description = "ID del modelo a inactivar", required = true) @QueryParam("id") Long id) {
        this.er.eliminarModelos(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todos los modelos", description = "Obtiene una lista de todos los modelos de equipos", tags = { "Modelos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de modelos obtenida correctamente", content = @Content(schema = @Schema(implementation = ModelosEquipoDto.class)))
    })
    public List<ModelosEquipoDto> listarTodos() {
        return this.er.listarModelos();
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar un modelo por ID", description = "Obtiene la información de un modelo específico por su ID", tags = { "Modelos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modelo encontrado", content = @Content(schema = @Schema(implementation = ModelosEquipoDto.class))),
            @ApiResponse(responseCode = "404", description = "Modelo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ModelosEquipoDto buscarPorId(@Parameter(description = "ID del modelo a buscar", required = true) @QueryParam("id") Long id) {
        return this.er.obtenerModelos(id);
    }
}
