package application.Models;

public class Car {

    private int id;
    private String name;
    private int companyId;
    private boolean isRented;

    public Car(int id, String name, int companyId, boolean isRented) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
        this.isRented = isRented;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }
}

