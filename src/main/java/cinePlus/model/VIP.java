package cinePlus.model;

/**
 * Sala VIP - aplica un recargo del 30% sobre el precio base de la función.
 */
public class VIP extends Sala {

    public static final double RECARGO = 0.30; // 30%

    public VIP(int codigoSala, String nombreSala, int capacidad, char fila, int columna) {
        super(codigoSala, nombreSala, capacidad, fila, columna);
    }

    /**
     * Calcula el precio de la boleta aplicando el recargo VIP.
     */
    public double calcularPrecio(double precioBase) {
        return precioBase * (1 + RECARGO);
    }

    @Override
    public String toString() {
        return "VIP{" +
                "codigo=" + getCodigoSala() +
                ", nombre='" + getNombreSala() + '\'' +
                ", capacidad=" + getCapacidad() +
                '}';
    }
}
