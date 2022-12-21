package application.DAO;

import application.Models.Customer;

public interface CustomerDAO extends GenericDAO<Customer> {
    void update(Customer customer, int carId);
}
