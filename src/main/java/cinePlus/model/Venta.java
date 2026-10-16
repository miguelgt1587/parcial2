package cinePlus.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una venta de boletas.
 * Relación: una Venta es registrada por un Operario, pertenece a un Cliente
 * y contiene una o varias Boletas (todas de la misma Función).
 */
public class Venta {

    private String codigoVenta;
    private LocalDate fechaVenta;
    private Operario operario;
    private Cliente cliente;
    private List<Boleta> boletas;

    public Venta(String codigoVenta, LocalDate fechaVenta, Operario operario, Cliente cliente) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
        this.operario = operario;
        this.cliente = cliente;
        this.boletas = new ArrayList<>();
    }

    public String getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(String codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Operario getOperario() {
        return operario;
    }

    public void setOperario(Operario operario) {
        this.operario = operario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Boleta> getBoletas() {
        return boletas;
    }

    public void agregarBoleta(Boleta boleta) {
        this.boletas.add(boleta);
    }

    /**
     * Calcula el valor total de la venta sumando el valor de todas las boletas.
     */
    public double calcularValorTotal() {
        double total = 0.0;
        for (Boleta b : boletas) {
            total += b.getValorPagado();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "codigo='" + codigoVenta + '\'' +
                ", fecha=" + fechaVenta +
                ", cliente=" + cliente.getNombre() +
                ", operario=" + operario.getNombre() +
                ", cantidadBoletas=" + boletas.size() +
                ", total=" + calcularValorTotal() +
                '}';
    }
}
