package repositorios;

import entidades.Autor;
import entidades.Libro; // Importamos la clase de libro
import entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroRepositorio implements Repositorio {

    private Connection connection;

    public LibroRepositorio(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {

        String crearTablaLibro = "CREATE TABLE IF NOT EXISTS Libro (" +
                "ID INT AUTO_INCREMENT PRIMARY KEY," +
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

        //Creamos la sentencia y creamos una lista de Libros para devolverla
        Statement sentencia = JdbcManager.createStatement(connection);
        ArrayList<Libro> listaLibros = new ArrayList<>();
        String query = "SELECT * FROM libro";

        try {
            ResultSet resultSet = sentencia.executeQuery(query);
            ResultSet result = sentencia.getResultSet();
            while (result.next()) {
                Libro libro = new Libro(
                        resultSet.getInt("ID"),
                        resultSet.getString("Titulo"),
                        resultSet.getString("Autor"),
                        resultSet.getInt("AnioPublicacion"),
                        resultSet.getInt("CopiasDisponibles"));
                listaLibros.add(libro);
            }
        } catch (SQLException e) {
            System.out.println("Hubo un problema con la lista de Libros");
            throw new RuntimeException(e);
        }
        return listaLibros;
    }

    @Override
    public Object findOneById(int id) {
        Libro libroEncontrado = null;
        try {
            Statement sentencia = JdbcManager.createStatement(connection);
            String query = "SELECT * from libro WHERE ID = " + id;

            ResultSet resultSet = sentencia.executeQuery(query);
            ResultSet result = sentencia.getResultSet();
            while (result.next()) {
                libroEncontrado = new Libro(
                        resultSet.getInt("ID"),
                        resultSet.getString("Titulo"),
                        resultSet.getString("Autor"),
                        resultSet.getInt("AnioPublicacion"),
                        resultSet.getInt("CopiasDisponibles")                        );
            }
        } catch (SQLException e) {
            System.out.println("Hubo un problema con el ResultSet de la sentencia SQL");
        }
        return libroEncontrado;
    }

    @Override
    public void save(Object o) {

        String insertSQL = "INSERT INTO libro (Titulo, Autor, AnioPublicacion, CopiasDisponibles) VALUES (?, ?, ?, ?)";
        Libro libro = (Libro) o;

        try(PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {

            preparedStatement.setString(1, libro.getTitulo());
            preparedStatement.setString(2, libro.getAutor());
            preparedStatement.setInt(3, libro.getYearPublicacion());
            preparedStatement.setInt(4, libro.getCopiasDisponibles());

            preparedStatement.executeUpdate();

            System.out.println("Libro con la ID: "+libro.getIdLibro()+" insertado exitosamente");
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al tratar de insertar el Libro con ID: "+libro.getIdLibro());
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object o) {
        String insertSQL = "UPDATE libro SET Titulo=?, Autor=?, AnioPublicacion=?, CopiasDisponibles=? WHERE ID=?";
        try(PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {

            Libro libro = (Libro) o;

            preparedStatement.setString(1, libro.getTitulo());
            preparedStatement.setString(2, libro.getAutor());
            preparedStatement.setInt(3, libro.getYearPublicacion());
            preparedStatement.setInt(4, libro.getCopiasDisponibles());
            preparedStatement.setInt(5, libro.getIdLibro());

            preparedStatement.executeUpdate();

            System.out.println("Libro actualizado exitosamente");
        } catch (SQLException e) {
            System.out.println("Hubo un problema al actualizar el Libro");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            String deleteSQL = "DELETE FROM libro WHERE ID=?";
            PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, deleteSQL);

            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Libro con ID: "+id+" eliminado exitosamente");
            } else {
                System.out.println("No se encontró ningún libro con el ID: "+id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
