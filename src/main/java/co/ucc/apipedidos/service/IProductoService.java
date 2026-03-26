package co.ucc.apipedidos.service;

import co.ucc.apipedidos.model.Producto;
import java.util.List;
import java.util.Optional;

public interface IProductoService {

    List<Producto> listar();

    Producto guardar(Producto producto);

    Optional<Producto> buscarPorId(Long id);

    List<Producto> buscarPorNombre(String nombre);

    void eliminar(Long id);
}
