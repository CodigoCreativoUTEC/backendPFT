package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.UbicacionRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@Path("/ubicaciones")
@Tag(name = "Ubicaciones", description = "Gestión de ubicaciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class UbicacionesResource {
    @EJB
    private UbicacionRemote er;

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todas las ubicaciones", description = "Obtiene una lista de todas las ubicaciones disponibles", tags = { "Ubicaciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ubicaciones obtenida correctamente", content = @Content(schema = @Schema(implementation = UbicacionDto.class))),
            @ApiResponse(responseCode = "500", description = "Error al listar ubicaciones", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public List<UbicacionDto> listarUbicaciones() throws ServiciosException {
        try {
            return this.er.listarUbicaciones();
        } catch (ServiciosException e) {
            throw new ServiciosException("Error al listar ubicaciones");
        }
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar una ubicación por ID", description = "Obtiene la información de una ubicación específica por su ID", tags = { "Ubicaciones" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ubicación encontrada", content = @Content(schema = @Schema(implementation = UbicacionDto.class))),
            @ApiResponse(responseCode = "404", description = "Ubicación no encontrada", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public UbicacionDto buscarPorId(@Parameter(description = "ID de la ubicación a buscar", required = true) @QueryParam("id") Long id) throws ServiciosException {
        return this.er.obtenerUbicacionPorId(id);
    }
}
