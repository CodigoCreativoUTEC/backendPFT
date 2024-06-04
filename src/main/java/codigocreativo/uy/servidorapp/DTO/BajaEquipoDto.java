package codigocreativo.uy.servidorapp.DTO;

import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for {@link BajaEquipo}
 */
public class BajaEquipoDto implements Serializable {
    private Long id;
    private String razon;
    private LocalDate fecha;
    private UsuarioDto idUsuario;
    private EquipoDto idEquipo;
    private Estados estado;
    private String comentarios;

    public BajaEquipoDto() {
    }

    public BajaEquipoDto(Long id, String razon, LocalDate fecha,
                         UsuarioDto idUsuario,
                         EquipoDto idEquipo, Estados estado, String comentarios) {
        this.id = id;
        this.razon = razon;
        this.fecha = fecha;
        this.estado = estado;
        this.comentarios = comentarios;
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
    }

    public Long getId() {
        return id;
    }

    public BajaEquipoDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRazon() {
        return razon;
    }

    public BajaEquipoDto setRazon(String razon) {
        this.razon = razon;
        return this;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public BajaEquipoDto setFecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public Estados getEstado() {
        return estado;
    }

    public BajaEquipoDto setEstado(Estados estado) {
        this.estado = estado;
        return this;
    }

    public String getComentarios() {
        return comentarios;
    }

    public BajaEquipoDto setComentarios(String comentarios) {
        this.comentarios = comentarios;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BajaEquipoDto entity = (BajaEquipoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.razon, entity.razon) &&
                Objects.equals(this.fecha, entity.fecha) &&
                Objects.equals(this.estado, entity.estado) &&
                Objects.equals(this.comentarios, entity.comentarios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, razon, fecha, estado, comentarios);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "razon = " + razon + ", " +
                "fecha = " + fecha + ", " +
                "estado = " + estado + ", " +
                "comentarios = " + comentarios + ")";
    }

    public UsuarioDto getIdUsuario() {
        return idUsuario;
    }

    public BajaEquipoDto setIdUsuario(UsuarioDto idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public EquipoDto getIdEquipo() {
        return idEquipo;
    }

    public BajaEquipoDto setIdEquipo(EquipoDto idEquipo) {
        this.idEquipo = idEquipo;
        return this;
    }
}