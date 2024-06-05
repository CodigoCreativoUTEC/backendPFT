package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.PerfilesPermisoIdDto;
import codigocreativo.uy.servidorapp.entidades.PerfilesPermisoId;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-05T11:17:58-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@ApplicationScoped
public class PerfilesPermisoIdMapperImpl implements PerfilesPermisoIdMapper {

    @Override
    public PerfilesPermisoId toEntity(PerfilesPermisoIdDto perfilesPermisoIdDto) {
        if ( perfilesPermisoIdDto == null ) {
            return null;
        }

        PerfilesPermisoId perfilesPermisoId = new PerfilesPermisoId();

        perfilesPermisoId.setIdPerfil( perfilesPermisoIdDto.getIdPerfil() );
        perfilesPermisoId.setIdPermiso( perfilesPermisoIdDto.getIdPermiso() );

        return perfilesPermisoId;
    }

    @Override
    public PerfilesPermisoIdDto toDto(PerfilesPermisoId perfilesPermisoId) {
        if ( perfilesPermisoId == null ) {
            return null;
        }

        PerfilesPermisoIdDto perfilesPermisoIdDto = new PerfilesPermisoIdDto();

        perfilesPermisoIdDto.setIdPerfil( perfilesPermisoId.getIdPerfil() );
        perfilesPermisoIdDto.setIdPermiso( perfilesPermisoId.getIdPermiso() );

        return perfilesPermisoIdDto;
    }

    @Override
    public PerfilesPermisoId partialUpdate(PerfilesPermisoIdDto perfilesPermisoIdDto, PerfilesPermisoId perfilesPermisoId) {
        if ( perfilesPermisoIdDto == null ) {
            return perfilesPermisoId;
        }

        if ( perfilesPermisoIdDto.getIdPerfil() != null ) {
            perfilesPermisoId.setIdPerfil( perfilesPermisoIdDto.getIdPerfil() );
        }
        if ( perfilesPermisoIdDto.getIdPermiso() != null ) {
            perfilesPermisoId.setIdPermiso( perfilesPermisoIdDto.getIdPermiso() );
        }

        return perfilesPermisoId;
    }

    @Override
    public List<PerfilesPermisoId> toEntity(List<PerfilesPermisoIdDto> perfilesPermisoIdDto) {
        if ( perfilesPermisoIdDto == null ) {
            return null;
        }

        List<PerfilesPermisoId> list = new ArrayList<PerfilesPermisoId>( perfilesPermisoIdDto.size() );
        for ( PerfilesPermisoIdDto perfilesPermisoIdDto1 : perfilesPermisoIdDto ) {
            list.add( toEntity( perfilesPermisoIdDto1 ) );
        }

        return list;
    }

    @Override
    public List<PerfilesPermisoIdDto> toDto(List<PerfilesPermisoId> perfilesPermisoId) {
        if ( perfilesPermisoId == null ) {
            return null;
        }

        List<PerfilesPermisoIdDto> list = new ArrayList<PerfilesPermisoIdDto>( perfilesPermisoId.size() );
        for ( PerfilesPermisoId perfilesPermisoId1 : perfilesPermisoId ) {
            list.add( toDto( perfilesPermisoId1 ) );
        }

        return list;
    }
}
