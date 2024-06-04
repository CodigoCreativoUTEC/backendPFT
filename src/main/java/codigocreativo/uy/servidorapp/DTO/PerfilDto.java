package codigocreativo.uy.servidorapp.DTO;

import codigocreativo.uy.servidorapp.enumerados.Estados;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.Perfil}
 */
public class PerfilDto implements Serializable {
    private List<PermisoDto> permisos = new LinkedList<>();
    private Long id;
    private String nombrePerfil;
    private Estados estado;

    public PerfilDto() {
    }

    public PerfilDto(
            List<PermisoDto> permisos, Long id, String nombrePerfil, Estados estado) {
        this.id = id;
        this.nombrePerfil = nombrePerfil;
        this.estado = estado;
        this.permisos = permisos;
    }

    public Long getId() {
        return id;
    }

    public PerfilDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public PerfilDto setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
        return this;
    }

    public Estados getEstado() {
        return estado;
    }

    public PerfilDto setEstado(Estados estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilDto entity = (PerfilDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombrePerfil, entity.nombrePerfil) &&
                Objects.equals(this.estado, entity.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombrePerfil, estado);
    }

    @Override
    public String toString() {
        return nombrePerfil;
    }

    public List<PermisoDto> getPermisos() {
        return permisos;
    }

    public PerfilDto setPermisos(List<PermisoDto> permisos) {
        this.permisos = permisos;
        return this;
    }
}