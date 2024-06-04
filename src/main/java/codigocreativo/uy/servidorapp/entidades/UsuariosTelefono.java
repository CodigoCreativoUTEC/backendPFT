package codigocreativo.uy.servidorapp.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "USUARIOS_TELEFONOS", schema = "PROYECTO")
public class UsuariosTelefono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TELEFONO", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO")
    private Usuario idUsuario;

    @Column(name = "NUMERO", nullable = false, length = 20)
    private String numero;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    //TODO [JPA Buddy] generate columns from DB
}