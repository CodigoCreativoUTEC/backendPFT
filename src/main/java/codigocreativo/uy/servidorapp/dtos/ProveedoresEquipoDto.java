package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.ProveedoresEquipo}
 */
public class ProveedoresEquipoDto implements Serializable {
    private Long id;
    private String nombre;
    private Estados estado;

    public ProveedoresEquipoDto() {
    }

    public ProveedoresEquipoDto(Long id, String nombre, Estados estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public ProveedoresEquipoDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public ProveedoresEquipoDto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Estados getEstado() {
        return estado;
    }

    public ProveedoresEquipoDto setEstado(Estados estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProveedoresEquipoDto entity = (ProveedoresEquipoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombre, entity.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, estado);
    }

    @Override
    public String toString() {
        return nombre;
    }
}