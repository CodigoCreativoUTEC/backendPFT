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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T19:06:54-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@ApplicationScoped
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(UsuarioDto usuarioDto, CycleAvoidingMappingContext context) {
        Usuario target = context.getMappedInstance( usuarioDto, Usuario.class );
        if ( target != null ) {
            return target;
        }

        if ( usuarioDto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        context.storeMappedInstance( usuarioDto, usuario );

        usuario.setUsuariosTelefonos( usuariosTelefonoDtoSetToUsuariosTelefonoSet( usuarioDto.getUsuariosTelefonos(), context ) );
        usuario.setNombreUsuario( usuarioDto.getNombreUsuario() );
        usuario.setId( usuarioDto.getId() );
        usuario.setCedula( usuarioDto.getCedula() );
        usuario.setIdPerfil( perfilDtoToPerfil( usuarioDto.getIdPerfil(), context ) );
        usuario.setIdInstitucion( institucionDtoToInstitucion( usuarioDto.getIdInstitucion(), context ) );
        usuario.setEmail( usuarioDto.getEmail() );
        usuario.setContrasenia( usuarioDto.getContrasenia() );
        usuario.setFechaNacimiento( usuarioDto.getFechaNacimiento() );
        usuario.setEstado( usuarioDto.getEstado() );
        usuario.setNombre( usuarioDto.getNombre() );
        usuario.setApellido( usuarioDto.getApellido() );

        return usuario;
    }

    @Override
    public UsuarioDto toDto(Usuario usuario, CycleAvoidingMappingContext context) {
        UsuarioDto target = context.getMappedInstance( usuario, UsuarioDto.class );
        if ( target != null ) {
            return target;
        }

        if ( usuario == null ) {
            return null;
        }

        UsuarioDto usuarioDto = new UsuarioDto();

        context.storeMappedInstance( usuario, usuarioDto );

        usuarioDto.setId( usuario.getId() );
        usuarioDto.setCedula( usuario.getCedula() );
        usuarioDto.setEmail( usuario.getEmail() );
        usuarioDto.setContrasenia( usuario.getContrasenia() );
        usuarioDto.setFechaNacimiento( usuario.getFechaNacimiento() );
        usuarioDto.setEstado( usuario.getEstado() );
        usuarioDto.setNombre( usuario.getNombre() );
        usuarioDto.setApellido( usuario.getApellido() );
        usuarioDto.setNombreUsuario( usuario.getNombreUsuario() );
        usuarioDto.setIdInstitucion( institucionToInstitucionDto( usuario.getIdInstitucion(), context ) );
        usuarioDto.setIdPerfil( perfilToPerfilDto( usuario.getIdPerfil(), context ) );
        usuarioDto.setUsuariosTelefonos( usuariosTelefonoSetToUsuariosTelefonoDtoSet( usuario.getUsuariosTelefonos(), context ) );

        return usuarioDto;
    }

    @Override
    public Usuario partialUpdate(UsuarioDto usuarioDto, Usuario usuario) {
        if ( usuarioDto == null ) {
            return usuario;
        }

        if ( usuario.getUsuariosTelefonos() != null ) {
            Set<UsuariosTelefono> set = usuariosTelefonoDtoSetToUsuariosTelefonoSet1( usuarioDto.getUsuariosTelefonos() );
            if ( set != null ) {
                usuario.getUsuariosTelefonos().clear();
                usuario.getUsuariosTelefonos().addAll( set );
            }
        }
        else {
            Set<UsuariosTelefono> set = usuariosTelefonoDtoSetToUsuariosTelefonoSet1( usuarioDto.getUsuariosTelefonos() );
            if ( set != null ) {
                usuario.setUsuariosTelefonos( set );
            }
        }
        if ( usuarioDto.getNombreUsuario() != null ) {
            usuario.setNombreUsuario( usuarioDto.getNombreUsuario() );
        }
        if ( usuarioDto.getId() != null ) {
            usuario.setId( usuarioDto.getId() );
        }
        if ( usuarioDto.getCedula() != null ) {
            usuario.setCedula( usuarioDto.getCedula() );
        }
        if ( usuarioDto.getIdPerfil() != null ) {
            if ( usuario.getIdPerfil() == null ) {
                usuario.setIdPerfil( new Perfil() );
            }
            perfilDtoToPerfil2( usuarioDto.getIdPerfil(), usuario.getIdPerfil() );
        }
        if ( usuarioDto.getIdInstitucion() != null ) {
            if ( usuario.getIdInstitucion() == null ) {
                usuario.setIdInstitucion( new Institucion() );
            }
            institucionDtoToInstitucion2( usuarioDto.getIdInstitucion(), usuario.getIdInstitucion() );
        }
        if ( usuarioDto.getEmail() != null ) {
            usuario.setEmail( usuarioDto.getEmail() );
        }
        if ( usuarioDto.getContrasenia() != null ) {
            usuario.setContrasenia( usuarioDto.getContrasenia() );
        }
        if ( usuarioDto.getFechaNacimiento() != null ) {
            usuario.setFechaNacimiento( usuarioDto.getFechaNacimiento() );
        }
        if ( usuarioDto.getEstado() != null ) {
            usuario.setEstado( usuarioDto.getEstado() );
        }
        if ( usuarioDto.getNombre() != null ) {
            usuario.setNombre( usuarioDto.getNombre() );
        }
        if ( usuarioDto.getApellido() != null ) {
            usuario.setApellido( usuarioDto.getApellido() );
        }

        return usuario;
    }

    @Override
    public List<Usuario> toEntity(List<UsuarioDto> usuarioDto, CycleAvoidingMappingContext context) {
        List<Usuario> target = context.getMappedInstance( usuarioDto, List.class );
        if ( target != null ) {
            return target;
        }

        if ( usuarioDto == null ) {
            return null;
        }

        List<Usuario> list = new ArrayList<Usuario>( usuarioDto.size() );
        context.storeMappedInstance( usuarioDto, list );

        for ( UsuarioDto usuarioDto1 : usuarioDto ) {
            list.add( toEntity( usuarioDto1, context ) );
        }

        return list;
    }

    @Override
    public List<UsuarioDto> toDto(List<Usuario> usuario, CycleAvoidingMappingContext context) {
        List<UsuarioDto> target = context.getMappedInstance( usuario, List.class );
        if ( target != null ) {
            return target;
        }

        if ( usuario == null ) {
            return null;
        }

        List<UsuarioDto> list = new ArrayList<UsuarioDto>( usuario.size() );
        context.storeMappedInstance( usuario, list );

        for ( Usuario usuario1 : usuario ) {
            list.add( toDto( usuario1, context ) );
        }

        return list;
    }

    protected UsuariosTelefono usuariosTelefonoDtoToUsuariosTelefono(UsuariosTelefonoDto usuariosTelefonoDto, CycleAvoidingMappingContext context) {
        UsuariosTelefono target = context.getMappedInstance( usuariosTelefonoDto, UsuariosTelefono.class );
        if ( target != null ) {
            return target;
        }

        if ( usuariosTelefonoDto == null ) {
            return null;
        }

        UsuariosTelefono usuariosTelefono = new UsuariosTelefono();

        context.storeMappedInstance( usuariosTelefonoDto, usuariosTelefono );

        usuariosTelefono.setNumero( usuariosTelefonoDto.getNumero() );
        usuariosTelefono.setId( usuariosTelefonoDto.getId() );
        usuariosTelefono.setIdUsuario( toEntity( usuariosTelefonoDto.getIdUsuario(), context ) );

        return usuariosTelefono;
    }

    protected Set<UsuariosTelefono> usuariosTelefonoDtoSetToUsuariosTelefonoSet(Set<UsuariosTelefonoDto> set, CycleAvoidingMappingContext context) {
        Set<UsuariosTelefono> target = context.getMappedInstance( set, Set.class );
        if ( target != null ) {
            return target;
        }

        if ( set == null ) {
            return null;
        }

        Set<UsuariosTelefono> set1 = new LinkedHashSet<UsuariosTelefono>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        context.storeMappedInstance( set, set1 );

        for ( UsuariosTelefonoDto usuariosTelefonoDto : set ) {
            set1.add( usuariosTelefonoDtoToUsuariosTelefono( usuariosTelefonoDto, context ) );
        }

        return set1;
    }

    protected Permiso permisoDtoToPermiso(PermisoDto permisoDto, CycleAvoidingMappingContext context) {
        Permiso target = context.getMappedInstance( permisoDto, Permiso.class );
        if ( target != null ) {
            return target;
        }

        if ( permisoDto == null ) {
            return null;
        }

        Permiso permiso = new Permiso();

        context.storeMappedInstance( permisoDto, permiso );

        permiso.setId( permisoDto.getId() );
        permiso.setTipoPermiso( permisoDto.getTipoPermiso() );

        return permiso;
    }

    protected List<Permiso> permisoDtoListToPermisoList(List<PermisoDto> list, CycleAvoidingMappingContext context) {
        List<Permiso> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<Permiso> list1 = new ArrayList<Permiso>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( PermisoDto permisoDto : list ) {
            list1.add( permisoDtoToPermiso( permisoDto, context ) );
        }

        return list1;
    }

    protected Perfil perfilDtoToPerfil(PerfilDto perfilDto, CycleAvoidingMappingContext context) {
        Perfil target = context.getMappedInstance( perfilDto, Perfil.class );
        if ( target != null ) {
            return target;
        }

        if ( perfilDto == null ) {
            return null;
        }

        Perfil perfil = new Perfil();

        context.storeMappedInstance( perfilDto, perfil );

        perfil.setPermisos( permisoDtoListToPermisoList( perfilDto.getPermisos(), context ) );
        perfil.setId( perfilDto.getId() );
        perfil.setNombrePerfil( perfilDto.getNombrePerfil() );
        perfil.setEstado( perfilDto.getEstado() );

        return perfil;
    }

    protected Institucion institucionDtoToInstitucion(InstitucionDto institucionDto, CycleAvoidingMappingContext context) {
        Institucion target = context.getMappedInstance( institucionDto, Institucion.class );
        if ( target != null ) {
            return target;
        }

        if ( institucionDto == null ) {
            return null;
        }

        Institucion institucion = new Institucion();

        context.storeMappedInstance( institucionDto, institucion );

        institucion.setId( institucionDto.getId() );
        institucion.setNombre( institucionDto.getNombre() );

        return institucion;
    }

    protected InstitucionDto institucionToInstitucionDto(Institucion institucion, CycleAvoidingMappingContext context) {
        InstitucionDto target = context.getMappedInstance( institucion, InstitucionDto.class );
        if ( target != null ) {
            return target;
        }

        if ( institucion == null ) {
            return null;
        }

        InstitucionDto institucionDto = new InstitucionDto();

        context.storeMappedInstance( institucion, institucionDto );

        institucionDto.setId( institucion.getId() );
        institucionDto.setNombre( institucion.getNombre() );

        return institucionDto;
    }

    protected PermisoDto permisoToPermisoDto(Permiso permiso, CycleAvoidingMappingContext context) {
        PermisoDto target = context.getMappedInstance( permiso, PermisoDto.class );
        if ( target != null ) {
            return target;
        }

        if ( permiso == null ) {
            return null;
        }

        PermisoDto permisoDto = new PermisoDto();

        context.storeMappedInstance( permiso, permisoDto );

        permisoDto.setId( permiso.getId() );
        permisoDto.setTipoPermiso( permiso.getTipoPermiso() );

        return permisoDto;
    }

    protected List<PermisoDto> permisoListToPermisoDtoList(List<Permiso> list, CycleAvoidingMappingContext context) {
        List<PermisoDto> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<PermisoDto> list1 = new ArrayList<PermisoDto>( list.size() );
        context.storeMappedInstance( list, list1 );

        for ( Permiso permiso : list ) {
            list1.add( permisoToPermisoDto( permiso, context ) );
        }

        return list1;
    }

    protected PerfilDto perfilToPerfilDto(Perfil perfil, CycleAvoidingMappingContext context) {
        PerfilDto target = context.getMappedInstance( perfil, PerfilDto.class );
        if ( target != null ) {
            return target;
        }

        if ( perfil == null ) {
            return null;
        }

        PerfilDto perfilDto = new PerfilDto();

        context.storeMappedInstance( perfil, perfilDto );

        perfilDto.setId( perfil.getId() );
        perfilDto.setNombrePerfil( perfil.getNombrePerfil() );
        perfilDto.setEstado( perfil.getEstado() );
        perfilDto.setPermisos( permisoListToPermisoDtoList( perfil.getPermisos(), context ) );

        return perfilDto;
    }

    protected UsuariosTelefonoDto usuariosTelefonoToUsuariosTelefonoDto(UsuariosTelefono usuariosTelefono, CycleAvoidingMappingContext context) {
        UsuariosTelefonoDto target = context.getMappedInstance( usuariosTelefono, UsuariosTelefonoDto.class );
        if ( target != null ) {
            return target;
        }

        if ( usuariosTelefono == null ) {
            return null;
        }

        UsuariosTelefonoDto usuariosTelefonoDto = new UsuariosTelefonoDto();

        context.storeMappedInstance( usuariosTelefono, usuariosTelefonoDto );

        usuariosTelefonoDto.setId( usuariosTelefono.getId() );
        usuariosTelefonoDto.setNumero( usuariosTelefono.getNumero() );
        usuariosTelefonoDto.setIdUsuario( toDto( usuariosTelefono.getIdUsuario(), context ) );

        return usuariosTelefonoDto;
    }

    protected Set<UsuariosTelefonoDto> usuariosTelefonoSetToUsuariosTelefonoDtoSet(Set<UsuariosTelefono> set, CycleAvoidingMappingContext context) {
        Set<UsuariosTelefonoDto> target = context.getMappedInstance( set, Set.class );
        if ( target != null ) {
            return target;
        }

        if ( set == null ) {
            return null;
        }

        Set<UsuariosTelefonoDto> set1 = new LinkedHashSet<UsuariosTelefonoDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        context.storeMappedInstance( set, set1 );

        for ( UsuariosTelefono usuariosTelefono : set ) {
            set1.add( usuariosTelefonoToUsuariosTelefonoDto( usuariosTelefono, context ) );
        }

        return set1;
    }

    protected Set<UsuariosTelefono> usuariosTelefonoDtoSetToUsuariosTelefonoSet1(Set<UsuariosTelefonoDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<UsuariosTelefono> set1 = new LinkedHashSet<UsuariosTelefono>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( UsuariosTelefonoDto usuariosTelefonoDto : set ) {
            set1.add( usuariosTelefonoDtoToUsuariosTelefono1( usuariosTelefonoDto ) );
        }

        return set1;
    }

    protected Permiso permisoDtoToPermiso1(PermisoDto permisoDto) {
        if ( permisoDto == null ) {
            return null;
        }

        Permiso permiso = new Permiso();

        permiso.setId( permisoDto.getId() );
        permiso.setTipoPermiso( permisoDto.getTipoPermiso() );

        return permiso;
    }

    protected List<Permiso> permisoDtoListToPermisoList1(List<PermisoDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Permiso> list1 = new ArrayList<Permiso>( list.size() );
        for ( PermisoDto permisoDto : list ) {
            list1.add( permisoDtoToPermiso1( permisoDto ) );
        }

        return list1;
    }

    protected Perfil perfilDtoToPerfil1(PerfilDto perfilDto) {
        if ( perfilDto == null ) {
            return null;
        }

        Perfil perfil = new Perfil();

        perfil.setPermisos( permisoDtoListToPermisoList1( perfilDto.getPermisos() ) );
        perfil.setId( perfilDto.getId() );
        perfil.setNombrePerfil( perfilDto.getNombrePerfil() );
        perfil.setEstado( perfilDto.getEstado() );

        return perfil;
    }

    protected Institucion institucionDtoToInstitucion1(InstitucionDto institucionDto) {
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

        usuario.setUsuariosTelefonos( usuariosTelefonoDtoSetToUsuariosTelefonoSet1( usuarioDto.getUsuariosTelefonos() ) );
        usuario.setNombreUsuario( usuarioDto.getNombreUsuario() );
        usuario.setId( usuarioDto.getId() );
        usuario.setCedula( usuarioDto.getCedula() );
        usuario.setIdPerfil( perfilDtoToPerfil1( usuarioDto.getIdPerfil() ) );
        usuario.setIdInstitucion( institucionDtoToInstitucion1( usuarioDto.getIdInstitucion() ) );
        usuario.setEmail( usuarioDto.getEmail() );
        usuario.setContrasenia( usuarioDto.getContrasenia() );
        usuario.setFechaNacimiento( usuarioDto.getFechaNacimiento() );
        usuario.setEstado( usuarioDto.getEstado() );
        usuario.setNombre( usuarioDto.getNombre() );
        usuario.setApellido( usuarioDto.getApellido() );

        return usuario;
    }

    protected UsuariosTelefono usuariosTelefonoDtoToUsuariosTelefono1(UsuariosTelefonoDto usuariosTelefonoDto) {
        if ( usuariosTelefonoDto == null ) {
            return null;
        }

        UsuariosTelefono usuariosTelefono = new UsuariosTelefono();

        usuariosTelefono.setNumero( usuariosTelefonoDto.getNumero() );
        usuariosTelefono.setId( usuariosTelefonoDto.getId() );
        usuariosTelefono.setIdUsuario( usuarioDtoToUsuario( usuariosTelefonoDto.getIdUsuario() ) );

        return usuariosTelefono;
    }

    protected void perfilDtoToPerfil2(PerfilDto perfilDto, Perfil mappingTarget) {
        if ( perfilDto == null ) {
            return;
        }

        if ( mappingTarget.getPermisos() != null ) {
            List<Permiso> list = permisoDtoListToPermisoList1( perfilDto.getPermisos() );
            if ( list != null ) {
                mappingTarget.getPermisos().clear();
                mappingTarget.getPermisos().addAll( list );
            }
        }
        else {
            List<Permiso> list = permisoDtoListToPermisoList1( perfilDto.getPermisos() );
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

    protected void institucionDtoToInstitucion2(InstitucionDto institucionDto, Institucion mappingTarget) {
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
}
