package application.DAO;

import java.util.List;

public interface GenericDAO<T> {

    T get(int id);

    List<T> getAll();

    void createTable();

    void insert(T t);
}
