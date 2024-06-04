package codigocreativo.uy.servidorapp.entidades;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PERFILES")
public class Perfil implements Serializable {
    private static final long serialVersionUID = 4351899704654953514L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERFIL", nullable = false)
    private Long id;

    @Column(name = "NOMBRE_PERFIL", nullable = false, length = 20)
    private String nombrePerfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false, length = 20)
    private Estados estado;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PERFILES_PERMISOS",
            joinColumns = @JoinColumn(name = "ID_PERFIL"),
            inverseJoinColumns = @JoinColumn(name = "ID_PERMISO"))
    private List<Permiso> permisos = new LinkedList<>();


    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {

            this.permisos = permisos;
    }




    //permisos del perfil


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

    @Override
    public String toString() {
        return id + " - " + nombrePerfil;
    }
}