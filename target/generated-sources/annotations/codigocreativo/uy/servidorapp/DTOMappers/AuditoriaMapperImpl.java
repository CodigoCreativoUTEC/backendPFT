package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.AuditoriaDto;
import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.DTO.OperacionDto;
import codigocreativo.uy.servidorapp.DTO.PerfilDto;
import codigocreativo.uy.servidorapp.DTO.PermisoDto;
import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.DTO.UsuariosTelefonoDto;
import codigocreativo.uy.servidorapp.entidades.Auditoria;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Operacion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Permiso;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.entidades.UsuariosTelefono;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-05T11:17:58-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@ApplicationScoped
public class AuditoriaMapperImpl implements AuditoriaMapper {

    @Override
    public Auditoria toEntity(AuditoriaDto auditoriaDto) {
        if ( auditoriaDto == null ) {
            return null;
        }

        Auditoria auditoria = new Auditoria();

        auditoria.setId( auditoriaDto.getId() );
        auditoria.setFechaHora( auditoriaDto.getFechaHora() );
        auditoria.setIdUsuario( usuarioDtoToUsuario( auditoriaDto.getIdUsuario() ) );
        auditoria.setIdOperacion( operacionDtoToOperacion( auditoriaDto.getIdOperacion() ) );

        return auditoria;
    }

    @Override
    public AuditoriaDto toDto(Auditoria auditoria) {
        if ( auditoria == null ) {
            return null;
        }

        AuditoriaDto auditoriaDto = new AuditoriaDto();

        auditoriaDto.setId( auditoria.getId() );
        auditoriaDto.setFechaHora( auditoria.getFechaHora() );
        auditoriaDto.setIdUsuario( usuarioToUsuarioDto( auditoria.getIdUsuario() ) );
        auditoriaDto.setIdOperacion( operacionToOperacionDto( auditoria.getIdOperacion() ) );

        return auditoriaDto;
    }

    @Override
    public Auditoria partialUpdate(AuditoriaDto auditoriaDto, Auditoria auditoria) {
        if ( auditoriaDto == null ) {
            return auditoria;
        }

        if ( auditoriaDto.getId() != null ) {
            auditoria.setId( auditoriaDto.getId() );
        }
        if ( auditoriaDto.getFechaHora() != null ) {
            auditoria.setFechaHora( auditoriaDto.getFechaHora() );
        }
        if ( auditoriaDto.getIdUsuario() != null ) {
            if ( auditoria.getIdUsuario() == null ) {
                auditoria.setIdUsuario( new Usuario() );
            }
            usuarioDtoToUsuario1( auditoriaDto.getIdUsuario(), auditoria.getIdUsuario() );
        }
        if ( auditoriaDto.getIdOperacion() != null ) {
            if ( auditoria.getIdOperacion() == null ) {
                auditoria.setIdOperacion( new Operacion() );
            }
            operacionDtoToOperacion1( auditoriaDto.getIdOperacion(), auditoria.getIdOperacion() );
        }

        return auditoria;
    }

    @Override
    public List<Auditoria> toEntity(List<AuditoriaDto> auditoriaDto) {
        if ( auditoriaDto == null ) {
            return null;
        }

        List<Auditoria> list = new ArrayList<Auditoria>( auditoriaDto.size() );
        for ( AuditoriaDto auditoriaDto1 : auditoriaDto ) {
            list.add( toEntity( auditoriaDto1 ) );
        }

        return list;
    }

    @Override
    public List<AuditoriaDto> toDto(List<Auditoria> auditoria) {
        if ( auditoria == null ) {
            return null;
        }

        List<AuditoriaDto> list = new ArrayList<AuditoriaDto>( auditoria.size() );
        for ( Auditoria auditoria1 : auditoria ) {
            list.add( toDto( auditoria1 ) );
        }

        return list;
    }

