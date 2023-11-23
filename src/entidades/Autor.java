package entidades;

public class Autor {
    private int idAutor;
    private String nombre;
    private String nacionalidad;
    public Autor(int idAutor, String nombre, String nacionalidad){
        this.idAutor = idAutor;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }
}
