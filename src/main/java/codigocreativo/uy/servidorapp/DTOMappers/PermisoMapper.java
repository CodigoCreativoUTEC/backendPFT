package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.PermisoDto;
import codigocreativo.uy.servidorapp.entidades.Permiso;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface PermisoMapper {
    Permiso toEntity(PermisoDto permisoDto);

    PermisoDto toDto(Permiso permiso);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Permiso partialUpdate(PermisoDto permisoDto, @MappingTarget Permiso permiso);

    List<Permiso> toEntity(List<PermisoDto> permisoDto);

    List<PermisoDto> toDto(List<Permiso> permiso);
}