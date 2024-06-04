package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.entidades.UsuariosTelefono;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface UsuariosTelefonoRemote {
    public void crearUsuariosTelefono(UsuariosTelefono usuariosTelefono);
    public void modificarUsuariosTelefono(UsuariosTelefono usuariosTelefono);
    public void obtenerUsuariosTelefono(Long id);
    public List<UsuariosTelefono> obtenerusuariosTelefono();
}
