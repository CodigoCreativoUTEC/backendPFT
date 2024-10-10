package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "FUNCIONALIDADES")
public class Funcionalidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FUNCIONALIDAD", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "NOMBRE_FUNCIONALIDAD")
    private String nombreFuncionalidad;

    @Size(max = 255)
    @Column(name = "ESTADO")
    private String estado;

    @ManyToMany
    @JoinTable(name = "FUNCIONALIDADES_PERFILES",
            joinColumns = @JoinColumn(name = "ID_FUNCIONALIDAD"),
            inverseJoinColumns = @JoinColumn(name = "ID_PERFIL"))
    private Set<Perfil> perfiles = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreFuncionalidad() {
        return nombreFuncionalidad;
    }

    public void setNombreFuncionalidad(String nombreFuncionalidad) {
        this.nombreFuncionalidad = nombreFuncionalidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<Perfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(Set<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

}