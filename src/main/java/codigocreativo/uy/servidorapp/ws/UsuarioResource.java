package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
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


    @GET
    @Path("/ListarTodosLosUsuarios")
    public List<UsuarioDto> obtenerTodosLosUsuarios(){
        return this.er.obtenerUsuarios();
    }

}
