package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
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
public class TiposIntervencioneMapperImpl implements TiposIntervencioneMapper {

    @Override
    public TiposIntervencione toEntity(TiposIntervencioneDto tiposIntervencioneDto) {
        if ( tiposIntervencioneDto == null ) {
            return null;
        }

        TiposIntervencione tiposIntervencione = new TiposIntervencione();

        tiposIntervencione.setId( tiposIntervencioneDto.getId() );
        tiposIntervencione.setNombreTipo( tiposIntervencioneDto.getNombreTipo() );
        tiposIntervencione.setEstado( tiposIntervencioneDto.getEstado() );

        return tiposIntervencione;
    }

    @Override
    public TiposIntervencioneDto toDto(TiposIntervencione tiposIntervencione) {
        if ( tiposIntervencione == null ) {
            return null;
        }

        TiposIntervencioneDto tiposIntervencioneDto = new TiposIntervencioneDto();

        tiposIntervencioneDto.setId( tiposIntervencione.getId() );
        tiposIntervencioneDto.setNombreTipo( tiposIntervencione.getNombreTipo() );
        tiposIntervencioneDto.setEstado( tiposIntervencione.getEstado() );

        return tiposIntervencioneDto;
    }

    @Override
    public TiposIntervencione partialUpdate(TiposIntervencioneDto tiposIntervencioneDto, TiposIntervencione tiposIntervencione) {
        if ( tiposIntervencioneDto == null ) {
            return tiposIntervencione;
        }

        if ( tiposIntervencioneDto.getId() != null ) {
            tiposIntervencione.setId( tiposIntervencioneDto.getId() );
        }
        if ( tiposIntervencioneDto.getNombreTipo() != null ) {
            tiposIntervencione.setNombreTipo( tiposIntervencioneDto.getNombreTipo() );
        }
        if ( tiposIntervencioneDto.getEstado() != null ) {
            tiposIntervencione.setEstado( tiposIntervencioneDto.getEstado() );
        }

        return tiposIntervencione;
    }

    @Override
    public List<TiposIntervencione> toEntity(List<TiposIntervencioneDto> tiposIntervencioneDto) {
        if ( tiposIntervencioneDto == null ) {
            return null;
        }

        List<TiposIntervencione> list = new ArrayList<TiposIntervencione>( tiposIntervencioneDto.size() );
        for ( TiposIntervencioneDto tiposIntervencioneDto1 : tiposIntervencioneDto ) {
            list.add( toEntity( tiposIntervencioneDto1 ) );
        }

        return list;
    }

    @Override
    public List<TiposIntervencioneDto> toDto(List<TiposIntervencione> tiposIntervencione) {
        if ( tiposIntervencione == null ) {
            return null;
        }

        List<TiposIntervencioneDto> list = new ArrayList<TiposIntervencioneDto>( tiposIntervencione.size() );
        for ( TiposIntervencione tiposIntervencione1 : tiposIntervencione ) {
            list.add( toDto( tiposIntervencione1 ) );
        }

        return list;
    }
}
