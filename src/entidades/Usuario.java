package entidades;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correoElectronico;
    public Usuario(int idUsuario, String nombre, String correoElectronico){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }
}
