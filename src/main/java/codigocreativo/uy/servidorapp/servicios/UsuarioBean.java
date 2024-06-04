package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.DTOMappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.DTOMappers.UsuarioMapper;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.List;
@Stateless
public class UsuarioBean implements UsuarioRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;

    @Inject
    UsuarioMapper usuarioMapper;

    @Override
    public void crearUsuario(UsuarioDto u) {
        em.persist(usuarioMapper.toEntity(u, new CycleAvoidingMappingContext()));
    }

    @Override
    public void modificarUsuario(UsuarioDto u) {
        em.merge(usuarioMapper.toEntity(u, new CycleAvoidingMappingContext()));
        em.flush();
    }

    @Override
    public void eliminarUsuario(UsuarioDto u) {
        em.createQuery("UPDATE Usuario u SET u.estado = 'INACTIVO' WHERE u.id = :id")
                .setParameter("id", u.getId())
                .executeUpdate();
        em.flush();
    }

    @Override
    public UsuarioDto obtenerUsuario(Long id) {
        return usuarioMapper.toDto(em.find(Usuario.class, id), new CycleAvoidingMappingContext());
    }

    @Override
    public UsuarioDto obtenerUsuarioDto(Long id) {
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u.id = :id", Usuario.class)
                .setParameter("id", id)
                .getSingleResult(), new CycleAvoidingMappingContext());
    }

    @Override
    public UsuarioDto obtenerUsuarioPorCI(String ci) {
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u.cedula = :ci", Usuario.class)
                .setParameter("ci", ci)
                .getSingleResult(), new CycleAvoidingMappingContext());
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList(), new CycleAvoidingMappingContext());
    }

    @Override
    public List<UsuarioDto> obtenerUsuariosFiltrado(String filtro, Object valor) {
        System.out.println("SELECT u FROM Usuario u WHERE u." + filtro + " = :valor");
        System.out.println("valor: " + valor);
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u." + filtro + " = :valor", Usuario.class)
                .setParameter("valor", valor)
                .getResultList(), new CycleAvoidingMappingContext());
    }

    @Override
    public List<UsuarioDto> obtenerUsuariosPorEstado(Estados estado) {
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u.estado = :estado", Usuario.class)
                .setParameter("estado", estado)
                .getResultList(), new CycleAvoidingMappingContext());
        }

    @Override
    public UsuarioDto login(String usuario, String password) {
        try {
            return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u.nombreUsuario = :usuario AND u.contrasenia = :password", Usuario.class)
                    .setParameter("usuario", usuario)
                    .setParameter("password", password)
                    .getSingleResult(), new CycleAvoidingMappingContext());
        } catch (NoResultException e) {
            return null;
        }
    }
}
