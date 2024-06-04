package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.InstitucionDto;
import codigocreativo.uy.servidorapp.DTO.UbicacionDto;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
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
public class UbicacionMapperImpl implements UbicacionMapper {

    @Override
    public Ubicacion toEntity(UbicacionDto ubicacionDto) {
        if ( ubicacionDto == null ) {
            return null;
        }

        Ubicacion ubicacion = new Ubicacion();

        ubicacion.setEstado( ubicacionDto.getEstado() );
        ubicacion.setId( ubicacionDto.getId() );
        ubicacion.setNombre( ubicacionDto.getNombre() );
        ubicacion.setSector( ubicacionDto.getSector() );
        ubicacion.setPiso( ubicacionDto.getPiso() );
        ubicacion.setNumero( ubicacionDto.getNumero() );
        ubicacion.setIdInstitucion( institucionDtoToInstitucion( ubicacionDto.getIdInstitucion() ) );
        ubicacion.setCama( ubicacionDto.getCama() );

        return ubicacion;
    }

    @Override
    public UbicacionDto toDto(Ubicacion ubicacion) {
        if ( ubicacion == null ) {
            return null;
        }

        UbicacionDto ubicacionDto = new UbicacionDto();

        ubicacionDto.setId( ubicacion.getId() );
        ubicacionDto.setNombre( ubicacion.getNombre() );
        ubicacionDto.setSector( ubicacion.getSector() );
        ubicacionDto.setPiso( ubicacion.getPiso() );
        ubicacionDto.setNumero( ubicacion.getNumero() );
        ubicacionDto.setCama( ubicacion.getCama() );
        ubicacionDto.setEstado( ubicacion.getEstado() );
        ubicacionDto.setIdInstitucion( institucionToInstitucionDto( ubicacion.getIdInstitucion() ) );

        return ubicacionDto;
    }

    @Override
    public Ubicacion partialUpdate(UbicacionDto ubicacionDto, Ubicacion ubicacion) {
        if ( ubicacionDto == null ) {
            return ubicacion;
        }

        if ( ubicacionDto.getEstado() != null ) {
            ubicacion.setEstado( ubicacionDto.getEstado() );
        }
        if ( ubicacionDto.getId() != null ) {
            ubicacion.setId( ubicacionDto.getId() );
        }
        if ( ubicacionDto.getNombre() != null ) {
            ubicacion.setNombre( ubicacionDto.getNombre() );
        }
        if ( ubicacionDto.getSector() != null ) {
            ubicacion.setSector( ubicacionDto.getSector() );
        }
        if ( ubicacionDto.getPiso() != null ) {
            ubicacion.setPiso( ubicacionDto.getPiso() );
        }
        if ( ubicacionDto.getNumero() != null ) {
            ubicacion.setNumero( ubicacionDto.getNumero() );
        }
        if ( ubicacionDto.getIdInstitucion() != null ) {
            if ( ubicacion.getIdInstitucion() == null ) {
                ubicacion.setIdInstitucion( new Institucion() );
            }
            institucionDtoToInstitucion1( ubicacionDto.getIdInstitucion(), ubicacion.getIdInstitucion() );
        }
        if ( ubicacionDto.getCama() != null ) {
            ubicacion.setCama( ubicacionDto.getCama() );
        }

        return ubicacion;
    }

    @Override
    public List<Ubicacion> toEntity(List<UbicacionDto> ubicacionDto) {
        if ( ubicacionDto == null ) {
            return null;
        }

        List<Ubicacion> list = new ArrayList<Ubicacion>( ubicacionDto.size() );
        for ( UbicacionDto ubicacionDto1 : ubicacionDto ) {
            list.add( toEntity( ubicacionDto1 ) );
        }

        return list;
    }

    @Override
    public List<UbicacionDto> toDto(List<Ubicacion> ubicacion) {
        if ( ubicacion == null ) {
            return null;
        }

        List<UbicacionDto> list = new ArrayList<UbicacionDto>( ubicacion.size() );
        for ( Ubicacion ubicacion1 : ubicacion ) {
            list.add( toDto( ubicacion1 ) );
        }

        return list;
    }

    protected Institucion institucionDtoToInstitucion(InstitucionDto institucionDto) {
        if ( institucionDto == null ) {
            return null;
        }

        Institucion institucion = new Institucion();

        institucion.setId( institucionDto.getId() );
        institucion.setNombre( institucionDto.getNombre() );

        return institucion;
    }

    protected InstitucionDto institucionToInstitucionDto(Institucion institucion) {
        if ( institucion == null ) {
            return null;
        }

        InstitucionDto institucionDto = new InstitucionDto();

        institucionDto.setId( institucion.getId() );
        institucionDto.setNombre( institucion.getNombre() );

        return institucionDto;
    }

    protected void institucionDtoToInstitucion1(InstitucionDto institucionDto, Institucion mappingTarget) {
        if ( institucionDto == null ) {
            return;
        }

        if ( institucionDto.getId() != null ) {
            mappingTarget.setId( institucionDto.getId() );
        }
        if ( institucionDto.getNombre() != null ) {
            mappingTarget.setNombre( institucionDto.getNombre() );
        }
    }
}
