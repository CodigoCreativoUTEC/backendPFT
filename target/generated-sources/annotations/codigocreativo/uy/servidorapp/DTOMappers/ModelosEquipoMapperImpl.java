package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.MarcasModeloDto;
import codigocreativo.uy.servidorapp.DTO.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.entidades.MarcasModelo;
import codigocreativo.uy.servidorapp.entidades.ModelosEquipo;
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
public class ModelosEquipoMapperImpl implements ModelosEquipoMapper {

    @Override
    public ModelosEquipo toEntity(ModelosEquipoDto modelosEquipoDto) {
        if ( modelosEquipoDto == null ) {
            return null;
        }

        ModelosEquipo modelosEquipo = new ModelosEquipo();

        modelosEquipo.setId( modelosEquipoDto.getId() );
        modelosEquipo.setIdMarca( marcasModeloDtoToMarcasModelo( modelosEquipoDto.getIdMarca() ) );
        modelosEquipo.setNombre( modelosEquipoDto.getNombre() );

        return modelosEquipo;
    }

    @Override
    public ModelosEquipoDto toDto(ModelosEquipo modelosEquipo) {
        if ( modelosEquipo == null ) {
            return null;
        }

        ModelosEquipoDto modelosEquipoDto = new ModelosEquipoDto();

        modelosEquipoDto.setId( modelosEquipo.getId() );
        modelosEquipoDto.setNombre( modelosEquipo.getNombre() );
        modelosEquipoDto.setIdMarca( marcasModeloToMarcasModeloDto( modelosEquipo.getIdMarca() ) );

        return modelosEquipoDto;
    }

    @Override
    public ModelosEquipo partialUpdate(ModelosEquipoDto modelosEquipoDto, ModelosEquipo modelosEquipo) {
        if ( modelosEquipoDto == null ) {
            return modelosEquipo;
        }

        if ( modelosEquipoDto.getId() != null ) {
            modelosEquipo.setId( modelosEquipoDto.getId() );
        }
        if ( modelosEquipoDto.getIdMarca() != null ) {
            if ( modelosEquipo.getIdMarca() == null ) {
                modelosEquipo.setIdMarca( new MarcasModelo() );
            }
            marcasModeloDtoToMarcasModelo1( modelosEquipoDto.getIdMarca(), modelosEquipo.getIdMarca() );
        }
        if ( modelosEquipoDto.getNombre() != null ) {
            modelosEquipo.setNombre( modelosEquipoDto.getNombre() );
        }

        return modelosEquipo;
    }

    @Override
    public List<ModelosEquipo> toEntity(List<ModelosEquipoDto> modelosEquipoDto) {
        if ( modelosEquipoDto == null ) {
            return null;
        }

        List<ModelosEquipo> list = new ArrayList<ModelosEquipo>( modelosEquipoDto.size() );
        for ( ModelosEquipoDto modelosEquipoDto1 : modelosEquipoDto ) {
            list.add( toEntity( modelosEquipoDto1 ) );
        }

        return list;
    }

    @Override
    public List<ModelosEquipoDto> toDto(List<ModelosEquipo> modelosEquipo) {
        if ( modelosEquipo == null ) {
            return null;
        }

        List<ModelosEquipoDto> list = new ArrayList<ModelosEquipoDto>( modelosEquipo.size() );
        for ( ModelosEquipo modelosEquipo1 : modelosEquipo ) {
            list.add( toDto( modelosEquipo1 ) );
        }

        return list;
    }

    protected MarcasModelo marcasModeloDtoToMarcasModelo(MarcasModeloDto marcasModeloDto) {
        if ( marcasModeloDto == null ) {
            return null;
        }

        MarcasModelo marcasModelo = new MarcasModelo();

        marcasModelo.setId( marcasModeloDto.getId() );
        marcasModelo.setNombre( marcasModeloDto.getNombre() );

        return marcasModelo;
    }

    protected MarcasModeloDto marcasModeloToMarcasModeloDto(MarcasModelo marcasModelo) {
        if ( marcasModelo == null ) {
            return null;
        }

        MarcasModeloDto marcasModeloDto = new MarcasModeloDto();

        marcasModeloDto.setId( marcasModelo.getId() );
        marcasModeloDto.setNombre( marcasModelo.getNombre() );

        return marcasModeloDto;
    }

    protected void marcasModeloDtoToMarcasModelo1(MarcasModeloDto marcasModeloDto, MarcasModelo mappingTarget) {
        if ( marcasModeloDto == null ) {
            return;
        }

        if ( marcasModeloDto.getId() != null ) {
            mappingTarget.setId( marcasModeloDto.getId() );
        }
        if ( marcasModeloDto.getNombre() != null ) {
            mappingTarget.setNombre( marcasModeloDto.getNombre() );
        }
    }
}
