package repositorios;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {

    void createTable(Connection conn) throws SQLException;

    List<T> findAll();

    T findOneById(int id);

    void save(T t);

    void update(T t);

    void deleteById(int id);
   
}
