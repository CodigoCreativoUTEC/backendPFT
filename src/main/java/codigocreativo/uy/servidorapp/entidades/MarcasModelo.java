package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "MARCAS_MODELO")
public class MarcasModelo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MARCA", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}