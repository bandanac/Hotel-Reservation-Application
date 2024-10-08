package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import java.util.Collection;

public class AdminResource {
    /**
     *
     * @param email
     * @return
     */
    public static Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }

    /**
     *
     * @param iRoom
     */
    public static void addRoom(IRoom iRoom) {
        ReservationService.addRoom(iRoom);
    }

    /**
     *
     * @return
     */
    public static Collection<IRoom> getAllRooms() {
        return ReservationService.getAllRooms();
    }

    /**
     *
     * @return
     */
    public static Collection<Customer> getAllCustomers() {
        return CustomerService.getAllCustomers();
    }

    /**
     *
     * @return
     */
    public static Collection<Reservation> getAllReservation() {
        return ReservationService.getAllReservations();
    }

    /**
     *
     */
    public static void displayAllReservation(){
        ReservationService.printAllReservation();
    }
}
