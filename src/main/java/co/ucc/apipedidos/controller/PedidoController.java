package co.ucc.apipedidos.controller;

import co.ucc.apipedidos.model.EstadoPedido;
import co.ucc.apipedidos.model.Pedido;
import co.ucc.apipedidos.service.IPedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final IPedidoService pedidoService;

    public PedidoController(IPedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Pedido> buscarPorCliente(@PathVariable Long clienteId) {
        return pedidoService.buscarPorCliente(clienteId);
    }

    @PostMapping
    public Pedido crear(@RequestBody Map<String, Long> body) {
        return pedidoService.crear(body.get("clienteId"));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Pedido> agregarItem(@PathVariable Long id,
                                               @RequestBody Map<String, Object> body) {
        Long productoId = Long.valueOf(body.get("productoId").toString());
        int cantidad = Integer.parseInt(body.get("cantidad").toString());
        return ResponseEntity.ok(pedidoService.agregarItem(id, productoId, cantidad));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> cambiarEstado(@PathVariable Long id,
                                                 @RequestBody Map<String, String> body) {
        EstadoPedido estado = EstadoPedido.valueOf(body.get("estado"));
        return ResponseEntity.ok(pedidoService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
