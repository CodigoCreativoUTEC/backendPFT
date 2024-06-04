package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "TIPOS_EQUIPOS")
public class TiposEquipo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO", nullable = false)
    private Long id;

    @Column(name = "NOMBRE_TIPO", nullable = false, length = 30)
    private String nombreTipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    @Override
    public String toString() {
        return nombreTipo;
    }
}