package codigocreativo.uy.servidorapp.dtomappers;

import codigocreativo.uy.servidorapp.entidades.Funcionalidad;
import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI, uses = {PerfilMapper.class})
public interface FuncionalidadMapper {
    Funcionalidad toEntity(FuncionalidadDto funcionalidadDto, @Context CycleAvoidingMappingContext context);

    FuncionalidadDto toDto(Funcionalidad funcionalidad, @Context CycleAvoidingMappingContext context);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Funcionalidad partialUpdate(FuncionalidadDto funcionalidadDto, @MappingTarget Funcionalidad funcionalidad);

    List<Funcionalidad> toEntity(List<FuncionalidadDto> funcionalidadDto, @Context CycleAvoidingMappingContext context);

    List<FuncionalidadDto> toDto(List<Funcionalidad> funcionalidad, @Context CycleAvoidingMappingContext context);
}