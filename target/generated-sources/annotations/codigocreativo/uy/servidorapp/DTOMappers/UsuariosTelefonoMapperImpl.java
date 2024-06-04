package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.DTO.PerfilDto;
import codigocreativo.uy.servidorapp.DTO.PermisoDto;
import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.DTO.UsuariosTelefonoDto;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Permiso;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import codigocreativo.uy.servidorapp.entidades.UsuariosTelefono;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T19:06:55-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@ApplicationScoped
public class UsuariosTelefonoMapperImpl implements UsuariosTelefonoMapper {

    @Inject
    private UsuarioMapper usuarioMapper;

    @Override
    public UsuariosTelefono toEntity(UsuariosTelefonoDto usuariosTelefonoDto) {
        if ( usuariosTelefonoDto == null ) {
            return null;
        }

        UsuariosTelefono usuariosTelefono = new UsuariosTelefono();

        usuariosTelefono.setNumero( usuariosTelefonoDto.getNumero() );
        usuariosTelefono.setId( usuariosTelefonoDto.getId() );
        usuariosTelefono.setIdUsuario( usuarioDtoToUsuario( usuariosTelefonoDto.getIdUsuario() ) );

        return usuariosTelefono;
    }

    @Override
    public UsuariosTelefonoDto toDto(UsuariosTelefono usuariosTelefono) {
        if ( usuariosTelefono == null ) {
            return null;
        }

        UsuariosTelefonoDto usuariosTelefonoDto = new UsuariosTelefonoDto();

        usuariosTelefonoDto.setId( usuariosTelefono.getId() );
        usuariosTelefonoDto.setNumero( usuariosTelefono.getNumero() );
        usuariosTelefonoDto.setIdUsuario( usuarioToUsuarioDto( usuariosTelefono.getIdUsuario() ) );

        return usuariosTelefonoDto;
    }

    @Override
    public UsuariosTelefono partialUpdate(UsuariosTelefonoDto usuariosTelefonoDto, UsuariosTelefono usuariosTelefono) {
        if ( usuariosTelefonoDto == null ) {
            return usuariosTelefono;
        }

        if ( usuariosTelefonoDto.getNumero() != null ) {
            usuariosTelefono.setNumero( usuariosTelefonoDto.getNumero() );
        }
        if ( usuariosTelefonoDto.getId() != null ) {
            usuariosTelefono.setId( usuariosTelefonoDto.getId() );
        }
        if ( usuariosTelefonoDto.getIdUsuario() != null ) {
            if ( usuariosTelefono.getIdUsuario() == null ) {
                usuariosTelefono.setIdUsuario( new Usuario() );
            }
            usuarioMapper.partialUpdate( usuariosTelefonoDto.getIdUsuario(), usuariosTelefono.getIdUsuario() );
        }

        return usuariosTelefono;
    }

    @Override
    public List<UsuariosTelefono> toEntity(List<UsuariosTelefonoDto> usuariosTelefonoDto) {
        if ( usuariosTelefonoDto == null ) {
            return null;
        }

        List<UsuariosTelefono> list = new ArrayList<UsuariosTelefono>( usuariosTelefonoDto.size() );
        for ( UsuariosTelefonoDto usuariosTelefonoDto1 : usuariosTelefonoDto ) {
            list.add( toEntity( usuariosTelefonoDto1 ) );
        }

        return list;
    }

    @Override
    public List<UsuariosTelefonoDto> toDto(List<UsuariosTelefono> usuariosTelefono) {
        if ( usuariosTelefono == null ) {
            return null;
        }

        List<UsuariosTelefonoDto> list = new ArrayList<UsuariosTelefonoDto>( usuariosTelefono.size() );
        for ( UsuariosTelefono usuariosTelefono1 : usuariosTelefono ) {
            list.add( toDto( usuariosTelefono1 ) );
        }

        return list;
    }

    protected Set<UsuariosTelefono> usuariosTelefonoDtoSetToUsuariosTelefonoSet(Set<UsuariosTelefonoDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<UsuariosTelefono> set1 = new LinkedHashSet<UsuariosTelefono>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( UsuariosTelefonoDto usuariosTelefonoDto : set ) {
            set1.add( toEntity( usuariosTelefonoDto ) );
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

    protected Set<UsuariosTelefonoDto> usuariosTelefonoSetToUsuariosTelefonoDtoSet(Set<UsuariosTelefono> set) {
        if ( set == null ) {
            return null;
        }

        Set<UsuariosTelefonoDto> set1 = new LinkedHashSet<UsuariosTelefonoDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( UsuariosTelefono usuariosTelefono : set ) {
            set1.add( toDto( usuariosTelefono ) );
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
}
