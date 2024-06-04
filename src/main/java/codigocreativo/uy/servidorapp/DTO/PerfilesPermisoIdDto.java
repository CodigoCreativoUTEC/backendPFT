package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.PerfilesPermisoId}
 */
public class PerfilesPermisoIdDto implements Serializable {
    private Long idPerfil;
    private Long idPermiso;

    public PerfilesPermisoIdDto() {
    }

    public PerfilesPermisoIdDto(Long idPerfil, Long idPermiso) {
        this.idPerfil = idPerfil;
        this.idPermiso = idPermiso;
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public PerfilesPermisoIdDto setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
        return this;
    }

    public Long getIdPermiso() {
        return idPermiso;
    }

    public PerfilesPermisoIdDto setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilesPermisoIdDto entity = (PerfilesPermisoIdDto) o;
        return Objects.equals(this.idPerfil, entity.idPerfil) &&
                Objects.equals(this.idPermiso, entity.idPermiso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerfil, idPermiso);
    }

    @Override
    public String toString() {
        return idPerfil.toString() + " " + idPermiso.toString();
    }
}