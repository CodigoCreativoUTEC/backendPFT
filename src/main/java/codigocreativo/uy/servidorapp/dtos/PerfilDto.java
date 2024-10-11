package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.Perfil}
 */
    @JsonIgnoreProperties({"usuarios", "permisos", "funcionalidades"})
public class PerfilDto implements Serializable {
    private Long id;
    private String nombrePerfil;
    private Estados estado;
    private Set<FuncionalidadDto> funcionalidades;
    private Set<PermisoDto> permisos;
    private Set<UsuarioDto> usuarios;

    public PerfilDto(Long id, String nombrePerfil, Estados estado, Set<FuncionalidadDto> funcionalidades, Set<PermisoDto> permisos, Set<UsuarioDto> usuarios) {
        this.id = id;
        this.nombrePerfil = nombrePerfil;
        this.estado = estado;
        this.funcionalidades = funcionalidades;
        this.permisos = permisos;
        this.usuarios = usuarios;
    }
    public PerfilDto(){}

    public Long getId() {
        return id;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public Estados getEstado() {
        return estado;
    }

    public Set<FuncionalidadDto> getFuncionalidades() {
        return funcionalidades;
    }

    public Set<PermisoDto> getPermisos() {
        return permisos;
    }

    public Set<UsuarioDto> getUsuarios() {
        return usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilDto entity = (PerfilDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombrePerfil, entity.nombrePerfil) &&
                Objects.equals(this.estado, entity.estado) &&
                Objects.equals(this.funcionalidades, entity.funcionalidades) &&
                Objects.equals(this.permisos, entity.permisos) &&
                Objects.equals(this.usuarios, entity.usuarios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombrePerfil, estado, funcionalidades, permisos, usuarios);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombrePerfil = " + nombrePerfil + ", " +
                "estado = " + estado + ", " +
                "funcionalidades = " + funcionalidades + ", " +
                "permisos = " + permisos + ", " +
                "usuarios = " + usuarios + ")";
    }
}