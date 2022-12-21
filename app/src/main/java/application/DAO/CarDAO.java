package application.DAO;

import application.Models.Car;

import java.util.List;

public interface CarDAO extends GenericDAO<Car> {
    List<Car> getAllById(int id, boolean printAlsoRented);
    void update(Car car, boolean isRented);
}

