package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.DTO.EquipoDto;
import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.UsuarioRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    @EJB
    private UsuarioRemote er;

    @GET
    @Path("/Login")
    public UsuarioDto loginUsuario(@QueryParam("usuario")  String usuario, @QueryParam("password") String password){
        return this.er.login(usuario, password);
    }

    @POST
    @Path("")
    public Response crearEquipo(UsuarioDto usuario) {
        this.er.crearUsuario(usuario);
        return Response.status(201).build();
    }

    @PUT
    @Path("")
    public Response modificarUsuario(UsuarioDto usuario){
        this.er.modificarUsuario(usuario);
        return Response.status(200).build();
    }

    @PUT
    @Path("Inactivar")
    public Response inactivarUsuario(UsuarioDto usuario){
        this.er.eliminarUsuario(usuario);
        return Response.status(200).build();
    }

    @GET
    @Path("/BuscarUsuarioPorCI")
    public UsuarioDto buscarEquipo(@QueryParam("ci") String ci){
        return this.er.obtenerUsuarioPorCI(ci);
    }

    @GET
    @Path("/BuscarUsuarioPorId")
    public UsuarioDto buscarEquipo(@QueryParam("id") Long id){
        return this.er.obtenerUsuario(id);
    }

    @GET
    @Path("/ObtenerUsuarioPorEstado")
    public List<UsuarioDto> obtenerUsuarioPorEstado(@QueryParam("estado") Estados estado){
        return this.er.obtenerUsuariosPorEstado(estado);
    }

    @GET
    @Path("/ListarTodosLosUsuarios")
    public List<UsuarioDto> obtenerTodosLosUsuarios(){
        return this.er.obtenerUsuarios();
    }
}
