package co.ucc.apipedidos.service;

import co.ucc.apipedidos.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface IClienteService {

    List<Cliente> listar();

    Cliente guardar(Cliente cliente);

    Optional<Cliente> buscarPorId(Long id);

    List<Cliente> buscarPorNombre(String nombre);

    void eliminar(Long id);
}
