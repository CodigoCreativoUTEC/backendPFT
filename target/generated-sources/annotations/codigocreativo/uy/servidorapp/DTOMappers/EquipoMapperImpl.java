package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.EquipoDto;
import codigocreativo.uy.servidorapp.DTO.EquiposUbicacioneDto;
import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.DTO.MarcasModeloDto;
import codigocreativo.uy.servidorapp.DTO.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.DTO.PaisDto;
import codigocreativo.uy.servidorapp.DTO.PerfilDto;
import codigocreativo.uy.servidorapp.DTO.PermisoDto;
import codigocreativo.uy.servidorapp.DTO.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.DTO.TiposEquipoDto;
import codigocreativo.uy.servidorapp.DTO.UbicacionDto;
import codigocreativo.uy.servidorapp.DTO.UsuarioDto;
import codigocreativo.uy.servidorapp.DTO.UsuariosTelefonoDto;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import codigocreativo.uy.servidorapp.entidades.EquiposUbicacione;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import codigocreativo.uy.servidorapp.entidades.ModelosEquipo;
import codigocreativo.uy.servidorapp.entidades.Pais;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Permiso;
import codigocreativo.uy.servidorapp.entidades.ProveedoresEquipo;
import codigocreativo.uy.servidorapp.entidades.TiposEquipo;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
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
public class EquipoMapperImpl implements EquipoMapper {

    @Override
    public Equipo toEntity(EquipoDto equipoDto, CycleAvoidingMappingContext context) {
        Equipo target = context.getMappedInstance( equipoDto, Equipo.class );
        if ( target != null ) {
            return target;
        }

        if ( equipoDto == null ) {
            return null;
        }

        Equipo equipo = new Equipo();

        context.storeMappedInstance( equipoDto, equipo );

        equipo.setIdUbicacion( ubicacionDtoToUbicacion( equipoDto.getIdUbicacion(), context ) );
        equipo.setEquiposUbicaciones( equiposUbicacioneDtoSetToEquiposUbicacioneSet( equipoDto.getEquiposUbicaciones(), context ) );
        equipo.setEstado( equipoDto.getEstado() );
        equipo.setId( equipoDto.getId() );
        equipo.setIdInterno( equipoDto.getIdInterno() );
        equipo.setNroSerie( equipoDto.getNroSerie() );
        equipo.setNombre( equipoDto.getNombre() );
        equipo.setIdTipo( tiposEquipoDtoToTiposEquipo( equipoDto.getIdTipo(), context ) );
        equipo.setIdProveedor( proveedoresEquipoDtoToProveedoresEquipo( equipoDto.getIdProveedor(), context ) );
        equipo.setIdPais( paisDtoToPais( equipoDto.getIdPais(), context ) );
        equipo.setIdModelo( modelosEquipoDtoToModelosEquipo( equipoDto.getIdModelo(), context ) );
        equipo.setImagen( equipoDto.getImagen() );
        equipo.setFechaAdquisicion( equipoDto.getFechaAdquisicion() );

        return equipo;
    }

    @Override
    public EquipoDto toDto(Equipo equipo, CycleAvoidingMappingContext context) {
        EquipoDto target = context.getMappedInstance( equipo, EquipoDto.class );
        if ( target != null ) {
            return target;
        }

        if ( equipo == null ) {
            return null;
        }

        EquipoDto equipoDto = new EquipoDto();

        context.storeMappedInstance( equipo, equipoDto );

        equipoDto.setId( equipo.getId() );
        equipoDto.setIdInterno( equipo.getIdInterno() );
        equipoDto.setNroSerie( equipo.getNroSerie() );
        equipoDto.setNombre( equipo.getNombre() );
        equipoDto.setImagen( equipo.getImagen() );
        equipoDto.setFechaAdquisicion( equipo.getFechaAdquisicion() );
        equipoDto.setEstado( equipo.getEstado() );
        equipoDto.setIdTipo( tiposEquipoToTiposEquipoDto( equipo.getIdTipo(), context ) );
        equipoDto.setIdProveedor( proveedoresEquipoToProveedoresEquipoDto( equipo.getIdProveedor(), context ) );
        equipoDto.setIdPais( paisToPaisDto( equipo.getIdPais(), context ) );
        equipoDto.setIdModelo( modelosEquipoToModelosEquipoDto( equipo.getIdModelo(), context ) );
        equipoDto.setEquiposUbicaciones( equiposUbicacioneSetToEquiposUbicacioneDtoSet( equipo.getEquiposUbicaciones(), context ) );
        equipoDto.setIdUbicacion( ubicacionToUbicacionDto( equipo.getIdUbicacion(), context ) );

        return equipoDto;
    }

