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

import java.util.List;

@Path("/marca")
@Tag(name = "Marcas", description = "Gestión de marcas de modelos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class MarcaResource {
    @EJB
    private MarcasModeloRemote er;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear una marca", description = "Crea una nueva marca en la base de datos", tags = { "Marcas" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crearMarca(MarcasModeloDto p) {
        this.er.crearMarcasModelo(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar una marca", description = "Modifica la información de una marca existente en la base de datos", tags = { "Marcas" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca modificada correctamente"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificarMarca(MarcasModeloDto p) {
        this.er.modificarMarcasModelo(p);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/inactivar")
    @Operation(summary = "Inactivar una marca", description = "Inactiva una marca en la base de datos", tags = { "Marcas" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca inactivada correctamente"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response eliminarMarca(@Parameter(description = "ID de la marca a inactivar", required = true) @QueryParam("id") Long id) {
        this.er.eliminarMarca(id);
        return Response.status(200).build();
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
}
