package entidades;
import java.util.Date;

public class Libro {
    private int idLibro;
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

    public int getIdLibro() { return idLibro; }

    public String getTitulo() { return titulo; }

    public String getAutor() { return autor; }

    public int getYearPublicacion() { return yearPublicacion; }

    public int getCopiasDisponibles() { return copiasDisponibles; }
}
