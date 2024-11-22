package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.ProveedoresEquipoRemote;
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

@Path("/proveedores")
@Tag(name = "Proveedores", description = "Gestión de proveedores de equipos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "BearerAuth")
public class ProveedoresResource {
    @EJB
    private ProveedoresEquipoRemote er;

    @POST
    @Path("/crear")
    @Operation(summary = "Crear un proveedor", description = "Crea un nuevo proveedor de equipos en la base de datos", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proveedor creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response crearProveedor(ProveedoresEquipoDto p) {
        this.er.crearProveedor(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    @Operation(summary = "Modificar un proveedor", description = "Modifica la información de un proveedor existente en la base de datos", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor modificado correctamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response modificarProveedor(ProveedoresEquipoDto p) {
        this.er.modificarProveedor(p);
        return Response.status(200).build();
    }

    @DELETE
    @Path("/inactivar")
    @Operation(summary = "Inactivar un proveedor", description = "Inactiva un proveedor en la base de datos", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor inactivado correctamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public Response eliminarProveedor(@Parameter(description = "ID del proveedor a inactivar", required = true) @QueryParam("id") Long id) {
        this.er.eliminarProveedor(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    @Operation(summary = "Listar todos los proveedores", description = "Obtiene una lista de todos los proveedores de equipos", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de proveedores obtenida correctamente", content = @Content(schema = @Schema(implementation = ProveedoresEquipoDto.class)))
    })
    public List<ProveedoresEquipoDto> listarProveedores() {
        return this.er.obtenerProveedores();
    }

    @GET
    @Path("/seleccionar")
    @Operation(summary = "Buscar un proveedor por ID", description = "Obtiene la información de un proveedor específico por su ID", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado", content = @Content(schema = @Schema(implementation = ProveedoresEquipoDto.class))),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ProveedoresEquipoDto buscarPorId(@Parameter(description = "ID del proveedor a buscar", required = true) @QueryParam("id") Long id) {
        return this.er.obtenerProveedor(id);
    }

    @GET
    @Path("/buscar")
    @Operation(summary = "Buscar proveedores por nombre y estado", description = "Obtiene una lista de proveedores que coinciden con el nombre y el estado especificado", tags = { "Proveedores" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de proveedores obtenida correctamente", content = @Content(schema = @Schema(implementation = ProveedoresEquipoDto.class)))
    })
    public List<ProveedoresEquipoDto> buscarProveedores(
            @Parameter(description = "Nombre del proveedor a buscar") @QueryParam("nombre") String nombre,
            @Parameter(description = "Estado del proveedor a buscar") @QueryParam("estado") Estados estado) {
        return this.er.buscarProveedores(nombre, estado);
    }
}