    @Override
    public Equipo partialUpdate(EquipoDto equipoDto, Equipo equipo, CycleAvoidingMappingContext context) {
        Equipo target = context.getMappedInstance( equipoDto, Equipo.class );
        if ( target != null ) {
            return target;
        }

        if ( equipoDto == null ) {
            return equipo;
        }

        context.storeMappedInstance( equipoDto, equipo );

        if ( equipoDto.getIdUbicacion() != null ) {
            if ( equipo.getIdUbicacion() == null ) {
                equipo.setIdUbicacion( new Ubicacion() );
            }
            ubicacionDtoToUbicacion1( equipoDto.getIdUbicacion(), context, equipo.getIdUbicacion() );
        }
        if ( equipo.getEquiposUbicaciones() != null ) {
            Set<EquiposUbicacione> set = equiposUbicacioneDtoSetToEquiposUbicacioneSet( equipoDto.getEquiposUbicaciones(), context );
            if ( set != null ) {
                equipo.getEquiposUbicaciones().clear();
                equipo.getEquiposUbicaciones().addAll( set );
            }
        }
        else {
            Set<EquiposUbicacione> set = equiposUbicacioneDtoSetToEquiposUbicacioneSet( equipoDto.getEquiposUbicaciones(), context );
            if ( set != null ) {
                equipo.setEquiposUbicaciones( set );
            }
        }
        if ( equipoDto.getEstado() != null ) {
            equipo.setEstado( equipoDto.getEstado() );
        }
        if ( equipoDto.getId() != null ) {
            equipo.setId( equipoDto.getId() );
        }
        if ( equipoDto.getIdInterno() != null ) {
            equipo.setIdInterno( equipoDto.getIdInterno() );
        }
        if ( equipoDto.getNroSerie() != null ) {
            equipo.setNroSerie( equipoDto.getNroSerie() );
        }
        if ( equipoDto.getNombre() != null ) {
            equipo.setNombre( equipoDto.getNombre() );
        }
        if ( equipoDto.getIdTipo() != null ) {
            if ( equipo.getIdTipo() == null ) {
                equipo.setIdTipo( new TiposEquipo() );
            }
            tiposEquipoDtoToTiposEquipo1( equipoDto.getIdTipo(), context, equipo.getIdTipo() );
        }
        if ( equipoDto.getIdProveedor() != null ) {
            if ( equipo.getIdProveedor() == null ) {
                equipo.setIdProveedor( new ProveedoresEquipo() );
            }
            proveedoresEquipoDtoToProveedoresEquipo1( equipoDto.getIdProveedor(), context, equipo.getIdProveedor() );
        }
        if ( equipoDto.getIdPais() != null ) {
            if ( equipo.getIdPais() == null ) {
                equipo.setIdPais( new Pais() );
            }
            paisDtoToPais1( equipoDto.getIdPais(), context, equipo.getIdPais() );
        }
        if ( equipoDto.getIdModelo() != null ) {
            if ( equipo.getIdModelo() == null ) {
                equipo.setIdModelo( new ModelosEquipo() );
            }
            modelosEquipoDtoToModelosEquipo1( equipoDto.getIdModelo(), context, equipo.getIdModelo() );
        }
        if ( equipoDto.getImagen() != null ) {
            equipo.setImagen( equipoDto.getImagen() );
        }
        if ( equipoDto.getFechaAdquisicion() != null ) {
            equipo.setFechaAdquisicion( equipoDto.getFechaAdquisicion() );
        }

        return equipo;
    }

    @Override
    public List<Equipo> toEntity(List<EquipoDto> equipoDto, CycleAvoidingMappingContext context) {
        List<Equipo> target = context.getMappedInstance( equipoDto, List.class );
        if ( target != null ) {
            return target;
        }

        if ( equipoDto == null ) {
            return null;
        }

        List<Equipo> list = new ArrayList<Equipo>( equipoDto.size() );
        context.storeMappedInstance( equipoDto, list );

        for ( EquipoDto equipoDto1 : equipoDto ) {
            list.add( toEntity( equipoDto1, context ) );
        }

        return list;
    }

