package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.entidades.Funcionalidad;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link Funcionalidad}
 */
public class FuncionalidadDto implements Serializable {
    private final Long id;
    @Size(max = 255)
    private final String nombreFuncionalidad;
    @Size(max = 255)
    private final String estado;
    private final Set<PerfilDto> perfiles;

    public FuncionalidadDto(Long id, String nombreFuncionalidad, String estado, Set<PerfilDto> perfiles) {
        this.id = id;
        this.nombreFuncionalidad = nombreFuncionalidad;
        this.estado = estado;
        this.perfiles = perfiles;
    }

    public Long getId() {
        return id;
    }

    public String getNombreFuncionalidad() {
        return nombreFuncionalidad;
    }

    public String getEstado() {
        return estado;
    }

    public Set<PerfilDto> getPerfiles() {
        return perfiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuncionalidadDto entity = (FuncionalidadDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombreFuncionalidad, entity.nombreFuncionalidad) &&
                Objects.equals(this.estado, entity.estado) &&
                Objects.equals(this.perfiles, entity.perfiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreFuncionalidad, estado, perfiles);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombreFuncionalidad = " + nombreFuncionalidad + ", " +
                "estado = " + estado + ", " +
                "perfiles = " + perfiles + ")";
    }
}