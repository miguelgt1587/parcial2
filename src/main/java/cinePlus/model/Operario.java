package cinePlus.model;

import java.time.LocalDate;

/**
 * Operario del cine. Hereda de Usuario y agrega código de empleado, fecha de contratación y salario.
 */
public class Operario extends Usuario {

    private String codigoEmpleado;
    private LocalDate fechaContratacion;
    private double salarioBasico;

    public Operario(int documento, String nombre, String correo, int telefono,
                    String codigoEmpleado, LocalDate fechaContratacion, double salarioBasico) {
        super(documento, nombre, correo, telefono);
        this.codigoEmpleado = codigoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.salarioBasico = salarioBasico;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public double getSalarioBasico() {
        return salarioBasico;
    }

    public void setSalarioBasico(double salarioBasico) {
        this.salarioBasico = salarioBasico;
    }

    @Override
    public String toString() {
        return "Operario{" +
                "documento=" + getDocumento() +
                ", nombre='" + getNombre() + '\'' +
                ", codigoEmpleado='" + codigoEmpleado + '\'' +
                ", fechaContratacion=" + fechaContratacion +
                ", salarioBasico=" + salarioBasico +
                '}';
    }
}
