package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.entidades.Institucion;
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
public class InstitucionMapperImpl implements InstitucionMapper {

    @Override
    public Institucion toEntity(InstitucionDto institucionDto) {
        if ( institucionDto == null ) {
            return null;
        }

        Institucion institucion = new Institucion();

        institucion.setId( institucionDto.getId() );
        institucion.setNombre( institucionDto.getNombre() );

        return institucion;
    }

    @Override
    public InstitucionDto toDto(Institucion institucion) {
        if ( institucion == null ) {
            return null;
        }

        InstitucionDto institucionDto = new InstitucionDto();

        institucionDto.setId( institucion.getId() );
        institucionDto.setNombre( institucion.getNombre() );

        return institucionDto;
    }

    @Override
    public Institucion partialUpdate(InstitucionDto institucionDto, Institucion institucion) {
        if ( institucionDto == null ) {
            return institucion;
        }

        if ( institucionDto.getId() != null ) {
            institucion.setId( institucionDto.getId() );
        }
        if ( institucionDto.getNombre() != null ) {
            institucion.setNombre( institucionDto.getNombre() );
        }

        return institucion;
    }

    @Override
    public List<Institucion> toEntity(List<InstitucionDto> institucionDto) {
        if ( institucionDto == null ) {
            return null;
        }

        List<Institucion> list = new ArrayList<Institucion>( institucionDto.size() );
        for ( InstitucionDto institucionDto1 : institucionDto ) {
            list.add( toEntity( institucionDto1 ) );
        }

        return list;
    }

    @Override
    public List<InstitucionDto> toDto(List<Institucion> institucion) {
        if ( institucion == null ) {
            return null;
        }

        List<InstitucionDto> list = new ArrayList<InstitucionDto>( institucion.size() );
        for ( Institucion institucion1 : institucion ) {
            list.add( toDto( institucion1 ) );
        }

        return list;
    }
}
