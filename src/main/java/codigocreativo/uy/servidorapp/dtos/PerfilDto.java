package codigocreativo.uy.servidorapp.dtos;

import codigocreativo.uy.servidorapp.enumerados.Estados;
import java.io.Serializable;

public class PerfilDto implements Serializable {
    private Long id;
    private String nombrePerfil;
    private Estados estado;  // Usamos el enum directamente

    public PerfilDto(Long id, String nombrePerfil, Estados estado) {
        this.id = id;
        this.nombrePerfil = nombrePerfil;
        this.estado = estado;
    }

    public PerfilDto() {

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
