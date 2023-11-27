package repositorios;

import entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio implements Repositorio {
    private Connection connection;

    public UsuarioRepositorio(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {

        Statement sentencia = JdbcManager.createStatement(connection);

        String crearTablaUsuario = "CREATE TABLE IF NOT EXISTS Usuario (" +
                "ID INT AUTO_INCREMENT PRIMARY KEY," +
                "Nombre VARCHAR(255)," +
                "CorreoElectronico VARCHAR(255)" +
                ")";
        sentencia.executeUpdate(crearTablaUsuario);
    }

    @Override
    public List findAll() {

        //Creamos la sentencia y creamos una lista de Usuarios para devolverla
        Statement sentencia = JdbcManager.createStatement(connection);
        ArrayList<Usuario> listaUser = new ArrayList<>();
        String query = "SELECT * FROM usuario";

        try {
            ResultSet resultSet = sentencia.executeQuery(query);
            ResultSet result = sentencia.getResultSet();
            while (result.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getInt("ID"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("CorreoElectronico"));
                listaUser.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Hubo un problema con la lista de Usuarios");
            throw new RuntimeException(e);
        }
        return listaUser;
    }

    @Override
    public Object findOneById(int id) {
        Usuario usuarioEncontrado = null;
        try {
            Statement sentencia = JdbcManager.createStatement(connection);
            String query = "SELECT * from usuario WHERE ID = " + id;

            ResultSet resultSet = sentencia.executeQuery(query);
            ResultSet result = sentencia.getResultSet();
            while (result.next()) {
                usuarioEncontrado = new Usuario(
                        resultSet.getInt("ID"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("CorreoElectronico"));
            }
        } catch (SQLException e) {
            System.out.println("Hubo un problema con el ResultSet de la sentencia SQL");
        }
        return usuarioEncontrado;
    }

    @Override
    public void save(Object o) {

        String insertSQL = "INSERT INTO usuario (Nombre, CorreoElectronico) VALUES (?, ?)";
        Usuario usuario = (Usuario) o;

        try(PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getCorreoElectronico());

            preparedStatement.executeUpdate();

            System.out.println("Usuario insertado exitosamente");
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un error al tratar de insertar el Usuario");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object o) {

        String insertSQL = "UPDATE usuario SET Nombre=?, CorreoElectronico=? WHERE ID=?";
        try(PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, insertSQL)) {

            Usuario usuario = (Usuario) o;

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getCorreoElectronico());
            preparedStatement.setInt(3, usuario.getIdUsuario());

            preparedStatement.executeUpdate();

            System.out.println("Usuario actualizado exitosamente");
        } catch (SQLException e) {
            System.out.println("Hubo un problema al actualizar el Usuario");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            String deleteSQL = "DELETE FROM usuario WHERE ID=?";
            PreparedStatement preparedStatement = JdbcManager.createPreparedStatement(connection, deleteSQL);

            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Usuario eliminado exitosamente");
            } else {
                System.out.println("No se encontró ningún usuario con el ID especificado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}