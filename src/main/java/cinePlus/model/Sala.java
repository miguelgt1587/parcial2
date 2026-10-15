package cinePlus.model;

public class Sala {

    private int codigoSala;
    private String nombreSala;
    private int capacidad;
    private char fila;
    private int columna;

    public Sala(int codigoSala, String nombreSala, int capacidad, char fila, int columna) {
        this.codigoSala=codigoSala;
        this.nombreSala=nombreSala;
        this.capacidad=capacidad;
        this.fila=fila;
        this.columna=columna;
    }

    public int getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(int codigoSala) {
        this.codigoSala = codigoSala;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public char getFila() {
        return fila;
    }

    public void setFila(char fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
