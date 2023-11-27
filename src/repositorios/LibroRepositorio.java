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

        Statement sentencia = JdbcManager.createStatement(connection);

        String crearTablaLibro = "CREATE TABLE Libro ("+
                "ID INT AUTO_INCREMENT PRIMARY KEY,"+
                "Titulo VARCHAR(255),"+
                "AnioPublicacion INT, " +
                "CopiasDisponibles INT, " +
                "IDAutor INT, FOREIGN KEY (IDAutor) REFERENCES Autor(ID))";
        sentencia.executeUpdate(crearTablaLibro);
        System.out.println("Tabla libros creada");
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
                        resultSet.getInt("AnioPublicacion"),
                        resultSet.getInt("CopiasDisponibles"),
                        resultSet.getInt("IDAutor"));
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
                        resultSet.getInt("AnioPublicacion"),
                        resultSet.getInt("CopiasDisponibles"),
                        resultSet.getInt("IDAutor"));
            }
        } catch (SQLException e) {
            System.out.println("Hubo un problema con el ResultSet de la sentencia SQL");
        }
        return libroEncontrado;
    }

    @Override
    public void save(Object o) {

        String insertSQL = "INSERT INTO libro (Titulo, AnioPublicacion, CopiasDisponibles, IDAutor) VALUES (?, ?, ?, ?)";
        Libro libro = (Libro) o;

        try(PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {

            preparedStatement.setString(1, libro.getTitulo());
            preparedStatement.setInt(2, libro.getYearPublicacion());
            preparedStatement.setInt(3, libro.getCopiasDisponibles());
            preparedStatement.setInt(4, libro.getIDAutor());

            preparedStatement.executeUpdate();

            System.out.println("Libro: "+libro.getTitulo()+" insertado exitosamente");
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al tratar de insertar el Libro con ID: "+libro.getIdLibro());
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object o) {
        String insertSQL = "UPDATE libro SET Titulo=?, AnioPublicacion=?, CopiasDisponibles=?, IDAutor=? WHERE ID=?";
        try(PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {

            Libro libro = (Libro) o;

            preparedStatement.setString(1, libro.getTitulo());
            preparedStatement.setInt(2, libro.getYearPublicacion());
            preparedStatement.setInt(3, libro.getCopiasDisponibles());
            preparedStatement.setInt(4, libro.getIDAutor());
            preparedStatement.setInt(5, libro.getIdLibro());

            preparedStatement.executeUpdate();

            System.out.println("Libro: "+libro.getIdLibro()+" actualizado exitosamente");
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

    public List BookAndAutor(){
        Statement sentencia = JdbcManager.createStatement(connection);
        ArrayList<Libro> listaLibros = new ArrayList<>();
        String librosYAutores = "SELECT Libro.ID AS LibroID, Libro.Titulo AS TituloLibro, Autor.ID AS AutorID, Autor.Nombre AS NombreAutor FROM Libro JOIN Autor ON Libro.IDAutor = Autor.ID";
        // El String librosYAutores genera una query que utiliza JOIN para poder mostrar el libro
        // junto con su autor utilizando el ID del autor como conexion entre ambas tablas

        try {
            ResultSet resultSet = sentencia.executeQuery(librosYAutores);
            ResultSet result = sentencia.getResultSet();
            while (result.next()) {
                Libro libro = new Libro(
                        resultSet.getInt("LibroID"),
                        resultSet.getString("TituloLibro"),
                        resultSet.getInt("AutorID"),
                        resultSet.getString("NombreAutor"));
                listaLibros.add(libro);
            }
        } catch (SQLException e) {
            System.out.println("Hubo un problema con la lista de Libros");
            throw new RuntimeException(e);
        }
        return listaLibros;
    }
}