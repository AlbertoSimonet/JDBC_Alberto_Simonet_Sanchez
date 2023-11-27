package entidades;
import java.time.LocalDate;

public class Prestamo {
    private Integer ID;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private int usuarioID;
    private int libroID;
    public Prestamo(Integer prestamoID, LocalDate fechaPrestamo, LocalDate fechaDevolucion, int prestamoUsuario, int prestamoLibro){
        this.ID = prestamoID;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.usuarioID = prestamoUsuario;
        this.libroID = prestamoLibro;
    }

    public int getID() { return this.ID; }

    public LocalDate getFechaPrestamo() { return this.fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return this.fechaDevolucion; }

    public int getUsuarioID() { return usuarioID; }

    public int getLibroID() { return libroID; }

    @Override
    public String toString() {
        return  "ID Prestamo:" + ID +
                ", Fecha del prestamo:" + fechaPrestamo +
                ", Fecha de Devolucion:" + fechaDevolucion +
                ", Usuario:" + usuarioID +
                ", Libro:" + libroID +
                '}';
    }
}