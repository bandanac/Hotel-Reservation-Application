package model;

import java.util.regex.Pattern;

/**
 * The class Customer handle customer accounts
 *
 * @author bee
 */
public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    private final String emailRegex = "^(.+)@(.+).com$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email){
        super();
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Error! Invalid email");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "First Name = '" + firstName + '\'' +
                ", Last Name = '" + lastName + '\'' +
                ", Email ID = '" + email + '\'' +
                '}';
    }
}
