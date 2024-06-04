package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.PerfilesPermisoDto;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import codigocreativo.uy.servidorapp.entidades.PerfilesPermiso;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T19:06:55-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@ApplicationScoped
public class PerfilesPermisoMapperImpl implements PerfilesPermisoMapper {

    @Inject
    private PerfilMapper perfilMapper;

    @Override
    public PerfilesPermiso toEntity(PerfilesPermisoDto perfilesPermisoDto) {
        if ( perfilesPermisoDto == null ) {
            return null;
        }

        PerfilesPermiso perfilesPermiso = new PerfilesPermiso();

        perfilesPermiso.setIdPerfil( perfilMapper.toEntity( perfilesPermisoDto.getIdPerfil() ) );

        return perfilesPermiso;
    }

    @Override
    public PerfilesPermisoDto toDto(PerfilesPermiso perfilesPermiso) {
        if ( perfilesPermiso == null ) {
            return null;
        }

        PerfilesPermisoDto perfilesPermisoDto = new PerfilesPermisoDto();

        perfilesPermisoDto.setIdPerfil( perfilMapper.toDto( perfilesPermiso.getIdPerfil() ) );

        return perfilesPermisoDto;
    }

    @Override
    public PerfilesPermiso partialUpdate(PerfilesPermisoDto perfilesPermisoDto, PerfilesPermiso perfilesPermiso) {
        if ( perfilesPermisoDto == null ) {
            return perfilesPermiso;
        }

        if ( perfilesPermisoDto.getIdPerfil() != null ) {
            if ( perfilesPermiso.getIdPerfil() == null ) {
                perfilesPermiso.setIdPerfil( new Perfil() );
            }
            perfilMapper.partialUpdate( perfilesPermisoDto.getIdPerfil(), perfilesPermiso.getIdPerfil() );
        }

        return perfilesPermiso;
    }

    @Override
    public List<PerfilesPermiso> toEntity(List<PerfilesPermisoDto> perfilesPermisoDto) {
        if ( perfilesPermisoDto == null ) {
            return null;
        }

        List<PerfilesPermiso> list = new ArrayList<PerfilesPermiso>( perfilesPermisoDto.size() );
        for ( PerfilesPermisoDto perfilesPermisoDto1 : perfilesPermisoDto ) {
            list.add( toEntity( perfilesPermisoDto1 ) );
        }

        return list;
    }

    @Override
    public List<PerfilesPermisoDto> toDto(List<PerfilesPermiso> perfilesPermiso) {
        if ( perfilesPermiso == null ) {
            return null;
        }

        List<PerfilesPermisoDto> list = new ArrayList<PerfilesPermisoDto>( perfilesPermiso.size() );
        for ( PerfilesPermiso perfilesPermiso1 : perfilesPermiso ) {
            list.add( toDto( perfilesPermiso1 ) );
        }

        return list;
    }
}
