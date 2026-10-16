package cinePlus.model;

public class Boleta {

    private String codigoBoleta;
    private char fila;
    private int numeroAsiento;
    private double valorPagado;
    private Funcion funcion;

    public Boleta(String codigoBoleta, char fila, int numeroAsiento,
                  double valorPagado, Funcion funcion) {
        this.codigoBoleta = codigoBoleta;
        this.fila = fila;
        this.numeroAsiento = numeroAsiento;
        this.valorPagado = valorPagado;
        this.funcion = funcion;
    }

    public String getCodigoBoleta() {
        return codigoBoleta;
    }

    public void setCodigoBoleta(String codigoBoleta) {
        this.codigoBoleta = codigoBoleta;
    }

    public char getFila() {
        return fila;
    }

    public void setFila(char fila) {
        this.fila = fila;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(int numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    @Override
    public String toString() {
        return "Boleta{" +
                "codigo='" + codigoBoleta + '\'' +
                ", asiento=" + fila + numeroAsiento +
                ", valorPagado=" + valorPagado +
                ", funcion=" + funcion.getCodigoFuncion() +
                '}';
    }
}


