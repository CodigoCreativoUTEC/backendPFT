package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.DTO.PerfilDto;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface PerfilMapper {
    Perfil toEntity(PerfilDto perfilDto);

    PerfilDto toDto(Perfil perfil);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Perfil partialUpdate(PerfilDto perfilDto, @MappingTarget Perfil perfil);

    List<Perfil> toEntity(List<PerfilDto> perfilDto);

    List<PerfilDto> toDto(List<Perfil> perfil);
}