package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.servicios.MarcasModeloRemote;
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
import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.util.List;

@Path("/marca")
@Tag(name = "Marcas", description = "Gestión de marcas de modelos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class MarcaResource {
    private static final String MESSAGE = "message";
    @EJB
    private MarcasModeloRemote er;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear una marca", description = "Crea una nueva marca en la base de datos", tags = { "Marcas" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Marca creada correctamente",
            content = @Content(schema = @Schema(example = "{\"message\": \"Marca creada correctamente\"}"))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
            content = @Content(schema = @Schema(example = "{\"message\": \"Error al crear la marca\"}")))
    })
    public Response crearMarca(MarcasModeloDto p) {
        try {
            this.er.crearMarcasModelo(p);
            return Response.status(201)
                .entity(java.util.Map.of(MESSAGE, "Marca creada correctamente"))
                .build();
        } catch (Exception e) {
            return Response.status(400)
                .entity(java.util.Map.of(MESSAGE, "Error al crear la marca: " + e.getMessage()))
                .build();
        }
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar una marca", description = "Modifica la información de una marca existente en la base de datos", tags = { "Marcas" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marca modificada correctamente",
            content = @Content(schema = @Schema(example = "{\"message\": \"Marca modificada correctamente\"}"))),
        @ApiResponse(responseCode = "400", description = "No se permite modificar el nombre ni el estado",
            content = @Content(schema = @Schema(example = "{\"error\": \"No se permite modificar el nombre ni el estado de la marca."))),
        @ApiResponse(responseCode = "404", description = "Marca no encontrada",
            content = @Content(schema = @Schema(example = "{\"error\": \"Marca no encontrada")))
    })
    public Response modificarMarca(MarcasModeloDto p) {
        try {
            MarcasModeloDto actual = er.obtenerMarca(p.getId());
            if (actual == null) {
                return Response.status(404).entity(java.util.Map.of("error", "Marca no encontrada")).build();
            }
            if (!actual.getNombre().equals(p.getNombre())) {
                return Response.status(400).entity(java.util.Map.of("error", "No se permite modificar el nombre de la marca.")).build();
            }
            this.er.modificarMarcasModelo(p);
            return Response.ok(java.util.Map.of(MESSAGE, "Marca modificada correctamente")).build();
        } catch (Exception e) {
            return Response.status(400).entity(java.util.Map.of("error", "Error al modificar la marca: " + e.getMessage())).build();
        }
    }

    @PUT
    @Path("/inactivar")
    @Operation(summary = "Inactivar una marca", description = "Inactiva una marca en la base de datos", tags = { "Marcas" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marca inactivada correctamente",
            content = @Content(schema = @Schema(example = "{\"message\": \"Marca inactivada correctamente\"}"))),
        @ApiResponse(responseCode = "404", description = "Marca no encontrada",
            content = @Content(schema = @Schema(example = "{\"message\": \"Marca no encontrada\"}")))
    })
    public Response eliminarMarca(@Parameter(description = "ID de la marca a inactivar", required = true) @QueryParam("id") Long id) {
        try {
            this.er.eliminarMarca(id);
            return Response.ok(java.util.Map.of(MESSAGE, "Marca inactivada correctamente")).build();
        } catch (Exception e) {
            return Response.status(404)
                .entity(java.util.Map.of(MESSAGE, "Marca no encontrada: " + e.getMessage()))
                .build();
        }
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todas las marcas", description = "Obtiene una lista de todas las marcas de modelos", tags = { "Marcas" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de marcas obtenida correctamente", content = @Content(schema = @Schema(implementation = MarcasModeloDto.class)))
    })
    public List<MarcasModeloDto> listarTodas() {
        return this.er.obtenerMarcasLista();
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar una marca por ID", description = "Obtiene la información de una marca específica por su ID", tags = { "Marcas" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca encontrada", content = @Content(schema = @Schema(implementation = MarcasModeloDto.class))),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public MarcasModeloDto buscarPorId(@Parameter(description = "ID de la marca a buscar", required = true) @QueryParam("id") Long id) {
        return this.er.obtenerMarca(id);
    }

    @GET
    @Path("/filtrar")
    @Operation(summary = "Filtrar marcas por estado", description = "Obtiene una lista de marcas filtradas por estado", tags = { "Marcas" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de marcas filtrada correctamente"),
        @ApiResponse(responseCode = "400", description = "Estado inválido", content = @Content(schema = @Schema(example = "{\"error\": \"Estado inválido\"}")))
    })
    public Response filtrarPorEstado(@QueryParam("estado") String estado) {
        try {
            Estados estadoEnum = Estados.valueOf(estado);
            return Response.ok(er.obtenerMarcasPorEstadoLista(estadoEnum)).build();
        } catch (Exception e) {
            return Response.status(400).entity(java.util.Map.of("error", "Estado inválido: " + e.getMessage())).build();
        }
    }
}
