package repositorios;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UsuarioRepositorio implements Repositorio{
    @Override
    public void createTable(Connection connection) throws SQLException {

        String crearTablaUsuario = "CREATE TABLE Usuario (" +
                "ID INT PRIMARY KEY," +
                "Nombre VARCHAR(255)," +
                "CorreoElectronico VARCHAR(255)" +
                ")";
        Statement sentencia = JdbcManager.createStatement(connection);
        sentencia.executeUpdate(crearTablaUsuario);

    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Object findOneById(int id) {
        return null;
    }

    @Override
    public void save(Object o) {

    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void deleteById(int id) {

    }
}
