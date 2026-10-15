package cinePlus.model;

public class Usuario {

    private int documento;
    private String nombre;
    private String correo;
    private int telefono;

public Usuario(int documento, String nombre, String correo, int telefono){

    this.documento=documento;
    this.nombre=nombre;
    this.correo=correo;
    this.telefono=telefono;

}

public int getDocumento(){

    return documento;
}

public void setDocumento(int documeto){

    this.documento=documeto;
}

public String getNombre(){

    return nombre;
}

public void setNombre(String nombre){

    this.nombre=nombre;
}

public String getCorreo(){

    return correo;
}

public void setCorreo(String correo){

    this.correo=correo;
}

public int getTelefono(){

    return telefono;
}

public void setTelefono(int telefono){

    this.telefono=telefono;
}


}
