package entidades;

public class Usuario {
    private final int idUsuario;
    private String nombre;
    private String correoElectronico;

    public Usuario(int idUsuario, String nombre, String correoElectronico){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
    }
}
