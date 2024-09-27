package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;

import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface FuncionalidadRemote {

    List<FuncionalidadDto> obtenerTodas();

    FuncionalidadDto crear(FuncionalidadDto funcionalidad);

    FuncionalidadDto actualizar(FuncionalidadDto funcionalidad);

    void eliminar(Long id);

    FuncionalidadDto buscarPorId(Long id);

}
