package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.BajaUbicacionDto;
import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.entidades.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-04T19:06:55-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@ApplicationScoped
public class BajaUbicacionMapperImpl implements BajaUbicacionMapper {

    @Inject
    private UsuarioMapper usuarioMapper;
    @Inject
    private UbicacionMapper ubicacionMapper;

    @Override
    public BajaUbicacion toEntity(BajaUbicacionDto bajaUbicacionDto, CycleAvoidingMappingContext context) {
        BajaUbicacion target = context.getMappedInstance( bajaUbicacionDto, BajaUbicacion.class );
        if ( target != null ) {
            return target;
        }

        if ( bajaUbicacionDto == null ) {
            return null;
        }

        BajaUbicacion bajaUbicacion = new BajaUbicacion();

        context.storeMappedInstance( bajaUbicacionDto, bajaUbicacion );

        bajaUbicacion.setId( bajaUbicacionDto.getId() );
        bajaUbicacion.setIdUsuario( usuarioMapper.toEntity( bajaUbicacionDto.getIdUsuario(), context ) );
        bajaUbicacion.setIdUbicacion( ubicacionMapper.toEntity( bajaUbicacionDto.getIdUbicacion() ) );
        bajaUbicacion.setRazon( bajaUbicacionDto.getRazon() );
        bajaUbicacion.setComentario( bajaUbicacionDto.getComentario() );
        bajaUbicacion.setFecha( bajaUbicacionDto.getFecha() );

        return bajaUbicacion;
    }

    @Override
    public BajaUbicacionDto toDto(BajaUbicacion bajaUbicacion, CycleAvoidingMappingContext context) {
        BajaUbicacionDto target = context.getMappedInstance( bajaUbicacion, BajaUbicacionDto.class );
        if ( target != null ) {
            return target;
        }

        if ( bajaUbicacion == null ) {
            return null;
        }

        BajaUbicacionDto bajaUbicacionDto = new BajaUbicacionDto();

        context.storeMappedInstance( bajaUbicacion, bajaUbicacionDto );

        bajaUbicacionDto.setId( bajaUbicacion.getId() );
        bajaUbicacionDto.setIdUsuario( usuarioMapper.toDto( bajaUbicacion.getIdUsuario(), context ) );
        bajaUbicacionDto.setIdUbicacion( ubicacionMapper.toDto( bajaUbicacion.getIdUbicacion() ) );
        bajaUbicacionDto.setRazon( bajaUbicacion.getRazon() );
        bajaUbicacionDto.setComentario( bajaUbicacion.getComentario() );
        bajaUbicacionDto.setFecha( bajaUbicacion.getFecha() );

        return bajaUbicacionDto;
    }

    @Override
    public BajaUbicacion partialUpdate(BajaUbicacionDto bajaUbicacionDto, BajaUbicacion bajaUbicacion, CycleAvoidingMappingContext context) {
        BajaUbicacion target = context.getMappedInstance( bajaUbicacionDto, BajaUbicacion.class );
        if ( target != null ) {
            return target;
        }

        if ( bajaUbicacionDto == null ) {
            return bajaUbicacion;
        }

        context.storeMappedInstance( bajaUbicacionDto, bajaUbicacion );

        if ( bajaUbicacionDto.getId() != null ) {
            bajaUbicacion.setId( bajaUbicacionDto.getId() );
        }
        if ( bajaUbicacionDto.getIdUsuario() != null ) {
            if ( bajaUbicacion.getIdUsuario() == null ) {
                bajaUbicacion.setIdUsuario( new Usuario() );
            }
            usuarioMapper.partialUpdate( bajaUbicacionDto.getIdUsuario(), bajaUbicacion.getIdUsuario() );
        }
        if ( bajaUbicacionDto.getIdUbicacion() != null ) {
            if ( bajaUbicacion.getIdUbicacion() == null ) {
                bajaUbicacion.setIdUbicacion( new Ubicacion() );
            }
            ubicacionMapper.partialUpdate( bajaUbicacionDto.getIdUbicacion(), bajaUbicacion.getIdUbicacion() );
        }
        if ( bajaUbicacionDto.getRazon() != null ) {
            bajaUbicacion.setRazon( bajaUbicacionDto.getRazon() );
        }
        if ( bajaUbicacionDto.getComentario() != null ) {
            bajaUbicacion.setComentario( bajaUbicacionDto.getComentario() );
        }
        if ( bajaUbicacionDto.getFecha() != null ) {
            bajaUbicacion.setFecha( bajaUbicacionDto.getFecha() );
        }

        return bajaUbicacion;
    }

    @Override
    public List<BajaUbicacion> toEntity(List<BajaUbicacionDto> bajaUbicacionDto, CycleAvoidingMappingContext context) {
        List<BajaUbicacion> target = context.getMappedInstance( bajaUbicacionDto, List.class );
        if ( target != null ) {
            return target;
        }

        if ( bajaUbicacionDto == null ) {
            return null;
        }

        List<BajaUbicacion> list = new ArrayList<BajaUbicacion>( bajaUbicacionDto.size() );
        context.storeMappedInstance( bajaUbicacionDto, list );

        for ( BajaUbicacionDto bajaUbicacionDto1 : bajaUbicacionDto ) {
            list.add( toEntity( bajaUbicacionDto1, context ) );
        }

        return list;
    }

    @Override
    public List<BajaUbicacionDto> toDto(List<BajaUbicacion> bajaUbicacion, CycleAvoidingMappingContext context) {
        List<BajaUbicacionDto> target = context.getMappedInstance( bajaUbicacion, List.class );
        if ( target != null ) {
            return target;
        }

        if ( bajaUbicacion == null ) {
            return null;
        }

        List<BajaUbicacionDto> list = new ArrayList<BajaUbicacionDto>( bajaUbicacion.size() );
        context.storeMappedInstance( bajaUbicacion, list );

        for ( BajaUbicacion bajaUbicacion1 : bajaUbicacion ) {
            list.add( toDto( bajaUbicacion1, context ) );
        }

        return list;
    }
}
