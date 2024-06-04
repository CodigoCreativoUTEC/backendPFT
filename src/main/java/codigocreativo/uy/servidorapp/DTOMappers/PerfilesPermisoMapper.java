package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.PerfilesPermisoDto;
import codigocreativo.uy.servidorapp.entidades.PerfilesPermiso;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, uses = {PerfilMapper.class})
public interface PerfilesPermisoMapper {
    PerfilesPermiso toEntity(PerfilesPermisoDto perfilesPermisoDto);

    PerfilesPermisoDto toDto(PerfilesPermiso perfilesPermiso);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PerfilesPermiso partialUpdate(PerfilesPermisoDto perfilesPermisoDto, @MappingTarget PerfilesPermiso perfilesPermiso);

    List<PerfilesPermiso> toEntity(List<PerfilesPermisoDto> perfilesPermisoDto);

    List<PerfilesPermisoDto> toDto(List<PerfilesPermiso> perfilesPermiso);
}