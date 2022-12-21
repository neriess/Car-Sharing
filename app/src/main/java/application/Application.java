package application;

import application.Models.Car;
import application.Models.Company;
import application.Models.Customer;

import java.util.List;
import java.util.Scanner;

public class Application {

    CompanyManager companyManager;
    CarManager carManager;
    CustomerManager customerManager;
    Scanner scn;
    private final String MAIN_MENU = """
            \n1. Log in as a manager
            2. Log in as a customer
            3. Create a customer
            0. Exit""";
    private final String MANAGER_MENU = """
            \n1. Company list
            2. Create a company
            0. Back""";
    private final String CAR_MENU = """
            \n1. Car list
            2. Create a car
            0. Back""";
    private final String RENT_CAR_MENU = """
            \n1. Rent a car
            2. Return a rented car
            3. My rented car
            0. Back""";
    private final String BAD_INPUT = "Bad Input!";

    public Application() {
        this.companyManager = new CompanyManager();
        this.carManager = new CarManager();
        this.customerManager = new CustomerManager();
        this.scn = new Scanner(System.in);
    }

    public void run() {

        while(true) {
            System.out.println(MAIN_MENU);

            switch (getMenuInput()) {
                case 0 -> exit();
                case 1 -> showManagerMenu();
                case 2 -> showCustomerMenu();
                case 3 -> customerManager.create(scn);
                default -> System.out.println(BAD_INPUT);
            }
        }
    }

    private void showCustomerMenu() {

        customerManager.printList();
        List<Customer> customerList = customerManager.getListOfCustomers();

        if (!customerList.isEmpty()) {
            int inputNum = getMenuInput();
            if (inputNum != 0) {
                if (inputNum > customerList.size() || inputNum < 0) {
                    System.out.println(BAD_INPUT);
                } else {
                    customerManager.setSelectedCustomer(customerList.get(inputNum - 1));
                    showLoggedCustomerMenu();
                }
            }
        }
    }

    private void showLoggedCustomerMenu() {

        boolean goBack = false;

        while(!goBack) {
            System.out.println(RENT_CAR_MENU);


            switch (getMenuInput()) {
                case 0 -> goBack = true;
                case 1 -> showRentCarMenu();
                case 2 -> returnRentedCar();
                case 3 -> showRentedCar();
                default -> System.out.println(BAD_INPUT);
            }
        }
    }

    private void showRentCarMenu() {

        int customerRentedCarId = customerManager.getSelectedCustomer().getRentedCarId();

        if(customerRentedCarId != 0) {
            System.out.printf("%nYou've already rented a car!%n");
        } else {
            showCompanyListMenu();
            List<Car> companyCars = carManager.getAllById(companyManager.getSelectedCompany().getId(), false);
            if (companyCars.isEmpty()) {
                System.out.printf("%nNo available cars in the %s company.%n",
                        companyManager.getSelectedCompany().getName());
            } else {
                chooseCarToRent(companyCars);
            }
        }
    }

    private void chooseCarToRent(List<Car> companyCars) {

        System.out.printf("%nChoose a car:%n");

        int selectedCompanyId = companyManager.getSelectedCompany().getId();
        carManager.printList(selectedCompanyId, false);
        int input = getMenuInput();

        if (input <= 0 || input > carManager.getAllById(selectedCompanyId, false).size()) {
            System.out.printf(BAD_INPUT);
        } else {
            carManager.setSelectedCar(companyCars.get(input - 1));
            updateCustomer();
            carManager.update(carManager.getSelectedCar(), true);
            System.out.printf("%nYou rented '%s'%n", carManager.getSelectedCar().getName());
        }
    }


    private void showManagerMenu() {

        boolean goBack = false;

        while(!goBack) {
            System.out.println(MANAGER_MENU);

            switch(getMenuInput()) {
                case 0 -> goBack = true;
                case 1 -> {
                    showCompanyListMenu();
                    showCarMenu();
                }
                case 2 -> companyManager.create(scn);
                default -> System.out.println(BAD_INPUT);
            }
        }
    }

    private void showCompanyListMenu() {

        companyManager.printList();
        List<Company> companyList = companyManager.getListOfCompanies();

        if (!companyList.isEmpty()) {
            int inputNum = getMenuInput();
            if (inputNum != 0) {
                if (inputNum > companyList.size() || inputNum < 0) {
                    System.out.println(BAD_INPUT);
                } else {
                    companyManager.setSelectedCompany(companyList.get(inputNum - 1));
                }
            }
        }
    }

    private void showCarMenu() {
        boolean goBack = false;

        Company selectedCompany = companyManager.getSelectedCompany();
        if (selectedCompany != null) {
            while(!goBack) {
                System.out.println(CAR_MENU);

                switch(getMenuInput()) {
                    case 0 -> goBack = true;
                    case 1 -> carManager.printList(selectedCompany.getId(), true);
                    case 2 -> carManager.create(scn, selectedCompany.getId());
                    default -> System.out.println(BAD_INPUT);
                }
            }
        }
    }


    private int getMenuInput() {
        int input = -1;

        try {
            input = Integer.parseInt(scn.nextLine());
        } catch (Exception e) {
            System.out.println();
        }
        return input;
    }

    private void updateCustomer() {
        int selectedCustomerId = customerManager.getSelectedCustomer().getId();

        customerManager.update(customerManager.getSelectedCustomer(), carManager.selectedCar.getId());
        customerManager.setSelectedCustomer(customerManager.get(selectedCustomerId));

    }

    private void returnRentedCar() {

        if (customerManager.getSelectedCustomer().getRentedCarId() == 0) {
            System.out.printf("%nYou didn't rent a car!%n");
        } else {
            carManager.setSelectedCar(carManager.get(customerManager.getSelectedCustomer().getId()));
            customerManager.updateRentedCarId(customerManager.getSelectedCustomer());
            carManager.update(carManager.selectedCar, false);
            customerManager.setSelectedCustomer(customerManager.get(customerManager.getSelectedCustomer().getId()));
            carManager.setSelectedCar(null);

            System.out.printf("%nYou've returned a rented car!%n");
        }
    }

    private void showRentedCar() {
        if (carManager.getSelectedCar() == null) {
            System.out.printf("%nYou didn't rent a car!%n");
        } else {
            System.out.printf("%nYour rented car:%n");
            System.out.print(carManager.selectedCar.getName());
            System.out.printf("%nCompany:%n");
            System.out.println(companyManager.getSelectedCompany().getName());
        }
    }

    private void exit() {
        System.exit(0);
    }
}
