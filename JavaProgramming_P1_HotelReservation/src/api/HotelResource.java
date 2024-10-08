package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import java.util.Collection;
import java.util.Date;

public class HotelResource {
    /**
     *
     * @param email
     * @return customer object
     */
    public static Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }

    /**
     *
     * @param email
     * @param firstName
     * @param lastName
     */
    public static void createACustomer(String email, String firstName, String lastName) {
        CustomerService.addCustomer(email, firstName, lastName);
    }

    /**
     *
     * @param roomNumber
     * @return room object
     */
    public static IRoom getARoom(String roomNumber) {
        return ReservationService.getARoom(roomNumber);
    }

    /**
     *
     * @param customerEmail
     * @param iRoom
     * @param checkInDate
     * @param checkOutDate
     * @return reservation object
     */
    public static Reservation bookARoom(String customerEmail, IRoom iRoom, Date checkInDate, Date checkOutDate) {
        Customer customer = CustomerService.getCustomer(customerEmail);
        return ReservationService.reserveARoom(customer, iRoom, checkInDate, checkOutDate);
    }

    /**
     *
     * @param customerEmail
     * @return customer object
     */
    public static Collection <Reservation> getCustomerReservations(String customerEmail) {
        Customer customer = CustomerService.getCustomer(customerEmail);
        return ReservationService.getCustomersReservation(customer);
    }

    /**
     *
     * @param checkInDate
     * @param checkOutDate
     * @return room object
     */
    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return ReservationService.findRooms(checkInDate, checkOutDate);
    }
}