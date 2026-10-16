package cinePlus.model;

/**
 * Sala 3D - aplica un recargo del 20% sobre el precio base de la función.
 */
public class Sala3D extends Sala {

    public static final double RECARGO = 0.20; // 20%

    public Sala3D(int codigoSala, String nombreSala, int capacidad, char fila, int columna) {
        super(codigoSala, nombreSala, capacidad, fila, columna);
    }

    /**
     * Calcula el precio de la boleta aplicando el recargo 3D.
     */
    public double calcularPrecio(double precioBase) {
        return precioBase * (1 + RECARGO);
    }

    @Override
    public String toString() {
        return "Sala3D{" +
                "codigo=" + getCodigoSala() +
                ", nombre='" + getNombreSala() + '\'' +
                ", capacidad=" + getCapacidad() +
                '}';
    }
}
