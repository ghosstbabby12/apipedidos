package co.ucc.apipedidos.service;

import co.ucc.apipedidos.model.EstadoTransaccion;
import co.ucc.apipedidos.model.Transaccion;
import co.ucc.apipedidos.model.TipoTransaccion;
import co.ucc.apipedidos.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TransaccionService implements ITransaccionService {

    private final TransaccionRepository transaccionRepository;

    public TransaccionService(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    @Override
    public List<Transaccion> listar() {
        return transaccionRepository.findAll();
    }

    @Override
    public List<Transaccion> listarPorPedido(Long pedidoId) {
        return transaccionRepository.findByPedidoId(pedidoId);
    }

    @Override
    public Transaccion registrarPago(Long pedidoId, double monto) {
        return crearTransaccion(pedidoId, TipoTransaccion.PAGO, monto);
    }

    @Override
    public Transaccion registrarDevolucion(Long pedidoId, double monto) {
        boolean tienePago = listarPorPedido(pedidoId).stream()
                .anyMatch(t -> TipoTransaccion.PAGO.equals(t.getTipo())
                        && EstadoTransaccion.APROBADA.equals(t.getEstado()));

        if (!tienePago) {
            throw new IllegalStateException(
                    "No existe un pago aprobado para el pedido " + pedidoId);
        }
        return crearTransaccion(pedidoId, TipoTransaccion.DEVOLUCION, monto);
    }

    @Override
    public Optional<Transaccion> buscarPorId(Long id) {
        return transaccionRepository.findById(id);
    }

    // privado: lógica interna de creación
    private Transaccion crearTransaccion(Long pedidoId, TipoTransaccion tipo, double monto) {
        Transaccion transaccion = new Transaccion(pedidoId, tipo, monto);
        transaccion.setEstado(EstadoTransaccion.APROBADA);
        Transaccion guardada = transaccionRepository.save(transaccion);
        guardada.setReferencia(guardada.construirReferencia());
        return transaccionRepository.save(guardada);
    }
}
