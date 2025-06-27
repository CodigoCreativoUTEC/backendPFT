package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.PaisDto;
import codigocreativo.uy.servidorapp.servicios.PaisRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Path("/paises")
@Tag(name = "Paises", description = "Gestión de países")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class PaisesResource {
    @EJB
    private PaisRemote er;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un país", description = "Crea un nuevo país en la base de datos", tags = { "Paises" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "País creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crearPais(PaisDto p) {
        this.er.crearPais(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar un país", description = "Modifica la información de un país existente en la base de datos", tags = { "Paises" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "País modificado correctamente"),
            @ApiResponse(responseCode = "404", description = "País no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificarPais(PaisDto p) {
        this.er.modificarPais(p);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todos los países", description = "Obtiene una lista de todos los países", tags = { "Paises" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de países obtenida correctamente", content = @Content(schema = @Schema(implementation = PaisDto.class)))
    })
    public List<PaisDto> listarPaises() {
        return this.er.obtenerpais();
    }
}
