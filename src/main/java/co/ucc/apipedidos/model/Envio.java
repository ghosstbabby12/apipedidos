package co.ucc.apipedidos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    private TipoEnvio tipoEnvio;

    private double pesoKg;
    private double costoEnvio;
    private String direccionDestino;
    private LocalDateTime fechaEnvio;
    private String estadoEnvio;

    // package-private: accesible en el paquete
    String numeroRastreo;

    public Envio() {}

    public Envio(Long pedidoId, TipoEnvio tipoEnvio, double pesoKg, String direccionDestino) {
        this.pedidoId = pedidoId;
        this.tipoEnvio = tipoEnvio;
        this.pesoKg = pesoKg;
        this.direccionDestino = direccionDestino;
        this.fechaEnvio = LocalDateTime.now();
        this.estadoEnvio = "EN_CAMINO";
        this.costoEnvio = tipoEnvio.calcularCosto(pesoKg);
    }

    @PostPersist
    public void generarNumeroRastreo() {
        this.numeroRastreo = "DR-" + tipoEnvio.name().charAt(0) + String.format("%08d", id);
    }

    protected String generarNumeroRastreoManual(Long id, TipoEnvio tipo) {
        return "DR-" + tipo.name().charAt(0) + String.format("%08d", id);
    }

    private double calcularCosto() {
        return tipoEnvio.calcularCosto(pesoKg);
    }

    public void actualizarPeso(double nuevoPesoKg) {
        this.pesoKg = nuevoPesoKg;
        this.costoEnvio = calcularCosto();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public TipoEnvio getTipoEnvio() { return tipoEnvio; }
    public void setTipoEnvio(TipoEnvio tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
        this.costoEnvio = calcularCosto();
    }

    public double getPesoKg() { return pesoKg; }

    public double getCostoEnvio() { return costoEnvio; }

    public String getDireccionDestino() { return direccionDestino; }
    public void setDireccionDestino(String direccionDestino) { this.direccionDestino = direccionDestino; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }

    public String getEstadoEnvio() { return estadoEnvio; }
    public void setEstadoEnvio(String estadoEnvio) { this.estadoEnvio = estadoEnvio; }

    public String getNumeroRastreo() { return numeroRastreo; }
    public void setNumeroRastreo(String numeroRastreo) { this.numeroRastreo = numeroRastreo; }

    public String construirNumeroRastreo() {
        return "DR-" + tipoEnvio.name().charAt(0) + String.format("%08d", id);
    }
}
