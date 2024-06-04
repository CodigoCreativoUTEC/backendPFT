package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.PerfilesPermisoIdDto;
import codigocreativo.uy.servidorapp.entidades.PerfilesPermisoId;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface PerfilesPermisoIdMapper {
    PerfilesPermisoId toEntity(PerfilesPermisoIdDto perfilesPermisoIdDto);

    PerfilesPermisoIdDto toDto(PerfilesPermisoId perfilesPermisoId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PerfilesPermisoId partialUpdate(PerfilesPermisoIdDto perfilesPermisoIdDto, @MappingTarget PerfilesPermisoId perfilesPermisoId);

    List<PerfilesPermisoId> toEntity(List<PerfilesPermisoIdDto> perfilesPermisoIdDto);

    List<PerfilesPermisoIdDto> toDto(List<PerfilesPermisoId> perfilesPermisoId);
}