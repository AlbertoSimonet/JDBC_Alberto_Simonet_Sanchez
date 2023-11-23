package repositorios;

import entidades.Autor; // Importamos la clase de autor
import entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorRepositorio implements Repositorio {
    private Connection connection;

    public AutorRepositorio(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {
        String crearTablaAutor = "CREATE TABLE IF NOT EXISTS Autor (" +
                "ID INT PRIMARY KEY," +
                "Nombre VARCHAR(255)," +
                "Nacionalidad VARCHAR(255)" +
                ")";
        Statement sentencia = JdbcManager.createStatement(connection);
        sentencia.executeUpdate(crearTablaAutor);
    }

    @Override
    public List findAll() throws SQLException {

        // Creamos la sentencia y creamos una lista de Autores para devolverla
        Statement sentencia = JdbcManager.createStatement(connection);
        ArrayList<Autor> listaAutores = new ArrayList<>();
        String query = "SELECT * FROM autor";

        try {
            ResultSet resultSet = sentencia.executeQuery(query);
            ResultSet result = sentencia.getResultSet();
            while (result.next()) {
                Autor autor = new Autor(
                        resultSet.getInt("ID"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("Nacionalidad"));
                listaAutores.add(autor);
            }
        } catch (SQLException e) {
            System.out.println("Hubo un problema con la lista de Autores");
            throw new RuntimeException(e);
        }
        return listaAutores;
    }

    @Override
    public Object findOneById(int id) {
        Autor autorEncontrado = null;
        try {
            Statement sentencia = JdbcManager.createStatement(connection);
            String query = "SELECT * from autor WHERE ID = " + id;

            ResultSet resultSet = sentencia.executeQuery(query);
            ResultSet result = sentencia.getResultSet();
            while (result.next()) {
                autorEncontrado = new Autor(
                        resultSet.getInt("ID"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("Nacionalidad"));
            }
        } catch (SQLException e) {
            System.out.println("Hubo un problema con el ResultSet de la sentencia SQL");
        }
        return autorEncontrado;
    }

    @Override
    public void save(Object o) {

        String insertSQL = "INSERT INTO autor (ID, Nombre, Nacionalidad) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {

            Autor autor = (Autor)o;

            preparedStatement.setInt(1, autor.getIdAutor());
            preparedStatement.setString(2, autor.getNombre());
            preparedStatement.setString(3, autor.getNacionalidad());

            preparedStatement.executeUpdate();

            System.out.println("Autor insertado exitosamente");
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al tratar de insertar el Autor");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object o) {
        String insertSQL = "UPDATE autor SET ID=?, Nombre=?, Nacionalidad=?";
        try(PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {

            Autor autor = (Autor)o;

            preparedStatement.setInt(1, autor.getIdAutor());
            preparedStatement.setString(2, autor.getNombre());
            preparedStatement.setString(3, autor.getNacionalidad());

            preparedStatement.executeUpdate();

            System.out.println("Autor actualizado exitosamente");
        } catch (SQLException e) {
            System.out.println("Hubo un problema al actualizar el Autor");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            String deleteSQL = "DELETE FROM autor WHERE ID=?";
            PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, deleteSQL);

            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Autor eliminado exitosamente");
            } else {
                System.out.println("No se encontró ningún autor con el ID especificado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
