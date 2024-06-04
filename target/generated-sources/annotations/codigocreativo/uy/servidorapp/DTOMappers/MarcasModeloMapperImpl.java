package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.MarcasModeloDto;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T19:06:55-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@ApplicationScoped
public class MarcasModeloMapperImpl implements MarcasModeloMapper {

    @Override
    public MarcasModelo toEntity(MarcasModeloDto marcasModeloDto) {
        if ( marcasModeloDto == null ) {
            return null;
        }

        MarcasModelo marcasModelo = new MarcasModelo();

        marcasModelo.setId( marcasModeloDto.getId() );
        marcasModelo.setNombre( marcasModeloDto.getNombre() );

        return marcasModelo;
    }

    @Override
    public MarcasModeloDto toDto(MarcasModelo marcasModelo) {
        if ( marcasModelo == null ) {
            return null;
        }

        MarcasModeloDto marcasModeloDto = new MarcasModeloDto();

        marcasModeloDto.setId( marcasModelo.getId() );
        marcasModeloDto.setNombre( marcasModelo.getNombre() );

        return marcasModeloDto;
    }

    @Override
    public MarcasModelo partialUpdate(MarcasModeloDto marcasModeloDto, MarcasModelo marcasModelo) {
        if ( marcasModeloDto == null ) {
            return marcasModelo;
        }

        if ( marcasModeloDto.getId() != null ) {
            marcasModelo.setId( marcasModeloDto.getId() );
        }
        if ( marcasModeloDto.getNombre() != null ) {
            marcasModelo.setNombre( marcasModeloDto.getNombre() );
        }

        return marcasModelo;
    }

    @Override
    public List<MarcasModelo> toEntity(List<MarcasModeloDto> marcasModeloDto) {
        if ( marcasModeloDto == null ) {
            return null;
        }

        List<MarcasModelo> list = new ArrayList<MarcasModelo>( marcasModeloDto.size() );
        for ( MarcasModeloDto marcasModeloDto1 : marcasModeloDto ) {
            list.add( toEntity( marcasModeloDto1 ) );
        }

        return list;
    }

    @Override
    public List<MarcasModeloDto> toDto(List<MarcasModelo> marcasModelo) {
        if ( marcasModelo == null ) {
            return null;
        }

        List<MarcasModeloDto> list = new ArrayList<MarcasModeloDto>( marcasModelo.size() );
        for ( MarcasModelo marcasModelo1 : marcasModelo ) {
            list.add( toDto( marcasModelo1 ) );
        }

        return list;
    }
}
