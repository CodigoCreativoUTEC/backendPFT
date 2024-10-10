package codigocreativo.uy.servidorapp.dtomappers;

import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.entidades.Perfil;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.CDI, uses = {PermisoMapper.class})
public interface PerfilMapper {
    Perfil toEntity(PerfilDto perfilDto, @Context CycleAvoidingMappingContext context);

    @AfterMapping
    default void linkUsuarios(@MappingTarget Perfil perfil) {
        perfil.getUsuarios().forEach(usuario -> usuario.setIdPerfil(perfil));
    }

    PerfilDto toDto(Perfil perfil, @Context CycleAvoidingMappingContext context);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Perfil partialUpdate(PerfilDto perfilDto, @MappingTarget Perfil perfil);

    List<Perfil> toEntity(List<PerfilDto> perfilDto, @Context CycleAvoidingMappingContext context);

    List<PerfilDto> toDto(List<Perfil> perfil, @Context CycleAvoidingMappingContext context);
}