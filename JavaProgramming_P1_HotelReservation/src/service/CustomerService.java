package service;

import model.Customer;
import java.util.*;

/**
 * 1. Create an customer account
 * 2. Get data of a specific user based on Email ID
 * 3. Get all customer data
 */
public final class CustomerService {

    /*
    Reference https://www.baeldung.com/java-singleton
     */
    private static CustomerService INSTANCE;
    private String string = "initial info class";

    private static final Map<String, Customer> allCustomerData = new HashMap<String, Customer>();

    private CustomerService(){}

    public static CustomerService getInstance(){
        if (INSTANCE == null){
            INSTANCE = new CustomerService();
        }
        return INSTANCE;
    }

    /**
     * The method add a new customer to allCustomer
     *
     * @param email email id of the customer
     * @param firstName first name of the customer
     * @param lastName last name of the customer
     */
    public static void addCustomer(String email, String firstName, String lastName){
        Customer newCustomer = new Customer(firstName, lastName, email);
        allCustomerData.put(newCustomer.getEmail(), newCustomer);
    }

    /**
     * The method gets the data of the specific customer
     *
     * @param customerEmail email id of the customer
     * @return if available - return customer data
     *         if not available - return null and error.
     */
    public  static Customer getCustomer(String customerEmail) {
        return allCustomerData.get(customerEmail);
    }

    /**
     * display all the customer data
     *
     * @return all the customer details
     */
    public static Collection<Customer> getAllCustomers() {
        return allCustomerData.values();
    }
}