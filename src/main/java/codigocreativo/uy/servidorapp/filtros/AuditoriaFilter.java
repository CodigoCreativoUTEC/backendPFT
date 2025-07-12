package codigocreativo.uy.servidorapp.filtros;


import jakarta.servlet.annotation.WebFilter;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Provider
@WebFilter(urlPatterns = "/*")
public class AuditoriaFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("REQUEST [").append(timestamp).append("] ");
        logMessage.append(method).append(" ").append(path);
        
        // Log de la petición (comentado para producción)
        // System.out.println(logMessage.toString());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        int status = responseContext.getStatus();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("RESPONSE [").append(timestamp).append("] ");
        logMessage.append(method).append(" ").append(path);
        logMessage.append(" -> ").append(status);
        
        // Log de la respuesta (comentado para producción)
        // System.out.println(logMessage.toString());
    }
} 