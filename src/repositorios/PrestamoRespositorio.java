package repositorios;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PrestamoRespositorio implements Repositorio{
    @Override
    public void createTable(Connection connection) throws SQLException {

        String crearTablaPrestamo = "CREATE TABLE Prestamo (" +
                "IDPrestamo INT PRIMARY KEY," +
                "FechaPrestamo DATE," +
                "FechaDevolucion DATE," +
                "IDUsuario INT," +
                "IDLibro INT," +
                "FOREIGN KEY (IDUsuario) REFERENCES Usuario(ID)," +
                "FOREIGN KEY (IDLibro) REFERENCES Libro(ID)" +
                ")";
        Statement sentencia = JdbcManager.createStatement(connection);
        sentencia.executeUpdate(crearTablaPrestamo);

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
