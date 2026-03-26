package co.ucc.apipedidos.service;

import co.ucc.apipedidos.model.EstadoPedido;
import co.ucc.apipedidos.model.Pedido;
import java.util.List;
import java.util.Optional;

public interface IPedidoService {

    List<Pedido> listar();

    Pedido crear(Long clienteId);

    Pedido agregarItem(Long pedidoId, Long productoId, int cantidad);

    Optional<Pedido> buscarPorId(Long id);

    List<Pedido> buscarPorCliente(Long clienteId);

    List<Pedido> buscarPorTotalMayorA(double total);

    Pedido cambiarEstado(Long pedidoId, EstadoPedido estado);

    void eliminar(Long id);
}
