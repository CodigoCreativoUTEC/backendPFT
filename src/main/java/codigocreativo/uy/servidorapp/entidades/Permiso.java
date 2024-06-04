package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "PERMISOS")
public class Permiso implements Serializable {
    private static final long serialVersionUID = -1586501627504876783L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERMISO", nullable = false)
    private Long id;

    @Column(name = "TIPO_PERMISO", nullable = false, length = 20)
    private String tipoPermiso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

}