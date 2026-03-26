package co.ucc.apipedidos.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemPedido> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    private double total;

    public Pedido() {}

    public Pedido(Cliente cliente) {
        this.fecha = LocalDate.now();
        this.cliente = cliente;
        this.estado = EstadoPedido.PENDIENTE;
        this.total = 0.0;
    }

    public void agregarItem(ItemPedido item) {
        item.setPedido(this);
        items.add(item);
        recalcularTotal();
    }

    public void cambiarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public double calcularSubtotal() {
        return items.stream().mapToDouble(ItemPedido::calcularSubtotal).sum();
    }

    public double calcularTotal(double descuento, double impuesto) {
        double subtotal = calcularSubtotal();
        this.total = (subtotal - descuento) + impuesto;
        return this.total;
    }

    private void recalcularTotal() {
        this.total = calcularSubtotal();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<ItemPedido> getItems() { return items; }
    public void setItems(List<ItemPedido> items) { this.items = items; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
