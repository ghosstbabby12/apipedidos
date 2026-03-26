package co.ucc.apipedidos.service;

import co.ucc.apipedidos.model.*;
import co.ucc.apipedidos.repository.ClienteRepository;
import co.ucc.apipedidos.repository.ItemPedidoRepository;
import co.ucc.apipedidos.repository.PedidoRepository;
import co.ucc.apipedidos.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService implements IPedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         ProductoRepository productoRepository,
                         ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @Override
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido crear(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + clienteId));
        Pedido pedido = new Pedido(cliente);
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido agregarItem(Long pedidoId, Long productoId, int cantidad) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + pedidoId));
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + productoId));
        ItemPedido item = new ItemPedido(producto, cantidad, pedido);
        itemPedidoRepository.save(item);
        pedido.agregarItem(item);
        pedido.setTotal(pedido.calcularSubtotal());
        return pedidoRepository.save(pedido);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public List<Pedido> buscarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Pedido> buscarPorTotalMayorA(double total) {
        return pedidoRepository.findByTotalGreaterThan(total);
    }

    @Override
    public Pedido cambiarEstado(Long pedidoId, EstadoPedido estado) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + pedidoId));
        pedido.cambiarEstado(estado);
        return pedidoRepository.save(pedido);
    }

    @Override
    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
