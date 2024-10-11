package codigocreativo.uy.servidorapp.entidades;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PERFILES")
public class Perfil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERFIL", nullable = false)
    private Long id;

    @Column(name = "NOMBRE_PERFIL", nullable = false, length = 20)
    private String nombrePerfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false, length = 20)
    private Estados estado;

    @ManyToMany(mappedBy = "perfiles")
    private Set<Funcionalidad> funcionalidades = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "PERFILES_PERMISOS",
            joinColumns = @JoinColumn(name = "ID_PERFIL"),
            inverseJoinColumns = @JoinColumn(name = "ID_PERMISO"))
    private Set<Permiso> permisos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPerfil")
    private Set<Usuario> usuarios = new LinkedHashSet<>();

    public Set<Usuario> getUsuarios() {
        if (usuarios == null) {
            usuarios = new LinkedHashSet<>();
        }
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Set<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Set<Funcionalidad> getFuncionalidades() {
        return funcionalidades;
    }

    public void setFuncionalidades(Set<Funcionalidad> funcionalidades) {
        this.funcionalidades = funcionalidades;
    }


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }


}
