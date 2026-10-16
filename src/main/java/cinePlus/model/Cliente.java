package cinePlus.model;

/**
 * Cliente del cine. Hereda de Usuario y agrega código de afiliación y descuento.
 */
public class Cliente extends Usuario {

    private String codigoAfiliacion;
    private double porcentajeDescuento; // ejemplo: 0.10 = 10%

    public Cliente(int documento, String nombre, String correo, int telefono,
                   String codigoAfiliacion, double porcentajeDescuento) {
        super(documento, nombre, correo, telefono);
        this.codigoAfiliacion = codigoAfiliacion;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getCodigoAfiliacion() {
        return codigoAfiliacion;
    }

    public void setCodigoAfiliacion(String codigoAfiliacion) {
        this.codigoAfiliacion = codigoAfiliacion;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    /**
     * Aplica el descuento del cliente sobre un valor ya calculado (con recargo de sala).
     */
    public double aplicarDescuento(double valorConRecargo) {
        return valorConRecargo * (1 - porcentajeDescuento);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "documento=" + getDocumento() +
                ", nombre='" + getNombre() + '\'' +
                ", codigoAfiliacion='" + codigoAfiliacion + '\'' +
                ", descuento=" + (porcentajeDescuento * 100) + "%" +
                '}';
    }
}
