package co.ucc.apipedidos.service;

import co.ucc.apipedidos.model.Envio;
import co.ucc.apipedidos.model.TipoEnvio;
import co.ucc.apipedidos.repository.EnvioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EnvioService implements IEnvioService {

    private final EnvioRepository envioRepository;

    public EnvioService(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    @Override
    public List<Envio> listar() {
        return envioRepository.findAll();
    }

    @Override
    public List<Envio> listarPorPedido(Long pedidoId) {
        return envioRepository.findByPedidoId(pedidoId);
    }

    @Override
    public Envio crearEnvio(Long pedidoId, TipoEnvio tipoEnvio, double pesoKg, String direccion) {
        validarPeso(pesoKg);
        Envio envio = new Envio(pedidoId, tipoEnvio, pesoKg, direccion);
        Envio guardado = envioRepository.save(envio);
        guardado.setNumeroRastreo(guardado.construirNumeroRastreo());
        return envioRepository.save(guardado);
    }

    @Override
    public Optional<Envio> buscarPorId(Long id) {
        return envioRepository.findById(id);
    }

    @Override
    public Envio actualizarEstado(Long id, String nuevoEstado) {
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado con id: " + id));
        envio.setEstadoEnvio(nuevoEstado);
        return envioRepository.save(envio);
    }

    @Override
    public double calcularCostoEnvio(TipoEnvio tipoEnvio, double pesoKg) {
        validarPeso(pesoKg);
        return tipoEnvio.calcularCosto(pesoKg);
    }

    private void validarPeso(double pesoKg) {
        if (pesoKg <= 0) throw new IllegalArgumentException("El peso debe ser mayor a cero");
    }
}
