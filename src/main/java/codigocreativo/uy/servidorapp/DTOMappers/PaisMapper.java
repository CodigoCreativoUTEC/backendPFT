package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.PaisDto;
import codigocreativo.uy.servidorapp.entidades.Pais;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface PaisMapper {
    Pais toEntity(PaisDto paisDto);

    PaisDto toDto(Pais pais);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Pais partialUpdate(PaisDto paisDto, @MappingTarget Pais pais);

    List<Pais> toEntity(List<PaisDto> paisDto);

    List<PaisDto> toDto(List<Pais> pais);
}