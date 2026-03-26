package co.ucc.apipedidos.controller;

import co.ucc.apipedidos.model.Envio;
import co.ucc.apipedidos.model.TipoEnvio;
import co.ucc.apipedidos.service.IEnvioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private final IEnvioService envioService;

    public EnvioController(IEnvioService envioService) {
        this.envioService = envioService;
    }

    @GetMapping
    public List<Envio> listar() {
        return envioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> buscarPorId(@PathVariable Long id) {
        return envioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pedido/{pedidoId}")
    public List<Envio> listarPorPedido(@PathVariable Long pedidoId) {
        return envioService.listarPorPedido(pedidoId);
    }

    @PostMapping
    public ResponseEntity<?> crearEnvio(@RequestBody Map<String, Object> body) {
        try {
            Long pedidoId = Long.valueOf(body.get("pedidoId").toString());
            TipoEnvio tipo = TipoEnvio.valueOf((String) body.get("tipoEnvio"));
            double pesoKg = ((Number) body.get("pesoKg")).doubleValue();
            String direccion = (String) body.get("direccion");
            return ResponseEntity.ok(envioService.crearEnvio(pedidoId, tipo, pesoKg, direccion));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id,
                                               @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(envioService.actualizarEstado(id, body.get("estado")));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/cotizar")
    public ResponseEntity<?> cotizar(@RequestParam TipoEnvio tipoEnvio,
                                      @RequestParam double pesoKg) {
        try {
            double costo = envioService.calcularCostoEnvio(tipoEnvio, pesoKg);
            return ResponseEntity.ok(Map.of(
                    "tipoEnvio", tipoEnvio.name(),
                    "descripcion", tipoEnvio.getDescripcion(),
                    "pesoKg", pesoKg,
                    "costoEnvio", costo
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/tarifas")
    public List<Map<String, Object>> tarifas() {
        return List.of(
                Map.of("tipo", "ESTANDAR",     "descripcion", TipoEnvio.ESTANDAR.getDescripcion(),     "tarifa", "$5.000 por kg"),
                Map.of("tipo", "EXPRES",        "descripcion", TipoEnvio.EXPRES.getDescripcion(),        "tarifa", "$8.000 por kg"),
                Map.of("tipo", "INTERNACIONAL", "descripcion", TipoEnvio.INTERNACIONAL.getDescripcion(), "tarifa", "$15.000 costo fijo")
        );
    }
}
