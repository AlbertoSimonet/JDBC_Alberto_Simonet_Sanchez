package repositorios;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {

    void createTable() throws SQLException;

    List<T> findAll() throws SQLException;

    T findOneById(int id);

    void save(T t);

    void update(T t);

    void deleteById(int id);
   
}