    protected UsuariosTelefono usuariosTelefonoDtoToUsuariosTelefono(UsuariosTelefonoDto usuariosTelefonoDto) {
        if ( usuariosTelefonoDto == null ) {
            return null;
        }

        UsuariosTelefono usuariosTelefono = new UsuariosTelefono();

        usuariosTelefono.setNumero( usuariosTelefonoDto.getNumero() );
        usuariosTelefono.setId( usuariosTelefonoDto.getId() );
        usuariosTelefono.setIdUsuario( usuarioDtoToUsuario( usuariosTelefonoDto.getIdUsuario() ) );

        return usuariosTelefono;
    }

    protected Set<UsuariosTelefono> usuariosTelefonoDtoSetToUsuariosTelefonoSet(Set<UsuariosTelefonoDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<UsuariosTelefono> set1 = new LinkedHashSet<UsuariosTelefono>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( UsuariosTelefonoDto usuariosTelefonoDto : set ) {
            set1.add( usuariosTelefonoDtoToUsuariosTelefono( usuariosTelefonoDto ) );
        }

        return set1;
    }

    protected Permiso permisoDtoToPermiso(PermisoDto permisoDto) {
        if ( permisoDto == null ) {
            return null;
        }

        Permiso permiso = new Permiso();

        permiso.setId( permisoDto.getId() );
        permiso.setTipoPermiso( permisoDto.getTipoPermiso() );

        return permiso;
    }

    protected List<Permiso> permisoDtoListToPermisoList(List<PermisoDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Permiso> list1 = new ArrayList<Permiso>( list.size() );
        for ( PermisoDto permisoDto : list ) {
            list1.add( permisoDtoToPermiso( permisoDto ) );
        }

        return list1;
    }

    protected Perfil perfilDtoToPerfil(PerfilDto perfilDto) {
        if ( perfilDto == null ) {
            return null;
        }

        Perfil perfil = new Perfil();

        perfil.setPermisos( permisoDtoListToPermisoList( perfilDto.getPermisos() ) );
        perfil.setId( perfilDto.getId() );
        perfil.setNombrePerfil( perfilDto.getNombrePerfil() );
        perfil.setEstado( perfilDto.getEstado() );

        return perfil;
    }

    protected Institucion institucionDtoToInstitucion(InstitucionDto institucionDto) {
        if ( institucionDto == null ) {
            return null;
        }

        Institucion institucion = new Institucion();

        institucion.setId( institucionDto.getId() );
        institucion.setNombre( institucionDto.getNombre() );

        return institucion;
    }

