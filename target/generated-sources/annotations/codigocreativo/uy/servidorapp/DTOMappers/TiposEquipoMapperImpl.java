package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.TiposEquipoDto;
import codigocreativo.uy.servidorapp.entidades.TiposEquipo;
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
public class TiposEquipoMapperImpl implements TiposEquipoMapper {

    @Override
    public TiposEquipo toEntity(TiposEquipoDto tiposEquipoDto) {
        if ( tiposEquipoDto == null ) {
            return null;
        }

        TiposEquipo tiposEquipo = new TiposEquipo();

        tiposEquipo.setId( tiposEquipoDto.getId() );
        tiposEquipo.setNombreTipo( tiposEquipoDto.getNombreTipo() );

        return tiposEquipo;
    }

    @Override
    public TiposEquipoDto toDto(TiposEquipo tiposEquipo) {
        if ( tiposEquipo == null ) {
            return null;
        }

        TiposEquipoDto tiposEquipoDto = new TiposEquipoDto();

        tiposEquipoDto.setId( tiposEquipo.getId() );
        tiposEquipoDto.setNombreTipo( tiposEquipo.getNombreTipo() );

        return tiposEquipoDto;
    }

    @Override
    public TiposEquipo partialUpdate(TiposEquipoDto tiposEquipoDto, TiposEquipo tiposEquipo) {
        if ( tiposEquipoDto == null ) {
            return tiposEquipo;
        }

        if ( tiposEquipoDto.getId() != null ) {
            tiposEquipo.setId( tiposEquipoDto.getId() );
        }
        if ( tiposEquipoDto.getNombreTipo() != null ) {
            tiposEquipo.setNombreTipo( tiposEquipoDto.getNombreTipo() );
        }

        return tiposEquipo;
    }

    @Override
    public List<TiposEquipo> toEntity(List<TiposEquipoDto> tiposEquipoDto) {
        if ( tiposEquipoDto == null ) {
            return null;
        }

        List<TiposEquipo> list = new ArrayList<TiposEquipo>( tiposEquipoDto.size() );
        for ( TiposEquipoDto tiposEquipoDto1 : tiposEquipoDto ) {
            list.add( toEntity( tiposEquipoDto1 ) );
        }

        return list;
    }

    @Override
    public List<TiposEquipoDto> toDto(List<TiposEquipo> tiposEquipo) {
        if ( tiposEquipo == null ) {
            return null;
        }

        List<TiposEquipoDto> list = new ArrayList<TiposEquipoDto>( tiposEquipo.size() );
        for ( TiposEquipo tiposEquipo1 : tiposEquipo ) {
            list.add( toDto( tiposEquipo1 ) );
        }

        return list;
    }
}
