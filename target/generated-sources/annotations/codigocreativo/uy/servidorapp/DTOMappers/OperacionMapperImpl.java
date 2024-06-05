package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.OperacionDto;
import codigocreativo.uy.servidorapp.entidades.Operacion;
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
public class OperacionMapperImpl implements OperacionMapper {

    @Override
    public Operacion toEntity(OperacionDto operacionDto) {
        if ( operacionDto == null ) {
            return null;
        }

        Operacion operacion = new Operacion();

        operacion.setId( operacionDto.getId() );
        operacion.setNombreOperacion( operacionDto.getNombreOperacion() );

        return operacion;
    }

    @Override
    public OperacionDto toDto(Operacion operacion) {
        if ( operacion == null ) {
            return null;
        }

        OperacionDto operacionDto = new OperacionDto();

        operacionDto.setId( operacion.getId() );
        operacionDto.setNombreOperacion( operacion.getNombreOperacion() );

        return operacionDto;
    }

    @Override
    public Operacion partialUpdate(OperacionDto operacionDto, Operacion operacion) {
        if ( operacionDto == null ) {
            return operacion;
        }

        if ( operacionDto.getId() != null ) {
            operacion.setId( operacionDto.getId() );
        }
        if ( operacionDto.getNombreOperacion() != null ) {
            operacion.setNombreOperacion( operacionDto.getNombreOperacion() );
        }

        return operacion;
    }

    @Override
    public List<Operacion> toEntity(List<OperacionDto> operacionDto) {
        if ( operacionDto == null ) {
            return null;
        }

        List<Operacion> list = new ArrayList<Operacion>( operacionDto.size() );
        for ( OperacionDto operacionDto1 : operacionDto ) {
            list.add( toEntity( operacionDto1 ) );
        }

        return list;
    }

    @Override
    public List<OperacionDto> toDto(List<Operacion> operacion) {
        if ( operacion == null ) {
            return null;
        }

        List<OperacionDto> list = new ArrayList<OperacionDto>( operacion.size() );
        for ( Operacion operacion1 : operacion ) {
            list.add( toDto( operacion1 ) );
        }

        return list;
    }
}
