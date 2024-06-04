package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.entidades.ProveedoresEquipo;
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
public class ProveedoresEquipoMapperImpl implements ProveedoresEquipoMapper {

    @Override
    public ProveedoresEquipo toEntity(ProveedoresEquipoDto proveedoresEquipoDto) {
        if ( proveedoresEquipoDto == null ) {
            return null;
        }

        ProveedoresEquipo proveedoresEquipo = new ProveedoresEquipo();

        proveedoresEquipo.setId( proveedoresEquipoDto.getId() );
        proveedoresEquipo.setNombre( proveedoresEquipoDto.getNombre() );

        return proveedoresEquipo;
    }

    @Override
    public ProveedoresEquipoDto toDto(ProveedoresEquipo proveedoresEquipo) {
        if ( proveedoresEquipo == null ) {
            return null;
        }

        ProveedoresEquipoDto proveedoresEquipoDto = new ProveedoresEquipoDto();

        proveedoresEquipoDto.setId( proveedoresEquipo.getId() );
        proveedoresEquipoDto.setNombre( proveedoresEquipo.getNombre() );

        return proveedoresEquipoDto;
    }

    @Override
    public ProveedoresEquipo partialUpdate(ProveedoresEquipoDto proveedoresEquipoDto, ProveedoresEquipo proveedoresEquipo) {
        if ( proveedoresEquipoDto == null ) {
            return proveedoresEquipo;
        }

        if ( proveedoresEquipoDto.getId() != null ) {
            proveedoresEquipo.setId( proveedoresEquipoDto.getId() );
        }
        if ( proveedoresEquipoDto.getNombre() != null ) {
            proveedoresEquipo.setNombre( proveedoresEquipoDto.getNombre() );
        }

        return proveedoresEquipo;
    }

    @Override
    public List<ProveedoresEquipo> toEntity(List<ProveedoresEquipoDto> proveedoresEquipoDto) {
        if ( proveedoresEquipoDto == null ) {
            return null;
        }

        List<ProveedoresEquipo> list = new ArrayList<ProveedoresEquipo>( proveedoresEquipoDto.size() );
        for ( ProveedoresEquipoDto proveedoresEquipoDto1 : proveedoresEquipoDto ) {
            list.add( toEntity( proveedoresEquipoDto1 ) );
        }

        return list;
    }

    @Override
    public List<ProveedoresEquipoDto> toDto(List<ProveedoresEquipo> proveedoresEquipo) {
        if ( proveedoresEquipo == null ) {
            return null;
        }

        List<ProveedoresEquipoDto> list = new ArrayList<ProveedoresEquipoDto>( proveedoresEquipo.size() );
        for ( ProveedoresEquipo proveedoresEquipo1 : proveedoresEquipo ) {
            list.add( toDto( proveedoresEquipo1 ) );
        }

        return list;
    }
}