    protected Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto) {
        if ( usuarioDto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setUsuariosTelefonos( usuariosTelefonoDtoSetToUsuariosTelefonoSet( usuarioDto.getUsuariosTelefonos() ) );
        usuario.setNombreUsuario( usuarioDto.getNombreUsuario() );
        usuario.setId( usuarioDto.getId() );
        usuario.setCedula( usuarioDto.getCedula() );
        usuario.setIdPerfil( perfilDtoToPerfil( usuarioDto.getIdPerfil() ) );
        usuario.setIdInstitucion( institucionDtoToInstitucion( usuarioDto.getIdInstitucion() ) );
        usuario.setEmail( usuarioDto.getEmail() );
        usuario.setContrasenia( usuarioDto.getContrasenia() );
        usuario.setFechaNacimiento( usuarioDto.getFechaNacimiento() );
        usuario.setEstado( usuarioDto.getEstado() );
        usuario.setNombre( usuarioDto.getNombre() );
        usuario.setApellido( usuarioDto.getApellido() );

        return usuario;
    }

    protected Operacion operacionDtoToOperacion(OperacionDto operacionDto) {
        if ( operacionDto == null ) {
            return null;
        }

        Operacion operacion = new Operacion();

        operacion.setId( operacionDto.getId() );
        operacion.setNombreOperacion( operacionDto.getNombreOperacion() );

        return operacion;
    }

    protected InstitucionDto institucionToInstitucionDto(Institucion institucion) {
        if ( institucion == null ) {
            return null;
        }

        InstitucionDto institucionDto = new InstitucionDto();

        institucionDto.setId( institucion.getId() );
        institucionDto.setNombre( institucion.getNombre() );

        return institucionDto;
    }

    protected PermisoDto permisoToPermisoDto(Permiso permiso) {
        if ( permiso == null ) {
            return null;
        }

        PermisoDto permisoDto = new PermisoDto();

        permisoDto.setId( permiso.getId() );
        permisoDto.setTipoPermiso( permiso.getTipoPermiso() );

        return permisoDto;
    }

    protected List<PermisoDto> permisoListToPermisoDtoList(List<Permiso> list) {
        if ( list == null ) {
            return null;
        }

        List<PermisoDto> list1 = new ArrayList<PermisoDto>( list.size() );
        for ( Permiso permiso : list ) {
            list1.add( permisoToPermisoDto( permiso ) );
        }

        return list1;
    }

    protected PerfilDto perfilToPerfilDto(Perfil perfil) {
        if ( perfil == null ) {
            return null;
        }

        PerfilDto perfilDto = new PerfilDto();

        perfilDto.setId( perfil.getId() );
        perfilDto.setNombrePerfil( perfil.getNombrePerfil() );
        perfilDto.setEstado( perfil.getEstado() );
        perfilDto.setPermisos( permisoListToPermisoDtoList( perfil.getPermisos() ) );

        return perfilDto;
    }

    protected UsuariosTelefonoDto usuariosTelefonoToUsuariosTelefonoDto(UsuariosTelefono usuariosTelefono) {
        if ( usuariosTelefono == null ) {
            return null;
        }

        UsuariosTelefonoDto usuariosTelefonoDto = new UsuariosTelefonoDto();

        usuariosTelefonoDto.setId( usuariosTelefono.getId() );
        usuariosTelefonoDto.setNumero( usuariosTelefono.getNumero() );
        usuariosTelefonoDto.setIdUsuario( usuarioToUsuarioDto( usuariosTelefono.getIdUsuario() ) );

        return usuariosTelefonoDto;
    }

    protected Set<UsuariosTelefonoDto> usuariosTelefonoSetToUsuariosTelefonoDtoSet(Set<UsuariosTelefono> set) {
        if ( set == null ) {
            return null;
        }

        Set<UsuariosTelefonoDto> set1 = new LinkedHashSet<UsuariosTelefonoDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( UsuariosTelefono usuariosTelefono : set ) {
            set1.add( usuariosTelefonoToUsuariosTelefonoDto( usuariosTelefono ) );
        }

        return set1;
    }

    protected UsuarioDto usuarioToUsuarioDto(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setId( usuario.getId() );
        usuarioDto.setCedula( usuario.getCedula() );
        usuarioDto.setEmail( usuario.getEmail() );
        usuarioDto.setContrasenia( usuario.getContrasenia() );
        usuarioDto.setFechaNacimiento( usuario.getFechaNacimiento() );
        usuarioDto.setEstado( usuario.getEstado() );
        usuarioDto.setNombre( usuario.getNombre() );
        usuarioDto.setApellido( usuario.getApellido() );
        usuarioDto.setNombreUsuario( usuario.getNombreUsuario() );
        usuarioDto.setIdInstitucion( institucionToInstitucionDto( usuario.getIdInstitucion() ) );
        usuarioDto.setIdPerfil( perfilToPerfilDto( usuario.getIdPerfil() ) );
        usuarioDto.setUsuariosTelefonos( usuariosTelefonoSetToUsuariosTelefonoDtoSet( usuario.getUsuariosTelefonos() ) );

        return usuarioDto;
    }

    protected OperacionDto operacionToOperacionDto(Operacion operacion) {
        if ( operacion == null ) {
            return null;
        }

        OperacionDto operacionDto = new OperacionDto();

        operacionDto.setId( operacion.getId() );
        operacionDto.setNombreOperacion( operacion.getNombreOperacion() );

        return operacionDto;
    }

    protected void perfilDtoToPerfil1(PerfilDto perfilDto, Perfil mappingTarget) {
        if ( perfilDto == null ) {
            return;
        }

        if ( mappingTarget.getPermisos() != null ) {
            List<Permiso> list = permisoDtoListToPermisoList( perfilDto.getPermisos() );
            if ( list != null ) {
                mappingTarget.getPermisos().clear();
                mappingTarget.getPermisos().addAll( list );
            }
        }
        else {
            List<Permiso> list = permisoDtoListToPermisoList( perfilDto.getPermisos() );
            if ( list != null ) {
                mappingTarget.setPermisos( list );
            }
        }
        if ( perfilDto.getId() != null ) {
            mappingTarget.setId( perfilDto.getId() );
        }
        if ( perfilDto.getNombrePerfil() != null ) {
            mappingTarget.setNombrePerfil( perfilDto.getNombrePerfil() );
        }
        if ( perfilDto.getEstado() != null ) {
            mappingTarget.setEstado( perfilDto.getEstado() );
        }
    }

    protected void institucionDtoToInstitucion1(InstitucionDto institucionDto, Institucion mappingTarget) {
        if ( institucionDto == null ) {
            return;
        }

        if ( institucionDto.getId() != null ) {
            mappingTarget.setId( institucionDto.getId() );
        }
        if ( institucionDto.getNombre() != null ) {
            mappingTarget.setNombre( institucionDto.getNombre() );
        }
    }

    protected void usuarioDtoToUsuario1(UsuarioDto usuarioDto, Usuario mappingTarget) {
        if ( usuarioDto == null ) {
            return;
        }

        if ( mappingTarget.getUsuariosTelefonos() != null ) {
            Set<UsuariosTelefono> set = usuariosTelefonoDtoSetToUsuariosTelefonoSet( usuarioDto.getUsuariosTelefonos() );
            if ( set != null ) {
                mappingTarget.getUsuariosTelefonos().clear();
                mappingTarget.getUsuariosTelefonos().addAll( set );
            }
        }
        else {
            Set<UsuariosTelefono> set = usuariosTelefonoDtoSetToUsuariosTelefonoSet( usuarioDto.getUsuariosTelefonos() );
            if ( set != null ) {
                mappingTarget.setUsuariosTelefonos( set );
            }
        }
        if ( usuarioDto.getNombreUsuario() != null ) {
            mappingTarget.setNombreUsuario( usuarioDto.getNombreUsuario() );
        }
        if ( usuarioDto.getId() != null ) {
            mappingTarget.setId( usuarioDto.getId() );
        }
        if ( usuarioDto.getCedula() != null ) {
            mappingTarget.setCedula( usuarioDto.getCedula() );
        }
        if ( usuarioDto.getIdPerfil() != null ) {
            if ( mappingTarget.getIdPerfil() == null ) {
                mappingTarget.setIdPerfil( new Perfil() );
            }
            perfilDtoToPerfil1( usuarioDto.getIdPerfil(), mappingTarget.getIdPerfil() );
        }
        if ( usuarioDto.getIdInstitucion() != null ) {
            if ( mappingTarget.getIdInstitucion() == null ) {
                mappingTarget.setIdInstitucion( new Institucion() );
            }
            institucionDtoToInstitucion1( usuarioDto.getIdInstitucion(), mappingTarget.getIdInstitucion() );
        }
        if ( usuarioDto.getEmail() != null ) {
            mappingTarget.setEmail( usuarioDto.getEmail() );
        }
        if ( usuarioDto.getContrasenia() != null ) {
            mappingTarget.setContrasenia( usuarioDto.getContrasenia() );
        }
        if ( usuarioDto.getFechaNacimiento() != null ) {
            mappingTarget.setFechaNacimiento( usuarioDto.getFechaNacimiento() );
        }
        if ( usuarioDto.getEstado() != null ) {
            mappingTarget.setEstado( usuarioDto.getEstado() );
        }
        if ( usuarioDto.getNombre() != null ) {
            mappingTarget.setNombre( usuarioDto.getNombre() );
        }
        if ( usuarioDto.getApellido() != null ) {
            mappingTarget.setApellido( usuarioDto.getApellido() );
        }
    }

    protected void operacionDtoToOperacion1(OperacionDto operacionDto, Operacion mappingTarget) {
        if ( operacionDto == null ) {
            return;
        }

        if ( operacionDto.getId() != null ) {
            mappingTarget.setId( operacionDto.getId() );
        }
        if ( operacionDto.getNombreOperacion() != null ) {
            mappingTarget.setNombreOperacion( operacionDto.getNombreOperacion() );
        }
    }
}
