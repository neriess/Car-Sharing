package application;

import application.DAOImpl.CarDAOImpl;
import application.Models.Car;

import java.util.List;
import java.util.Scanner;

public class CarManager extends CarDAOImpl {

    List<Car> listOfCars;
    Car selectedCar;

    public CarManager() {
        createTable();
    }
    public void create(Scanner scn, int companyID) {
        String name;

        System.out.printf("%nEnter the car name:%n");
        name = scn.nextLine();

        insert(new Car(0,name, companyID, false));

        System.out.printf("The car was created!%n");
    }

    public void printList(int companyId, boolean printAlsoRented) {

        listOfCars = getAllById(companyId, printAlsoRented);

        if (this.listOfCars.isEmpty()) {
            System.out.printf("%nThe car list is empty!%n");
        } else {
            int n = 1;
            for (Car car : listOfCars) {
                System.out.printf("%d. %s%n", n, car.getName());
                n++;
            }
        }
    }

    public List<Car> getListOfCars() {
        return listOfCars;
    }

    public void setListOfCars(List<Car> listOfCars) {
        this.listOfCars = listOfCars;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }
}
