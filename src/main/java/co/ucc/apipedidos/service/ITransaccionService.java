package co.ucc.apipedidos.service;

import co.ucc.apipedidos.model.Transaccion;
import java.util.List;
import java.util.Optional;

public interface ITransaccionService {

    List<Transaccion> listar();

    List<Transaccion> listarPorPedido(Long pedidoId);

    Transaccion registrarPago(Long pedidoId, double monto);

    Transaccion registrarDevolucion(Long pedidoId, double monto);

    Optional<Transaccion> buscarPorId(Long id);
}
