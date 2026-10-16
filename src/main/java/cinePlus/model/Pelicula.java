package cinePlus.model;

import java.util.ArrayList;
import java.util.List;

public class Pelicula {

    private int codigoPelicula;
    private String titulo;
    private int duracion;
    private TipoGenero tipoGenero;
    private String clasificacionEdad;
    private List<Sala> Listsalas;
    private Funcion funcion;

public Pelicula(int codigoPelicula, String titulo, int duracion, TipoGenero tipoGenero, String clasificacionEdad){

    this.codigoPelicula=codigoPelicula;
    this.titulo=titulo;
    this.duracion=duracion;
    this.tipoGenero=tipoGenero;
    this.clasificacionEdad=clasificacionEdad;
    this.Listsalas=new ArrayList<>();
    this.funcion=funcion;
}

    public int getCodigoPelicula() {
        return codigoPelicula;
    }

    public void setCodigoPelicula(int codigoPelicula) {
        this.codigoPelicula = codigoPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public TipoGenero getTipoGenero() {
        return tipoGenero;
    }

    public void setTipoGenero(TipoGenero tipoGenero) {
        this.tipoGenero = tipoGenero;
    }

    public String getClasificacionEdad() {
        return clasificacionEdad;
    }

    public void setClasificacionEdad(String clasificacionEdad) {
        this.clasificacionEdad = clasificacionEdad;
    }
}
