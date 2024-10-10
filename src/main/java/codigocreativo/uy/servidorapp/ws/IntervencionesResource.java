package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.IntervencionDto;
import codigocreativo.uy.servidorapp.dtos.TiposEquipoDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.IntervencionRemote;
import codigocreativo.uy.servidorapp.servicios.TiposEquipoRemote;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

//TODO: Falta agregar filtros del listado
@Path("/intervenciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IntervencionesResource {
    @EJB
    private IntervencionRemote er;

    @POST
    @Path("/crear")
    public Response crear(IntervencionDto p) throws ServiciosException {
        this.er.crear(p);
        return Response.status(201).build();
    }

    @PUT
    @Path("/modificar")
    public Response modificar(IntervencionDto p) throws ServiciosException {
        this.er.actualizar(p);
        return Response.status(200).build();
    }

    @GET
    @Path("/listar")
    public List<IntervencionDto> listar() throws ServiciosException {
        return this.er.obtenerTodas();
    }

    @GET
    @Path("/seleccionar")
    public IntervencionDto buscarPorId(@QueryParam("id") Long id) throws ServiciosException {
        return this.er.buscarId(id);
    }

    @GET
    @Path("/reportePorFechas")
    public List<IntervencionDto> obtenerPorRangoDeFecha(@QueryParam("fechaDesde") String fechaDesde,
                                                        @QueryParam("fechaHasta") String fechaHasta,
                                                        @QueryParam("idEquipo") Long idEquipo) throws ServiciosException {
        LocalDateTime desde = LocalDateTime.parse(fechaDesde);
        LocalDateTime hasta = LocalDateTime.parse(fechaHasta);
        return this.er.obtenerPorRangoDeFecha(desde, hasta, idEquipo);
    }

    @GET
    @Path("/reportePorTipo")
    public Map<String, Long> obtenerCantidadPorTipo(@QueryParam("fechaDesde") String fechaDesde,
                                                    @QueryParam("fechaHasta") String fechaHasta,
                                                    @QueryParam("idTipo") Long idTipo) throws ServiciosException {
        LocalDateTime desde = fechaDesde != null ? LocalDateTime.parse(fechaDesde) : null;
        LocalDateTime hasta = fechaHasta != null ? LocalDateTime.parse(fechaHasta) : null;
        return this.er.obtenerCantidadPorTipo(desde, hasta, idTipo);
    }

}
