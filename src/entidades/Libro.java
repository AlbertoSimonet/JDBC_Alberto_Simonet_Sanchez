package entidades;
import java.util.Date;

public class Libro {
    private Integer idLibro;
    private String titulo;
    private int yearPublicacion;
    private int copiasDisponibles;
    private int idAutor;
    private String nombreAutor;
    public Libro(Integer idLibro, String titulo, int yearPublicacion, int copiasDisponibles, int idAutor){
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.yearPublicacion = yearPublicacion;
        this.copiasDisponibles = copiasDisponibles;
        this.idAutor = idAutor;
    }
    public Libro(Integer idLibro, String titulo, int idAutor, String nombreAutor){
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
    }

    public int getIdLibro() { return idLibro; }

    public String getTitulo() { return titulo; }

    public int getYearPublicacion() { return yearPublicacion; }

    public int getCopiasDisponibles() { return copiasDisponibles; }
    public int getIDAutor(){ return idAutor; }

    public int getIdAutor() {
        return idAutor;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "idLibro=" + idLibro +
                ", titulo='" + titulo + '\'' +
                ", autor='" + '\'' +
                ", yearPublicacion=" + yearPublicacion +
                ", copiasDisponibles=" + copiasDisponibles;
    }
}
