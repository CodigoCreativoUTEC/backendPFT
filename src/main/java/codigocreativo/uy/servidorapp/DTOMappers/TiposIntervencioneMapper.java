package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.TiposIntervencioneDto;
import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface TiposIntervencioneMapper {
    TiposIntervencione toEntity(TiposIntervencioneDto tiposIntervencioneDto);

    TiposIntervencioneDto toDto(TiposIntervencione tiposIntervencione);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TiposIntervencione partialUpdate(TiposIntervencioneDto tiposIntervencioneDto, @MappingTarget TiposIntervencione tiposIntervencione);

    List<TiposIntervencione> toEntity(List<TiposIntervencioneDto> tiposIntervencioneDto);

    List<TiposIntervencioneDto> toDto(List<TiposIntervencione> tiposIntervencione);
}