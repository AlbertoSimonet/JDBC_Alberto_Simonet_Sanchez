package repositorios;

import entidades.Autor;
import entidades.Prestamo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PrestamoRespositorio implements Repositorio{

    private Connection connection;

    public PrestamoRespositorio(Connection connection){ this.connection = connection; }
    @Override
    public void createTable() throws SQLException {

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
    public List findAll() throws SQLException {
        // Creamos la sentencia y creamos una lista de Autores para devolverla
        Statement sentencia = JdbcManager.createStatement(connection);
        ArrayList<Prestamo> listaPrestamos = new ArrayList<>();
        String query = "SELECT * FROM prestamo";

        try {
            ResultSet resultSet = sentencia.executeQuery(query);
            ResultSet result = sentencia.getResultSet();
            while (result.next()){
                Prestamo prestamo = new Prestamo(
                        resultSet.getInt("IDPrestamo"),
                        resultSet.getDate("FechaPrestamo"),
                        resultSet.getDate("FechaDevolucion"),
                        resultSet.getInt("IDUsuario"),
                        resultSet.getInt("IDLibro"));
                listaPrestamos.add(prestamo);
            }
        } catch (SQLException e) {
            System.out.println("Hubo un problema con la lista de Autores");
            throw new RuntimeException(e);
        }
        return listaPrestamos;
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
