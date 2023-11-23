package repositorios;

import entidades.Autor;
import entidades.Prestamo;
import entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoRespositorio implements Repositorio {

    private Connection connection;

    public PrestamoRespositorio(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {

        String crearTablaPrestamo = "CREATE TABLE IF NOT EXISTS Prestamo (" +
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

        // Creamos la sentencia y creamos una lista de los Prestamos para devolverla
        Statement sentencia = JdbcManager.createStatement(connection);
        ArrayList<Prestamo> listaPrestamos = new ArrayList<>();
        String query = "SELECT * FROM prestamo";

        try {
            ResultSet resultSet = sentencia.executeQuery(query);
            ResultSet result = sentencia.getResultSet();
            while (result.next()) {
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
        Prestamo prestamoEncontrado = null;
        try {
            Statement sentencia = JdbcManager.createStatement(connection);
            String query = "SELECT * from prestamo WHERE IDPrestamo = " + id;

            ResultSet resultSet = sentencia.executeQuery(query);
            ResultSet result = sentencia.getResultSet();
            while (result.next()) {
                prestamoEncontrado = new Prestamo(
                        resultSet.getInt("IDPrestamo"),
                        resultSet.getDate("FechaPrestamo"),
                        resultSet.getDate("FechaDevolucion"),
                        resultSet.getInt("IDUsuario"),
                        resultSet.getInt("IDLibro"));
            }
        } catch (SQLException e) {
            System.out.println("Hubo un problema con el ResultSet de la sentencia SQL");
        }
        return prestamoEncontrado;
    }

    @Override
    public void save(Object o) {
        String insertSQL = "INSERT INTO prestamo (IDPrestamo, FechaPrestamo, FechaDevolucion, IDUsuario, IDLibro) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {

            Prestamo prestamo = (Prestamo) o;

            preparedStatement.setInt(1, prestamo.getPrestamoID());
            preparedStatement.setDate(2, prestamo.getFechaPrestamo());
            preparedStatement.setDate(3, prestamo.getFechaDevolucion());
            preparedStatement.setInt(4, prestamo.getPrestamoUsuario());
            preparedStatement.setInt(5, prestamo.getPrestamoLibro());

            preparedStatement.executeUpdate();

            System.out.println("Prestamo insertado exitosamente");
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al tratar de insertar el Prestamo");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object o) {
        String insertSQL = "UPDATE prestamo SET IDPrestamo=?, FechaPrestamo=?, FechaDevolucion=?, IDUsuario=?, IDLibro=?";
        try(PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {

            Prestamo prestamo = (Prestamo) o;

            preparedStatement.setInt(1, prestamo.getPrestamoID());
            preparedStatement.setDate(2, prestamo.getFechaPrestamo());
            preparedStatement.setDate(3, prestamo.getFechaDevolucion());
            preparedStatement.setInt(4, prestamo.getPrestamoUsuario());
            preparedStatement.setInt(5, prestamo.getPrestamoLibro());

            preparedStatement.executeUpdate();

            System.out.println("Prestamo actualizado exitosamente");
        } catch (SQLException e) {
            System.out.println("Hubo un problema al actualizar el Prestamo");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            String deleteSQL = "DELETE FROM prestamo WHERE ID=?";
            PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, deleteSQL);

            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Prestamo eliminado exitosamente");
            } else {
                System.out.println("No se encontró ningún prestamo con el ID especificado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
