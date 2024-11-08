package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.PerfilRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/perfiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerfilResource {

    @EJB
    private PerfilRemote perfilRemote;

    @POST
    @Path("/crear")
    public Response crearPerfil(PerfilDto perfil) {
        perfilRemote.crearPerfil(perfil);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/modificar")
    public Response modificarPerfil(PerfilDto perfil) {
        perfilRemote.modificarPerfil(perfil);
        return Response.ok().build();
    }

    @DELETE
    @Path("/inactivar")
    public Response eliminarPerfil(@QueryParam("id") Long id) {
        PerfilDto perfil = perfilRemote.obtenerPerfil(id);
        if (perfil != null) {
            perfilRemote.eliminarPerfil(perfil);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/seleccionar")
    public Response obtenerPerfil(@QueryParam("id") Long id) {
        PerfilDto perfil = perfilRemote.obtenerPerfil(id);
        if (perfil != null) {
            return Response.ok(perfil).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/listar")
    public List<PerfilDto> obtenerPerfiles() {
        return perfilRemote.obtenerPerfiles();
    }

    @GET
    @Path("/buscarPorNombre")
    public List<PerfilDto> listarPerfilesPorNombre(@QueryParam("nombre") String nombre) {
        return perfilRemote.listarPerfilesPorNombre(nombre);
    }

    @GET
    @Path("/buscarPorEstado")
    public List<PerfilDto> listarPerfilesPorEstado(@QueryParam("estado") Estados estado) {
        return perfilRemote.listarPerfilesPorEstado(estado);
    }
}