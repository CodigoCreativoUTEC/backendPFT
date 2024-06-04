package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.PerfilDto;
import codigocreativo.uy.servidorapp.DTO.PermisoDto;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.Permiso;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T19:06:54-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@ApplicationScoped
public class PerfilMapperImpl implements PerfilMapper {

    @Override
    public Perfil toEntity(PerfilDto perfilDto) {
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

    @Override
    public PerfilDto toDto(Perfil perfil) {
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

    @Override
    public Perfil partialUpdate(PerfilDto perfilDto, Perfil perfil) {
        if ( perfilDto == null ) {
            return perfil;
        }

        if ( perfil.getPermisos() != null ) {
            List<Permiso> list = permisoDtoListToPermisoList( perfilDto.getPermisos() );
            if ( list != null ) {
                perfil.getPermisos().clear();
                perfil.getPermisos().addAll( list );
            }
        }
        else {
            List<Permiso> list = permisoDtoListToPermisoList( perfilDto.getPermisos() );
            if ( list != null ) {
                perfil.setPermisos( list );
            }
        }
        if ( perfilDto.getId() != null ) {
            perfil.setId( perfilDto.getId() );
        }
        if ( perfilDto.getNombrePerfil() != null ) {
            perfil.setNombrePerfil( perfilDto.getNombrePerfil() );
        }
        if ( perfilDto.getEstado() != null ) {
            perfil.setEstado( perfilDto.getEstado() );
        }

        return perfil;
    }

    @Override
    public List<Perfil> toEntity(List<PerfilDto> perfilDto) {
        if ( perfilDto == null ) {
            return null;
        }

        List<Perfil> list = new ArrayList<Perfil>( perfilDto.size() );
        for ( PerfilDto perfilDto1 : perfilDto ) {
            list.add( toEntity( perfilDto1 ) );
        }

        return list;
    }

    @Override
    public List<PerfilDto> toDto(List<Perfil> perfil) {
        if ( perfil == null ) {
            return null;
        }

        List<PerfilDto> list = new ArrayList<PerfilDto>( perfil.size() );
        for ( Perfil perfil1 : perfil ) {
            list.add( toDto( perfil1 ) );
        }

        return list;
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
}
