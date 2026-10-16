package cinePlus.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Representa una función cinematográfica.
 * Relación: una Función pertenece a una Película y se proyecta en una Sala.
 */
public class Funcion {

    private String codigoFuncion;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private double precioBase;
    private Pelicula pelicula;
    private Sala sala;

    // Control de asientos ocupados: "fila-numero" ejemplo "A-5"
    private Set<String> asientosOcupados;
    private List<Boleta> boletasVendidas;

    public Funcion(String codigoFuncion, LocalDate fecha, LocalTime horaInicio,
                   double precioBase, Pelicula pelicula, Sala sala) {
        this.codigoFuncion = codigoFuncion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.precioBase = precioBase;
        this.pelicula = pelicula;
        this.sala = sala;
        this.asientosOcupados = new HashSet<>();
        this.boletasVendidas = new ArrayList<>();
    }

    public String getCodigoFuncion() {
        return codigoFuncion;
    }

    public void setCodigoFuncion(String codigoFuncion) {
        this.codigoFuncion = codigoFuncion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Set<String> getAsientosOcupados() {
        return asientosOcupados;
    }

    public List<Boleta> getBoletasVendidas() {
        return boletasVendidas;
    }

    /**
     * Calcula la hora de finalización de la función según la duración de la película.
     */
    public LocalTime getHoraFin() {
        return horaInicio.plusMinutes(pelicula.getDuracion());
    }

    /**
     * Verifica si un asiento está disponible.
     */
    public boolean estaAsientoDisponible(char fila, int numeroAsiento) {
        String clave = fila + "-" + numeroAsiento;
        return !asientosOcupados.contains(clave);
    }

    /**
     * Marca un asiento como ocupado.
     */
    public void ocuparAsiento(char fila, int numeroAsiento) {
        String clave = fila + "-" + numeroAsiento;
        asientosOcupados.add(clave);
    }

    /**
     * Calcula el porcentaje de ocupación de la función.
     */
    public double calcularPorcentajeOcupacion() {
        if (sala.getCapacidad() == 0) return 0.0;
        return (boletasVendidas.size() * 100.0) / sala.getCapacidad();
    }

    /**
     * Calcula el precio de una boleta según el tipo de sala.
     */
    public double calcularPrecioBoleta() {
        if (sala instanceof VIP) {
            return ((VIP) sala).calcularPrecio(precioBase);
        } else if (sala instanceof Sala3D) {
            return ((Sala3D) sala).calcularPrecio(precioBase);
        } else {
            // Tradicional: precio base sin recargo
            return precioBase;
        }
    }

    @Override
    public String toString() {
        return "Funcion{" +
                "codigo='" + codigoFuncion + '\'' +
                ", fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", pelicula=" + pelicula.getTitulo() +
                ", sala=" + sala.getNombreSala() +
                ", precioBase=" + precioBase +
                '}';
    }
}
