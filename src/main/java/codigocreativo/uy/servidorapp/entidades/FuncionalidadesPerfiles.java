package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "FUNCIONALIDADES_PERFILES")
public class FuncionalidadesPerfiles {
    @EmbeddedId
    private FuncionalidadesPerfilesId id;

    @MapsId("idFuncionalidad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_FUNCIONALIDAD", nullable = false)
    private Funcionalidad idFuncionalidad;

    @MapsId("idPerfil")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PERFIL", nullable = false)
    private Perfil idPerfil;

    public FuncionalidadesPerfilesId getId() {
        return id;
    }

    public void setId(FuncionalidadesPerfilesId id) {
        this.id = id;
    }

    public Funcionalidad getIdFuncionalidad() {
        return idFuncionalidad;
    }

    public void setIdFuncionalidad(Funcionalidad idFuncionalidad) {
        this.idFuncionalidad = idFuncionalidad;
    }

    public Perfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Perfil idPerfil) {
        this.idPerfil = idPerfil;
    }

}