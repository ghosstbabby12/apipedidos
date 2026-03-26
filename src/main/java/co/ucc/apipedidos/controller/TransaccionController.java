package co.ucc.apipedidos.controller;

import co.ucc.apipedidos.model.Transaccion;
import co.ucc.apipedidos.service.ITransaccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final ITransaccionService transaccionService;

    public TransaccionController(ITransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping
    public List<Transaccion> listar() {
        return transaccionService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> buscarPorId(@PathVariable Long id) {
        return transaccionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pedido/{pedidoId}")
    public List<Transaccion> listarPorPedido(@PathVariable Long pedidoId) {
        return transaccionService.listarPorPedido(pedidoId);
    }

    @PostMapping("/pago")
    public ResponseEntity<Transaccion> registrarPago(@RequestBody Map<String, Object> body) {
        Long pedidoId = Long.valueOf(body.get("pedidoId").toString());
        double monto = ((Number) body.get("monto")).doubleValue();
        return ResponseEntity.ok(transaccionService.registrarPago(pedidoId, monto));
    }

    @PostMapping("/devolucion")
    public ResponseEntity<?> registrarDevolucion(@RequestBody Map<String, Object> body) {
        try {
            Long pedidoId = Long.valueOf(body.get("pedidoId").toString());
            double monto = ((Number) body.get("monto")).doubleValue();
            return ResponseEntity.ok(transaccionService.registrarDevolucion(pedidoId, monto));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
