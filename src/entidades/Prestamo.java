package entidades;

import java.util.Date;

public class Prestamo {
    private final int prestamoID;
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

}
