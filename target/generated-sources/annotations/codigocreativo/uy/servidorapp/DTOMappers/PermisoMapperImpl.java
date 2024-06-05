package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.PermisoDto;
import codigocreativo.uy.servidorapp.entidades.Permiso;
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
public class PermisoMapperImpl implements PermisoMapper {

    @Override
    public Permiso toEntity(PermisoDto permisoDto) {
        if ( permisoDto == null ) {
            return null;
        }

        Permiso permiso = new Permiso();

        permiso.setId( permisoDto.getId() );
        permiso.setTipoPermiso( permisoDto.getTipoPermiso() );

        return permiso;
    }

    @Override
    public PermisoDto toDto(Permiso permiso) {
        if ( permiso == null ) {
            return null;
        }

        PermisoDto permisoDto = new PermisoDto();

        permisoDto.setId( permiso.getId() );
        permisoDto.setTipoPermiso( permiso.getTipoPermiso() );

        return permisoDto;
    }

    @Override
    public Permiso partialUpdate(PermisoDto permisoDto, Permiso permiso) {
        if ( permisoDto == null ) {
            return permiso;
        }

        if ( permisoDto.getId() != null ) {
            permiso.setId( permisoDto.getId() );
        }
        if ( permisoDto.getTipoPermiso() != null ) {
            permiso.setTipoPermiso( permisoDto.getTipoPermiso() );
        }

        return permiso;
    }

    @Override
    public List<Permiso> toEntity(List<PermisoDto> permisoDto) {
        if ( permisoDto == null ) {
            return null;
        }

        List<Permiso> list = new ArrayList<Permiso>( permisoDto.size() );
        for ( PermisoDto permisoDto1 : permisoDto ) {
            list.add( toEntity( permisoDto1 ) );
        }

        return list;
    }

    @Override
    public List<PermisoDto> toDto(List<Permiso> permiso) {
        if ( permiso == null ) {
            return null;
        }

        List<PermisoDto> list = new ArrayList<PermisoDto>( permiso.size() );
        for ( Permiso permiso1 : permiso ) {
            list.add( toDto( permiso1 ) );
        }

        return list;
    }
}
