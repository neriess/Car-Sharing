package application;

import application.DAOImpl.CompanyDAOImpl;
import application.Models.Company;

import java.util.List;
import java.util.Scanner;

public class CompanyManager extends CompanyDAOImpl {

    private Company selectedCompany;
    private List<Company> listOfCompanies;


    public CompanyManager() {
        createTable();
    }

    public void printList() {
        this.listOfCompanies = getAll();

        if (this.listOfCompanies.isEmpty()) {
            System.out.printf("%nThe company list is empty!%n");
        } else {
            System.out.printf("%nChoose the company:%n");
            int n = 1;
            for (Company company : listOfCompanies) {
                System.out.printf("%d. %s%n", n, company.getName());
                n++;
            }
            System.out.println("0. Back");
        }
    }


    public void create(Scanner scn) {
        String name;

        System.out.printf("%nEnter the company name:%n");
        name = scn.nextLine();

        insert(new Company(0,name));

        System.out.printf("The company was created!%n");
    }

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    public List<Company> getListOfCompanies() {
        return listOfCompanies;
    }

    public void setListOfCompanies(List<Company> listOfCompanies) {
        this.listOfCompanies = listOfCompanies;
    }
}
