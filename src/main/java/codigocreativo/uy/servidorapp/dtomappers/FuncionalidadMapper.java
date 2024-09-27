package codigocreativo.uy.servidorapp.dtomappers;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.entidades.Funcionalidad;
import org.mapstruct.*;

import jakarta.inject.Inject;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public abstract class FuncionalidadMapper {

    @Inject
    private PerfilMapper perfilMapper;

    // Método principal para convertir FuncionalidadDto a Funcionalidad (Dto -> Entity)
    public abstract Funcionalidad toEntity(FuncionalidadDto funcionalidadDto, @Context CycleAvoidingMappingContext context);

    // Método principal para convertir Funcionalidad a FuncionalidadDto (Entity -> Dto)
    public abstract FuncionalidadDto toDto(Funcionalidad funcionalidad, @Context CycleAvoidingMappingContext context);

    // Método para manejar actualizaciones parciales
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Funcionalidad partialUpdate(FuncionalidadDto funcionalidadDto, @MappingTarget Funcionalidad funcionalidad);

    // Métodos para manejar listas
    public abstract List<Funcionalidad> toEntity(List<FuncionalidadDto> funcionalidadDtos, @Context CycleAvoidingMappingContext context);
    public abstract List<FuncionalidadDto> toDto(List<Funcionalidad> funcionalidades, @Context CycleAvoidingMappingContext context);

}
