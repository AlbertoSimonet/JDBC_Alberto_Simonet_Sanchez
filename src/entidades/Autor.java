package entidades;

public class Autor {
    private final int id;
    private String nombre;
    private String nacionalidad;

    public Autor(int id, String nombre, String nacionalidad){
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }
}
