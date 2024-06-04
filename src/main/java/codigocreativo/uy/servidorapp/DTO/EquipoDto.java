package codigocreativo.uy.servidorapp.DTO;

import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Equipo}
 */
public class EquipoDto implements Serializable {
    private Long id;
    private String idInterno;
    private String nroSerie;
    private TiposEquipoDto idTipo;
    private ProveedoresEquipoDto idProveedor;
    private PaisDto idPais;
    private ModelosEquipoDto idModelo;
    private Set<EquiposUbicacioneDto> equiposUbicaciones = new LinkedHashSet<>();
    private UbicacionDto idUbicacion;
    private String nombre;
    private String imagen;
    private LocalDate fechaAdquisicion;
    private Estados estado;

    public EquipoDto() {
    }

    public EquipoDto(Long id, String idInterno, String nroSerie,
                     TiposEquipoDto idTipo,
                     ProveedoresEquipoDto idProveedor,
                     PaisDto idPais,
                     ModelosEquipoDto idModelo,
                     Set<EquiposUbicacioneDto> equiposUbicaciones,
                     UbicacionDto idUbicacion, String nombre, String imagen, LocalDate fechaAdquisicion, Estados estado) {
        this.id = id;
        this.idInterno = idInterno;
        this.nroSerie = nroSerie;
        this.nombre = nombre;
        this.imagen = imagen;
        this.fechaAdquisicion = fechaAdquisicion;
        this.estado = estado;
        this.idTipo = idTipo;
        this.idProveedor = idProveedor;
        this.idPais = idPais;
        this.idModelo = idModelo;
        this.equiposUbicaciones = equiposUbicaciones;
        this.idUbicacion = idUbicacion;
    }

    public Long getId() {
        return id;
    }

    public EquipoDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getIdInterno() {
        return idInterno;
    }

    public EquipoDto setIdInterno(String idInterno) {
        this.idInterno = idInterno;
        return this;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public EquipoDto setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public EquipoDto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getImagen() {
        return imagen;
    }

    public EquipoDto setImagen(String imagen) {
        this.imagen = imagen;
        return this;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public EquipoDto setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
        return this;
    }

    public Estados getEstado() {
        return estado;
    }

    public EquipoDto setEstado(Estados estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipoDto entity = (EquipoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.idInterno, entity.idInterno) &&
                Objects.equals(this.nroSerie, entity.nroSerie) &&
                Objects.equals(this.nombre, entity.nombre) &&
                Objects.equals(this.imagen, entity.imagen) &&
                Objects.equals(this.fechaAdquisicion, entity.fechaAdquisicion) &&
                Objects.equals(this.estado, entity.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idInterno, nroSerie, nombre, imagen, fechaAdquisicion, estado);
    }

    @Override
    public String toString() {
        return idInterno + " - " + nombre;
    }

    public TiposEquipoDto getIdTipo() {
        return idTipo;
    }

    public EquipoDto setIdTipo(TiposEquipoDto idTipo) {
        this.idTipo = idTipo;
        return this;
    }

    public ProveedoresEquipoDto getIdProveedor() {
        return idProveedor;
    }

    public EquipoDto setIdProveedor(ProveedoresEquipoDto idProveedor) {
        this.idProveedor = idProveedor;
        return this;
    }

    public PaisDto getIdPais() {
        return idPais;
    }

    public EquipoDto setIdPais(PaisDto idPais) {
        this.idPais = idPais;
        return this;
    }

    public ModelosEquipoDto getIdModelo() {
        return idModelo;
    }

    public EquipoDto setIdModelo(ModelosEquipoDto idModelo) {
        this.idModelo = idModelo;
        return this;
    }

    public Set<EquiposUbicacioneDto> getEquiposUbicaciones() {
        return equiposUbicaciones;
    }

    public EquipoDto setEquiposUbicaciones(Set<EquiposUbicacioneDto> equiposUbicaciones) {
        this.equiposUbicaciones = equiposUbicaciones;
        return this;
    }

    public UbicacionDto getIdUbicacion() {
        return idUbicacion;
    }

    public EquipoDto setIdUbicacion(UbicacionDto idUbicacion) {
        this.idUbicacion = idUbicacion;
        return this;
    }
}