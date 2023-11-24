package repositorios;

import entidades.Prestamo;

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
                "IDPrestamo INT AUTO_INCREMENT PRIMARY KEY," +
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
                        resultSet.getDate("FechaPrestamo").toLocalDate(),
                        resultSet.getDate("FechaDevolucion").toLocalDate(),
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
                        resultSet.getDate("FechaPrestamo").toLocalDate(),
                        resultSet.getDate("FechaDevolucion").toLocalDate(),
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

        String insertSQL = "INSERT INTO prestamo (FechaPrestamo, FechaDevolucion, IDUsuario, IDLibro) VALUES (?, ?, ?, ?)";
        Prestamo prestamo = (Prestamo) o;

        try {
            // Verificar la disponibilidad de copias antes de insertar el préstamo
            String verificarCopiasSQL = "SELECT CopiasDisponibles FROM Libro WHERE ID = ? AND CopiasDisponibles > 0 FOR UPDATE";
            PreparedStatement verificarCopiasStatement = JdbcManager.createPreparedStatement(connection, verificarCopiasSQL);
            verificarCopiasStatement.setInt(1, prestamo.getLibroID());
            ResultSet resultSet = verificarCopiasStatement.executeQuery();

            if (resultSet.next()) { // Si hay resultados, hay copias disponibles
                int copiasDisponibles = resultSet.getInt("CopiasDisponibles");
                if (copiasDisponibles > 0) {
                    // Restar una copia disponible
                    String actualizarCopiasSQL = "UPDATE Libro SET CopiasDisponibles = ? WHERE ID = ?";
                    PreparedStatement actualizarCopiasStatement = JdbcManager.createPreparedStatement(connection, actualizarCopiasSQL);
                    actualizarCopiasStatement.setInt(1, copiasDisponibles - 1);
                    actualizarCopiasStatement.setInt(2, prestamo.getLibroID());
                    int filasActualizadas = actualizarCopiasStatement.executeUpdate();

                    // Continuar con la inserción del préstamo si la actualización de copias fue exitosa
                    if (filasActualizadas > 0) {
                        try (PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {
                            preparedStatement.setDate(1, Date.valueOf(prestamo.getFechaPrestamo()));
                            preparedStatement.setDate(2, Date.valueOf(prestamo.getFechaDevolucion()));
                            preparedStatement.setInt(3, prestamo.getUsuarioID());
                            preparedStatement.setInt(4, prestamo.getLibroID());

                            int filasInsertadas = preparedStatement.executeUpdate();
                            System.out.println("Se ha registrador correctamente el prestamo");
                        } catch (SQLException e) {
                            System.out.println("Hubo un problema al actualizar el Prestamo");
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("No se pudo actualizar la cantidad de copias disponibles.");
                        // Puedes manejar esta situación de acuerdo a tus necesidades
                    }
                }
            } else {
                // No hay copias disponibles para el libro
                System.out.println("No hay copias disponibles para el libro con ID " + prestamo.getLibroID());
                // Puedes manejar esta situación de acuerdo a tus necesidades
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object o) {
        String insertSQL = "UPDATE prestamo p JOIN libro l ON p.IDLibro = l.ID SET p.FechaPrestamo=?, p.FechaDevolucion=?, p.IDUsuario=?, p.IDLibro=? WHERE p.IDPrestamo=? AND l.CopiasDisponibles > 0 ";
        try (PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {

            Prestamo prestamo = (Prestamo) o;

            preparedStatement.setDate(1, Date.valueOf(prestamo.getFechaPrestamo()));
            preparedStatement.setDate(2, Date.valueOf(prestamo.getFechaDevolucion()));
            preparedStatement.setInt(3, prestamo.getUsuarioID());
            preparedStatement.setInt(4, prestamo.getLibroID());
            preparedStatement.setInt(5, prestamo.getID());

            preparedStatement.executeUpdate();

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
