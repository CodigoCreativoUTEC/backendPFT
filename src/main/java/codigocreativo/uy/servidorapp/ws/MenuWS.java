package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.entidades.Funcionalidad;
import codigocreativo.uy.servidorapp.servicios.PermissionService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/menu")
public class MenuWS {
    
    @EJB
    private PermissionService permissionService;
    
    @Context
    private SecurityContext securityContext;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Funcionalidad> getUserMenu() {
        // Obtener el ID del usuario del contexto de seguridad
        Long userId = Long.parseLong(securityContext.getUserPrincipal().getName());
        return permissionService.getUserMenu(userId);
    }
} 