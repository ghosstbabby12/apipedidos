package co.ucc.apipedidos.service;

import co.ucc.apipedidos.model.Envio;
import co.ucc.apipedidos.model.TipoEnvio;
import java.util.List;
import java.util.Optional;

public interface IEnvioService {

    List<Envio> listar();

    List<Envio> listarPorPedido(Long pedidoId);

    Envio crearEnvio(Long pedidoId, TipoEnvio tipoEnvio, double pesoKg, String direccion);

    Optional<Envio> buscarPorId(Long id);

    Envio actualizarEstado(Long id, String nuevoEstado);

    double calcularCostoEnvio(TipoEnvio tipoEnvio, double pesoKg);
}
