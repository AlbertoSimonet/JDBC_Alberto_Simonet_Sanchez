package entidades;
import java.util.Date;

public class Prestamo {
    private int prestamoID;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private int prestamoUsuario;
    private int prestamoLibro;
    public Prestamo(int prestamoID, Date fechaPrestamo, Date fechaDevolucion, int prestamoUsuario, int prestamoLibro){
        this.prestamoID = prestamoID;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.prestamoUsuario = prestamoUsuario;
        this.prestamoLibro = prestamoLibro;
    }

    public int getPrestamoID() { return prestamoID;  }

    public Date getFechaPrestamo() { return fechaPrestamo; }

    public Date getFechaDevolucion() { return fechaDevolucion; }

    public int getPrestamoUsuario() { return prestamoUsuario; }

    public int getPrestamoLibro() { return prestamoLibro; }
}
