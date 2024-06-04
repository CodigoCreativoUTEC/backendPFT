package codigocreativo.uy.servidorapp.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link codigocreativo.uy.servidorapp.entidades.ModelosEquipo}
 */
public class ModelosEquipoDto implements Serializable {
    private Long id;
    private String nombre;
    private MarcasModeloDto idMarca;

    public ModelosEquipoDto() {
    }

    public ModelosEquipoDto(Long id, String nombre,
                            MarcasModeloDto idMarca) {
        this.id = id;
        this.nombre = nombre;
        this.idMarca = idMarca;
    }

    public Long getId() {
        return id;
    }

    public ModelosEquipoDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public ModelosEquipoDto setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelosEquipoDto entity = (ModelosEquipoDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombre, entity.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }

    public MarcasModeloDto getIdMarca() {
        return idMarca;
    }

    public ModelosEquipoDto setIdMarca(MarcasModeloDto idMarca) {
        this.idMarca = idMarca;
        return this;
    }
}