    @Override
    public List<EquipoDto> toDto(List<Equipo> equipo, CycleAvoidingMappingContext context) {
        List<EquipoDto> target = context.getMappedInstance( equipo, List.class );
        if ( target != null ) {
            return target;
        }

        if ( equipo == null ) {
            return null;
        }

        List<EquipoDto> list = new ArrayList<EquipoDto>( equipo.size() );
        context.storeMappedInstance( equipo, list );

        for ( Equipo equipo1 : equipo ) {
            list.add( toDto( equipo1, context ) );
        }

        return list;
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

    protected Ubicacion ubicacionDtoToUbicacion(UbicacionDto ubicacionDto, CycleAvoidingMappingContext context) {
        Ubicacion target = context.getMappedInstance( ubicacionDto, Ubicacion.class );
        if ( target != null ) {
            return target;
        }

        if ( ubicacionDto == null ) {
            return null;
        }

        Ubicacion ubicacion = new Ubicacion();

        context.storeMappedInstance( ubicacionDto, ubicacion );

        ubicacion.setEstado( ubicacionDto.getEstado() );
        ubicacion.setId( ubicacionDto.getId() );
        ubicacion.setNombre( ubicacionDto.getNombre() );
        ubicacion.setSector( ubicacionDto.getSector() );
        ubicacion.setPiso( ubicacionDto.getPiso() );
        ubicacion.setNumero( ubicacionDto.getNumero() );
        ubicacion.setIdInstitucion( institucionDtoToInstitucion( ubicacionDto.getIdInstitucion(), context ) );
        ubicacion.setCama( ubicacionDto.getCama() );

        return ubicacion;
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
        usuariosTelefono.setIdUsuario( usuarioDtoToUsuario( usuariosTelefonoDto.getIdUsuario(), context ) );

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

    protected Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto, CycleAvoidingMappingContext context) {
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

    protected EquiposUbicacione equiposUbicacioneDtoToEquiposUbicacione(EquiposUbicacioneDto equiposUbicacioneDto, CycleAvoidingMappingContext context) {
        EquiposUbicacione target = context.getMappedInstance( equiposUbicacioneDto, EquiposUbicacione.class );
        if ( target != null ) {
            return target;
        }

        if ( equiposUbicacioneDto == null ) {
            return null;
        }

        EquiposUbicacione equiposUbicacione = new EquiposUbicacione();

        context.storeMappedInstance( equiposUbicacioneDto, equiposUbicacione );

        equiposUbicacione.setObservaciones( equiposUbicacioneDto.getObservaciones() );
        equiposUbicacione.setUsuario( usuarioDtoToUsuario( equiposUbicacioneDto.getUsuario(), context ) );
        equiposUbicacione.setIdUbicacion( ubicacionDtoToUbicacion( equiposUbicacioneDto.getIdUbicacion(), context ) );
        equiposUbicacione.setIdEquipo( toEntity( equiposUbicacioneDto.getIdEquipo(), context ) );
        equiposUbicacione.setId( equiposUbicacioneDto.getId() );
        equiposUbicacione.setFecha( equiposUbicacioneDto.getFecha() );

        return equiposUbicacione;
    }

    protected Set<EquiposUbicacione> equiposUbicacioneDtoSetToEquiposUbicacioneSet(Set<EquiposUbicacioneDto> set, CycleAvoidingMappingContext context) {
        Set<EquiposUbicacione> target = context.getMappedInstance( set, Set.class );
        if ( target != null ) {
            return target;
        }

        if ( set == null ) {
            return null;
        }

        Set<EquiposUbicacione> set1 = new LinkedHashSet<EquiposUbicacione>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        context.storeMappedInstance( set, set1 );

        for ( EquiposUbicacioneDto equiposUbicacioneDto : set ) {
            set1.add( equiposUbicacioneDtoToEquiposUbicacione( equiposUbicacioneDto, context ) );
        }

        return set1;
    }

    protected TiposEquipo tiposEquipoDtoToTiposEquipo(TiposEquipoDto tiposEquipoDto, CycleAvoidingMappingContext context) {
        TiposEquipo target = context.getMappedInstance( tiposEquipoDto, TiposEquipo.class );
        if ( target != null ) {
            return target;
        }

        if ( tiposEquipoDto == null ) {
            return null;
        }

        TiposEquipo tiposEquipo = new TiposEquipo();

        context.storeMappedInstance( tiposEquipoDto, tiposEquipo );

        tiposEquipo.setId( tiposEquipoDto.getId() );
        tiposEquipo.setNombreTipo( tiposEquipoDto.getNombreTipo() );

        return tiposEquipo;
    }

    protected ProveedoresEquipo proveedoresEquipoDtoToProveedoresEquipo(ProveedoresEquipoDto proveedoresEquipoDto, CycleAvoidingMappingContext context) {
        ProveedoresEquipo target = context.getMappedInstance( proveedoresEquipoDto, ProveedoresEquipo.class );
        if ( target != null ) {
            return target;
        }

        if ( proveedoresEquipoDto == null ) {
            return null;
        }

        ProveedoresEquipo proveedoresEquipo = new ProveedoresEquipo();

        context.storeMappedInstance( proveedoresEquipoDto, proveedoresEquipo );

        proveedoresEquipo.setId( proveedoresEquipoDto.getId() );
        proveedoresEquipo.setNombre( proveedoresEquipoDto.getNombre() );

        return proveedoresEquipo;
    }

    protected Pais paisDtoToPais(PaisDto paisDto, CycleAvoidingMappingContext context) {
        Pais target = context.getMappedInstance( paisDto, Pais.class );
        if ( target != null ) {
            return target;
        }

        if ( paisDto == null ) {
            return null;
        }

        Pais pais = new Pais();

        context.storeMappedInstance( paisDto, pais );

        pais.setId( paisDto.getId() );
        pais.setNombre( paisDto.getNombre() );

        return pais;
    }

    protected MarcasModelo marcasModeloDtoToMarcasModelo(MarcasModeloDto marcasModeloDto, CycleAvoidingMappingContext context) {
        MarcasModelo target = context.getMappedInstance( marcasModeloDto, MarcasModelo.class );
        if ( target != null ) {
            return target;
        }

        if ( marcasModeloDto == null ) {
            return null;
        }

        MarcasModelo marcasModelo = new MarcasModelo();

        context.storeMappedInstance( marcasModeloDto, marcasModelo );

        marcasModelo.setId( marcasModeloDto.getId() );
        marcasModelo.setNombre( marcasModeloDto.getNombre() );

        return marcasModelo;
    }

    protected ModelosEquipo modelosEquipoDtoToModelosEquipo(ModelosEquipoDto modelosEquipoDto, CycleAvoidingMappingContext context) {
        ModelosEquipo target = context.getMappedInstance( modelosEquipoDto, ModelosEquipo.class );
        if ( target != null ) {
            return target;
        }

        if ( modelosEquipoDto == null ) {
            return null;
        }

        ModelosEquipo modelosEquipo = new ModelosEquipo();

        context.storeMappedInstance( modelosEquipoDto, modelosEquipo );

        modelosEquipo.setId( modelosEquipoDto.getId() );
        modelosEquipo.setIdMarca( marcasModeloDtoToMarcasModelo( modelosEquipoDto.getIdMarca(), context ) );
        modelosEquipo.setNombre( modelosEquipoDto.getNombre() );

        return modelosEquipo;
    }

    protected TiposEquipoDto tiposEquipoToTiposEquipoDto(TiposEquipo tiposEquipo, CycleAvoidingMappingContext context) {
        TiposEquipoDto target = context.getMappedInstance( tiposEquipo, TiposEquipoDto.class );
        if ( target != null ) {
            return target;
        }

        if ( tiposEquipo == null ) {
            return null;
        }

        TiposEquipoDto tiposEquipoDto = new TiposEquipoDto();

        context.storeMappedInstance( tiposEquipo, tiposEquipoDto );

        tiposEquipoDto.setId( tiposEquipo.getId() );
        tiposEquipoDto.setNombreTipo( tiposEquipo.getNombreTipo() );

        return tiposEquipoDto;
    }

    protected ProveedoresEquipoDto proveedoresEquipoToProveedoresEquipoDto(ProveedoresEquipo proveedoresEquipo, CycleAvoidingMappingContext context) {
        ProveedoresEquipoDto target = context.getMappedInstance( proveedoresEquipo, ProveedoresEquipoDto.class );
        if ( target != null ) {
            return target;
        }

        if ( proveedoresEquipo == null ) {
            return null;
        }

        ProveedoresEquipoDto proveedoresEquipoDto = new ProveedoresEquipoDto();

        context.storeMappedInstance( proveedoresEquipo, proveedoresEquipoDto );

        proveedoresEquipoDto.setId( proveedoresEquipo.getId() );
        proveedoresEquipoDto.setNombre( proveedoresEquipo.getNombre() );

        return proveedoresEquipoDto;
    }

    protected PaisDto paisToPaisDto(Pais pais, CycleAvoidingMappingContext context) {
        PaisDto target = context.getMappedInstance( pais, PaisDto.class );
        if ( target != null ) {
            return target;
        }

        if ( pais == null ) {
            return null;
        }

        PaisDto paisDto = new PaisDto();

        context.storeMappedInstance( pais, paisDto );

        paisDto.setId( pais.getId() );
        paisDto.setNombre( pais.getNombre() );

        return paisDto;
    }

    protected MarcasModeloDto marcasModeloToMarcasModeloDto(MarcasModelo marcasModelo, CycleAvoidingMappingContext context) {
        MarcasModeloDto target = context.getMappedInstance( marcasModelo, MarcasModeloDto.class );
        if ( target != null ) {
            return target;
        }

        if ( marcasModelo == null ) {
            return null;
        }

        MarcasModeloDto marcasModeloDto = new MarcasModeloDto();

        context.storeMappedInstance( marcasModelo, marcasModeloDto );

        marcasModeloDto.setId( marcasModelo.getId() );
        marcasModeloDto.setNombre( marcasModelo.getNombre() );

        return marcasModeloDto;
    }

    protected ModelosEquipoDto modelosEquipoToModelosEquipoDto(ModelosEquipo modelosEquipo, CycleAvoidingMappingContext context) {
        ModelosEquipoDto target = context.getMappedInstance( modelosEquipo, ModelosEquipoDto.class );
        if ( target != null ) {
            return target;
        }

        if ( modelosEquipo == null ) {
            return null;
        }

        ModelosEquipoDto modelosEquipoDto = new ModelosEquipoDto();

        context.storeMappedInstance( modelosEquipo, modelosEquipoDto );

        modelosEquipoDto.setId( modelosEquipo.getId() );
        modelosEquipoDto.setNombre( modelosEquipo.getNombre() );
        modelosEquipoDto.setIdMarca( marcasModeloToMarcasModeloDto( modelosEquipo.getIdMarca(), context ) );

        return modelosEquipoDto;
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

    protected UbicacionDto ubicacionToUbicacionDto(Ubicacion ubicacion, CycleAvoidingMappingContext context) {
        UbicacionDto target = context.getMappedInstance( ubicacion, UbicacionDto.class );
        if ( target != null ) {
            return target;
        }

        if ( ubicacion == null ) {
            return null;
        }

        UbicacionDto ubicacionDto = new UbicacionDto();

        context.storeMappedInstance( ubicacion, ubicacionDto );

        ubicacionDto.setId( ubicacion.getId() );
        ubicacionDto.setNombre( ubicacion.getNombre() );
        ubicacionDto.setSector( ubicacion.getSector() );
        ubicacionDto.setPiso( ubicacion.getPiso() );
        ubicacionDto.setNumero( ubicacion.getNumero() );
        ubicacionDto.setCama( ubicacion.getCama() );
        ubicacionDto.setEstado( ubicacion.getEstado() );
        ubicacionDto.setIdInstitucion( institucionToInstitucionDto( ubicacion.getIdInstitucion(), context ) );

        return ubicacionDto;
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
        usuariosTelefonoDto.setIdUsuario( usuarioToUsuarioDto( usuariosTelefono.getIdUsuario(), context ) );

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

    protected UsuarioDto usuarioToUsuarioDto(Usuario usuario, CycleAvoidingMappingContext context) {
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

    protected EquiposUbicacioneDto equiposUbicacioneToEquiposUbicacioneDto(EquiposUbicacione equiposUbicacione, CycleAvoidingMappingContext context) {
        EquiposUbicacioneDto target = context.getMappedInstance( equiposUbicacione, EquiposUbicacioneDto.class );
        if ( target != null ) {
            return target;
        }

        if ( equiposUbicacione == null ) {
            return null;
        }

        EquiposUbicacioneDto equiposUbicacioneDto = new EquiposUbicacioneDto();

        context.storeMappedInstance( equiposUbicacione, equiposUbicacioneDto );

        equiposUbicacioneDto.setId( equiposUbicacione.getId() );
        equiposUbicacioneDto.setFecha( equiposUbicacione.getFecha() );
        equiposUbicacioneDto.setIdEquipo( toDto( equiposUbicacione.getIdEquipo(), context ) );
        equiposUbicacioneDto.setIdUbicacion( ubicacionToUbicacionDto( equiposUbicacione.getIdUbicacion(), context ) );
        equiposUbicacioneDto.setUsuario( usuarioToUsuarioDto( equiposUbicacione.getUsuario(), context ) );
        equiposUbicacioneDto.setObservaciones( equiposUbicacione.getObservaciones() );

        return equiposUbicacioneDto;
    }

    protected Set<EquiposUbicacioneDto> equiposUbicacioneSetToEquiposUbicacioneDtoSet(Set<EquiposUbicacione> set, CycleAvoidingMappingContext context) {
        Set<EquiposUbicacioneDto> target = context.getMappedInstance( set, Set.class );
        if ( target != null ) {
            return target;
        }

        if ( set == null ) {
            return null;
        }

        Set<EquiposUbicacioneDto> set1 = new LinkedHashSet<EquiposUbicacioneDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        context.storeMappedInstance( set, set1 );

        for ( EquiposUbicacione equiposUbicacione : set ) {
            set1.add( equiposUbicacioneToEquiposUbicacioneDto( equiposUbicacione, context ) );
        }

        return set1;
    }

    protected void institucionDtoToInstitucion1(InstitucionDto institucionDto, CycleAvoidingMappingContext context, Institucion mappingTarget) {
        Institucion target = context.getMappedInstance( institucionDto, Institucion.class );
        if ( target != null ) {
            return;
        }

        if ( institucionDto == null ) {
            return;
        }

        context.storeMappedInstance( institucionDto, mappingTarget );

        if ( institucionDto.getId() != null ) {
            mappingTarget.setId( institucionDto.getId() );
        }
        if ( institucionDto.getNombre() != null ) {
            mappingTarget.setNombre( institucionDto.getNombre() );
        }
    }

    protected void ubicacionDtoToUbicacion1(UbicacionDto ubicacionDto, CycleAvoidingMappingContext context, Ubicacion mappingTarget) {
        Ubicacion target = context.getMappedInstance( ubicacionDto, Ubicacion.class );
        if ( target != null ) {
            return;
        }

        if ( ubicacionDto == null ) {
            return;
        }

        context.storeMappedInstance( ubicacionDto, mappingTarget );

        if ( ubicacionDto.getEstado() != null ) {
            mappingTarget.setEstado( ubicacionDto.getEstado() );
        }
        if ( ubicacionDto.getId() != null ) {
            mappingTarget.setId( ubicacionDto.getId() );
        }
        if ( ubicacionDto.getNombre() != null ) {
            mappingTarget.setNombre( ubicacionDto.getNombre() );
        }
        if ( ubicacionDto.getSector() != null ) {
            mappingTarget.setSector( ubicacionDto.getSector() );
        }
        if ( ubicacionDto.getPiso() != null ) {
            mappingTarget.setPiso( ubicacionDto.getPiso() );
        }
        if ( ubicacionDto.getNumero() != null ) {
            mappingTarget.setNumero( ubicacionDto.getNumero() );
        }
        if ( ubicacionDto.getIdInstitucion() != null ) {
            if ( mappingTarget.getIdInstitucion() == null ) {
                mappingTarget.setIdInstitucion( new Institucion() );
            }
            institucionDtoToInstitucion1( ubicacionDto.getIdInstitucion(), context, mappingTarget.getIdInstitucion() );
        }
        if ( ubicacionDto.getCama() != null ) {
            mappingTarget.setCama( ubicacionDto.getCama() );
        }
    }

    protected void tiposEquipoDtoToTiposEquipo1(TiposEquipoDto tiposEquipoDto, CycleAvoidingMappingContext context, TiposEquipo mappingTarget) {
        TiposEquipo target = context.getMappedInstance( tiposEquipoDto, TiposEquipo.class );
        if ( target != null ) {
            return;
        }

        if ( tiposEquipoDto == null ) {
            return;
        }

        context.storeMappedInstance( tiposEquipoDto, mappingTarget );

        if ( tiposEquipoDto.getId() != null ) {
            mappingTarget.setId( tiposEquipoDto.getId() );
        }
        if ( tiposEquipoDto.getNombreTipo() != null ) {
            mappingTarget.setNombreTipo( tiposEquipoDto.getNombreTipo() );
        }
    }

    protected void proveedoresEquipoDtoToProveedoresEquipo1(ProveedoresEquipoDto proveedoresEquipoDto, CycleAvoidingMappingContext context, ProveedoresEquipo mappingTarget) {
        ProveedoresEquipo target = context.getMappedInstance( proveedoresEquipoDto, ProveedoresEquipo.class );
        if ( target != null ) {
            return;
        }

        if ( proveedoresEquipoDto == null ) {
            return;
        }

        context.storeMappedInstance( proveedoresEquipoDto, mappingTarget );

        if ( proveedoresEquipoDto.getId() != null ) {
            mappingTarget.setId( proveedoresEquipoDto.getId() );
        }
        if ( proveedoresEquipoDto.getNombre() != null ) {
            mappingTarget.setNombre( proveedoresEquipoDto.getNombre() );
        }
    }

    protected void paisDtoToPais1(PaisDto paisDto, CycleAvoidingMappingContext context, Pais mappingTarget) {
        Pais target = context.getMappedInstance( paisDto, Pais.class );
        if ( target != null ) {
            return;
        }

        if ( paisDto == null ) {
            return;
        }

        context.storeMappedInstance( paisDto, mappingTarget );

        if ( paisDto.getId() != null ) {
            mappingTarget.setId( paisDto.getId() );
        }
        if ( paisDto.getNombre() != null ) {
            mappingTarget.setNombre( paisDto.getNombre() );
        }
    }

    protected void marcasModeloDtoToMarcasModelo1(MarcasModeloDto marcasModeloDto, CycleAvoidingMappingContext context, MarcasModelo mappingTarget) {
        MarcasModelo target = context.getMappedInstance( marcasModeloDto, MarcasModelo.class );
        if ( target != null ) {
            return;
        }

        if ( marcasModeloDto == null ) {
            return;
        }

        context.storeMappedInstance( marcasModeloDto, mappingTarget );

        if ( marcasModeloDto.getId() != null ) {
            mappingTarget.setId( marcasModeloDto.getId() );
        }
        if ( marcasModeloDto.getNombre() != null ) {
            mappingTarget.setNombre( marcasModeloDto.getNombre() );
        }
    }

    protected void modelosEquipoDtoToModelosEquipo1(ModelosEquipoDto modelosEquipoDto, CycleAvoidingMappingContext context, ModelosEquipo mappingTarget) {
        ModelosEquipo target = context.getMappedInstance( modelosEquipoDto, ModelosEquipo.class );
        if ( target != null ) {
            return;
        }

        if ( modelosEquipoDto == null ) {
            return;
        }

        context.storeMappedInstance( modelosEquipoDto, mappingTarget );

        if ( modelosEquipoDto.getId() != null ) {
            mappingTarget.setId( modelosEquipoDto.getId() );
        }
        if ( modelosEquipoDto.getIdMarca() != null ) {
            if ( mappingTarget.getIdMarca() == null ) {
                mappingTarget.setIdMarca( new MarcasModelo() );
            }
            marcasModeloDtoToMarcasModelo1( modelosEquipoDto.getIdMarca(), context, mappingTarget.getIdMarca() );
        }
        if ( modelosEquipoDto.getNombre() != null ) {
            mappingTarget.setNombre( modelosEquipoDto.getNombre() );
        }
    }
}
