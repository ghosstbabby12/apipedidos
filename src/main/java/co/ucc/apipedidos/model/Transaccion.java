package co.ucc.apipedidos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipo;

    private double monto;
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private EstadoTransaccion estado;

    // package-private: accesible dentro del paquete model
    String referencia;

    public Transaccion() {}

    public Transaccion(Long pedidoId, TipoTransaccion tipo, double monto) {
        this.pedidoId = pedidoId;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
        this.estado = EstadoTransaccion.PENDIENTE;
    }

    @PostPersist
    public void generarReferencia() {
        this.referencia = tipo.name().charAt(0) + "-" + String.format("%06d", id);
    }

    protected String generarReferenciaManual(Long id, TipoTransaccion tipo) {
        return tipo.name().charAt(0) + "-" + String.format("%06d", id);
    }

    private boolean montoValido(double monto) {
        return monto > 0;
    }

    public boolean esDevolucion() {
        return TipoTransaccion.DEVOLUCION.equals(this.tipo);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public TipoTransaccion getTipo() { return tipo; }
    public void setTipo(TipoTransaccion tipo) { this.tipo = tipo; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) {
        if (!montoValido(monto)) throw new IllegalArgumentException("El monto debe ser mayor a cero");
        this.monto = monto;
    }

    public LocalDateTime getFecha() { return fecha; }

    public EstadoTransaccion getEstado() { return estado; }
    public void setEstado(EstadoTransaccion estado) { this.estado = estado; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String construirReferencia() {
        return tipo.name().charAt(0) + "-" + String.format("%06d", id);
    }
}
