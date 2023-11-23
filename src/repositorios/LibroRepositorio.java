package repositorios;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class LibroRepositorio implements Repositorio{
    @Override
    public void createTable(Connection connection) throws SQLException {

        String crearTablaLibro = "CREATE TABLE IF NOT EXISTS Libro (" +
                "ID INT PRIMARY KEY," +
                "Titulo VARCHAR(255)," +
                "Autor VARCHAR(255)," +
                "AnioPublicacion INT," +
                "CopiasDisponibles INT" +
                ")";
        Statement sentencia = JdbcManager.createStatement(connection);
        sentencia.executeUpdate(crearTablaLibro);
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
