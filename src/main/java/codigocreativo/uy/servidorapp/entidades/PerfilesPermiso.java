package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Table(name = "PERFILES_PERMISOS")
public class PerfilesPermiso implements Serializable {
    private static final long serialVersionUID = -1586501627504876783L;


    @EmbeddedId
    private PerfilesPermisoId id;

    @MapsId("idPerfil")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PERFIL", nullable = false)
    private Perfil idPerfil;

    @MapsId("idPermiso")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PERMISO", nullable = false)
    private Permiso idPermiso;

    public PerfilesPermisoId getId() {
        return id;
    }

    public void setId(PerfilesPermisoId id) {
        this.id = id;
    }

    public Perfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Perfil idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Permiso getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Permiso idPermiso) {
        this.idPermiso = idPermiso;
    }

}