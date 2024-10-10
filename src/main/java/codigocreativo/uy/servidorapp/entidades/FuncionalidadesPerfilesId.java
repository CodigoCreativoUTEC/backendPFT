package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FuncionalidadesPerfilesId implements Serializable {
    private static final long serialVersionUID = 1532915017361734107L;
    @Column(name = "id_funcionalidad")
    private Long idFuncionalidad;

    @NotNull
    @Column(name = "ID_PERFIL", nullable = false)
    private Long idPerfil;

    public Long getIdFuncionalidad() {
        return idFuncionalidad;
    }

    public void setIdFuncionalidad(Long idFuncionalidad) {
        this.idFuncionalidad = idFuncionalidad;
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FuncionalidadesPerfilesId entity = (FuncionalidadesPerfilesId) o;
        return Objects.equals(this.idPerfil, entity.idPerfil) &&
                Objects.equals(this.idFuncionalidad, entity.idFuncionalidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPerfil, idFuncionalidad);
    }

}