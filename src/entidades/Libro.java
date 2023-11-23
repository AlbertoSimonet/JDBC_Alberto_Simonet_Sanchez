package entidades;

public class Libro {
    private final int idLibro;
    private String titulo;
    private String autor;
    private int yearPublicacion;
    private int copiasDisponibles;

    public Libro(int idLibro, String titulo, String autor, int yearPublicacion, int copiasDisponibles){
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.yearPublicacion = yearPublicacion;
        this.copiasDisponibles = copiasDisponibles;
    }
}
