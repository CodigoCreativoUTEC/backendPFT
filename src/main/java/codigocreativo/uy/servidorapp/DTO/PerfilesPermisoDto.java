package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.PerfilesPermiso}
 */
public class PerfilesPermisoDto implements Serializable {
    private PerfilDto idPerfil;

    public PerfilesPermisoDto() {
    }

    public PerfilesPermisoDto(PerfilDto idPerfil) {
        this.idPerfil = idPerfil;
    }

    public PerfilDto getIdPerfil() {
        return idPerfil;
    }

    public PerfilesPermisoDto setIdPerfil(PerfilDto idPerfil) {
        this.idPerfil = idPerfil;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilesPermisoDto entity = (PerfilesPermisoDto) o;
        return Objects.equals(this.idPerfil, entity.idPerfil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerfil);
    }

    @Override
    public String toString() {
        return idPerfil.toString();
    }
}