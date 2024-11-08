package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.UsuarioDto;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtomappers.UsuarioMapper;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Map;

@Stateless
public class UsuarioBean implements UsuarioRemote {
    @PersistenceContext (unitName = "default")
    private EntityManager em;

    @Inject
    UsuarioMapper usuarioMapper;

    private static final String EMAIL = "email";
    private static final String ESTADO = "estado";

    @Override
    public void crearUsuario(UsuarioDto u) {
        u.setEstado(Estados.SIN_VALIDAR);
        em.merge(usuarioMapper.toEntity(u, new CycleAvoidingMappingContext()));
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
    public List<UsuarioDto> obtenerUsuariosFiltrados(String filtro, Object valor) {
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u." + filtro + " = :valor", Usuario.class)
                .setParameter("valor", valor)
                .getResultList(), new CycleAvoidingMappingContext());
    }

    @Override
    public List<UsuarioDto> obtenerUsuariosPorEstado(Estados estado) {
        return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u.estado = :estado", Usuario.class)
                .setParameter(ESTADO, estado)
                .getResultList(), new CycleAvoidingMappingContext());
    }

    @Override
    public UsuarioDto login(String usuario, String password) {
        try {
            return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u.email = :usuario AND u.contrasenia = :password", Usuario.class)
                    .setParameter("usuario", usuario)
                    .setParameter("password", password)
                    .getSingleResult(), new CycleAvoidingMappingContext());
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public UsuarioDto findUserByEmail(String email) {
        try {
            return usuarioMapper.toDto(em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter(EMAIL, email)
                    .getSingleResult(), new CycleAvoidingMappingContext());
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean hasPermission(String email, String requiredRole) {
        try {
            UsuarioDto usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", UsuarioDto.class)
                    .setParameter(EMAIL, email)
                    .getSingleResult();
            return usuario.getIdPerfil().getNombrePerfil().equals(requiredRole);
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
public List<UsuarioDto> obtenerUsuariosFiltrado(Map<String, String> filtros) {
    StringBuilder queryStr = new StringBuilder("SELECT u FROM Usuario u WHERE 1=1");

    // Añadir condiciones de filtrado
    if (filtros.containsKey("nombre")) {
        queryStr.append(" AND LOWER(u.nombre) LIKE LOWER(:nombre)");
    }
    if (filtros.containsKey("apellido")) {
        queryStr.append(" AND LOWER(u.apellido) LIKE LOWER(:apellido)");
    }
    if (filtros.containsKey("nombreUsuario")) {
        queryStr.append(" AND LOWER(u.nombreUsuario) LIKE LOWER(:nombreUsuario)");
    }
    if (filtros.containsKey(EMAIL)) {
        queryStr.append(" AND LOWER(u.email) LIKE LOWER(:email)");
    }
    if (filtros.containsKey("tipoUsuario")) {
        queryStr.append(" AND LOWER(u.idPerfil.nombrePerfil) LIKE LOWER(:tipoUsuario)");
    }
    if (filtros.containsKey(ESTADO)) {
        queryStr.append(" AND u.estado = :estado");
    }

    var query = em.createQuery(queryStr.toString(), Usuario.class);

    // Establecer parámetros de la consulta
    filtros.forEach((key, value) -> {
        if (!value.isEmpty()) {
            if (key.equals(ESTADO)) {
                query.setParameter(key, Estados.valueOf(value));  // Si es el estado, usamos el enum
            } else {
                query.setParameter(key, "%" + value + "%");
            }
        }
    });
    return usuarioMapper.toDto(query.getResultList(), new CycleAvoidingMappingContext());
}

}
