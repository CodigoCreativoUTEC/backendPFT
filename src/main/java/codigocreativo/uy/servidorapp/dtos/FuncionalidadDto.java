package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import java.io.Serializable;
import java.util.List;

public class FuncionalidadDto implements Serializable {
    private Long id;
    private String nombreFuncionalidad;
    private Estados estado;  // Usamos el enum directamente
    private List<PerfilDto> perfiles;

    // Getters y Setters
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

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public List<PerfilDto> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<PerfilDto> perfiles) {
        this.perfiles = perfiles;
    }
}
