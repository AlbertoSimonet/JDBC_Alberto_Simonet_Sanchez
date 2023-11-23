package repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcManager {
    public static Statement createStatement(Connection connection) {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Error al conectar el statement");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static PreparedStatement createPreparedStatement(Connection connection, String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println("Error al conectar el statement");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
