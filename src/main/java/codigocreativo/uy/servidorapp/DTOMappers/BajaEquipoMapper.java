package codigocreativo.uy.servidorapp.DTOMappers;

import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import codigocreativo.uy.servidorapp.DTO.BajaEquipoDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface BajaEquipoMapper {
    BajaEquipo toEntity(BajaEquipoDto bajaEquipoDto, @Context CycleAvoidingMappingContext context);

    BajaEquipoDto toDto(BajaEquipo bajaEquipo, @Context CycleAvoidingMappingContext context);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BajaEquipo partialUpdate(BajaEquipoDto bajaEquipoDto, @MappingTarget BajaEquipo bajaEquipo);

    List<BajaEquipo> toEntity(List<BajaEquipoDto> bajaEquipoDto, @Context CycleAvoidingMappingContext context);

    List<BajaEquipoDto> toDto(List<BajaEquipo> bajaEquipo, @Context CycleAvoidingMappingContext context);
}