package repositorios;
import entidades.Autor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AutorRepositorio implements Repositorio{
    private Connection connection;

    public AutorRepositorio(Connection connection){ this.connection = connection; }
    @Override
    public void createTable() throws SQLException {
        String crearTablaAutor = "CREATE TABLE Autor (" +
                "ID INT PRIMARY KEY," +
                "Nombre VARCHAR(255)," +
                "Nacionalidad VARCHAR(255)" +
                ")";
        Statement sentencia = JdbcManager.createStatement(connection);
        sentencia.executeUpdate(crearTablaAutor);
    }

    @Override
    public List<Autor> findAll() throws SQLException {

        // Creamos la sentencia y creamos una lista de Autores para devolverla
        Statement sentencia = JdbcManager.createStatement(connection);
        ArrayList<Autor> listaAutores = new ArrayList<>();
        String query = "SELECT * FROM autor";

        try {
            ResultSet resultSet = sentencia.executeQuery(query);
            ResultSet result = sentencia.getResultSet();
            while (result.next()){
                Autor autor = new Autor(
                resultSet.getInt("id"),
                resultSet.getString("nombre"),
                resultSet.getString("nacionalidad"));
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
