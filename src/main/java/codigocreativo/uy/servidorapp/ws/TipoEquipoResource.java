package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.TiposEquipoDto;
import codigocreativo.uy.servidorapp.servicios.TiposEquipoRemote;
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

@Path("/tipoEquipos")
@Tag(name = "Tipos de Equipos", description = "Gestión de tipos de equipos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class TipoEquipoResource {
    @EJB
    private TiposEquipoRemote er;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un tipo de equipo", description = "Crea un nuevo tipo de equipo en la base de datos", tags = { "Tipos de Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de equipo creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crear(@Parameter(description = "Datos del tipo de equipo a crear", required = true) TiposEquipoDto p) {
        this.er.crearTiposEquipo(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar un tipo de equipo", description = "Modifica la información de un tipo de equipo existente en la base de datos", tags = { "Tipos de Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de equipo modificado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de equipo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificar(@Parameter(description = "Datos del tipo de equipo a modificar", required = true) TiposEquipoDto p) {
        this.er.modificarTiposEquipo(p);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/inactivar")
    @Operation(summary = "Inactivar un tipo de equipo", description = "Inactiva un tipo de equipo en la base de datos", tags = { "Tipos de Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de equipo inactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de equipo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response eliminar(@Parameter(description = "ID del tipo de equipo a inactivar", required = true) @QueryParam("id") Long id) {
        this.er.eliminarTiposEquipo(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todos los tipos de equipos", description = "Obtiene una lista de todos los tipos de equipos", tags = { "Tipos de Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tipos de equipos obtenida correctamente", content = @Content(schema = @Schema(implementation = TiposEquipoDto.class)))
    })
    public List<TiposEquipoDto> listar() {
        return this.er.listarTiposEquipo();
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar un tipo de equipo por ID", description = "Obtiene la información de un tipo de equipo específico por su ID", tags = { "Tipos de Equipos" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de equipo encontrado", content = @Content(schema = @Schema(implementation = TiposEquipoDto.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de equipo no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public TiposEquipoDto buscarPorId(@Parameter(description = "ID del tipo de equipo a buscar", required = true) @QueryParam("id") Long id) {
        return this.er.obtenerPorId(id);
    }
}
