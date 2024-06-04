package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PerfilesPermisoId implements Serializable {
    private static final long serialVersionUID = -5698473148146927540L;
    @Column(name = "ID_PERFIL", nullable = false)
    private Long idPerfil;

    @Column(name = "ID_PERMISO", nullable = false)
    private Long idPermiso;

    public Long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PerfilesPermisoId entity = (PerfilesPermisoId) o;
        return Objects.equals(this.idPerfil, entity.idPerfil) &&
                Objects.equals(this.idPermiso, entity.idPermiso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerfil, idPermiso);
    }

}