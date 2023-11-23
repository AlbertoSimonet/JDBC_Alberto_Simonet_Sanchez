package repositorios;
import entidades.Autor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AutorRepositorio implements Repositorio{
    @Override
    public void createTable(Connection connection) throws SQLException {
        String crearTablaAutor = "CREATE TABLE Autor (" +
                "ID INT PRIMARY KEY," +
                "Nombre VARCHAR(255)," +
                "Nacionalidad VARCHAR(255)" +
                ")";
        Statement sentencia = JdbcManager.createStatement(connection);
        sentencia.executeUpdate(crearTablaAutor);
    }

    @Override
    public List<Autor> findAll() {
        ArrayList<Autor> listaAutores = new ArrayList<Autor>();



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
