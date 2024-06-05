package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.PaisDto;
import codigocreativo.uy.servidorapp.entidades.Pais;
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
public class PaisMapperImpl implements PaisMapper {

    @Override
    public Pais toEntity(PaisDto paisDto) {
        if ( paisDto == null ) {
            return null;
        }

        Pais pais = new Pais();

        pais.setId( paisDto.getId() );
        pais.setNombre( paisDto.getNombre() );

        return pais;
    }

    @Override
    public PaisDto toDto(Pais pais) {
        if ( pais == null ) {
            return null;
        }

        PaisDto paisDto = new PaisDto();

        paisDto.setId( pais.getId() );
        paisDto.setNombre( pais.getNombre() );

        return paisDto;
    }

    @Override
    public Pais partialUpdate(PaisDto paisDto, Pais pais) {
        if ( paisDto == null ) {
            return pais;
        }

        if ( paisDto.getId() != null ) {
            pais.setId( paisDto.getId() );
        }
        if ( paisDto.getNombre() != null ) {
            pais.setNombre( paisDto.getNombre() );
        }

        return pais;
    }

    @Override
    public List<Pais> toEntity(List<PaisDto> paisDto) {
        if ( paisDto == null ) {
            return null;
        }

        List<Pais> list = new ArrayList<Pais>( paisDto.size() );
        for ( PaisDto paisDto1 : paisDto ) {
            list.add( toEntity( paisDto1 ) );
        }

        return list;
    }

    @Override
    public List<PaisDto> toDto(List<Pais> pais) {
        if ( pais == null ) {
            return null;
        }

        List<PaisDto> list = new ArrayList<PaisDto>( pais.size() );
        for ( Pais pais1 : pais ) {
            list.add( toDto( pais1 ) );
        }

        return list;
    }
}
