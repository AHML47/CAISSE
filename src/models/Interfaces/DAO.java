package models.Interfaces;
import java.util.List;

public interface DAO<T> {
    void save(T entity);
    void update(T entity);
    void delete(T entity);
    List<T> findAll();
}