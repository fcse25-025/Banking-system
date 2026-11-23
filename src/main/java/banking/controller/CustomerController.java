package banking.controller;

import banking.dao.CustomerDAO;
import banking.model.Customer;

public class CustomerController {

    private CustomerDAO customerDAO = new CustomerDAO();

    public boolean saveCustomer(Customer customer) {
        return customerDAO.saveCustomer(customer);
    }

    public Customer findCustomer(int customerID) {
        return customerDAO.findCustomer(customerID);
    }

    public boolean updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    public boolean deleteCustomer(int customerID) {
        return customerDAO.deleteCustomer(customerID);
    }
}