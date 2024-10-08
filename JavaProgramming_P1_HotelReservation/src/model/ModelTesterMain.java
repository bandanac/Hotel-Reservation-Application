package model;

public class ModelTesterMain {
    public static void main(String[] args){
        Customer customer = new Customer("firstname", "lastname", "first@gmail.com");
        System.out.println(customer);

        Customer errorCustomer = new Customer("first", "second", "email");
        System.out.println(errorCustomer);
    }
}
