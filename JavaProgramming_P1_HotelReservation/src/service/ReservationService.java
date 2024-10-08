package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import java.util.*;

public final class ReservationService {

    /*
    Reference https://www.baeldung.com/java-singleton
     */
    private static ReservationService INSTANCE;
    private String string = "initial info class";

    private static final Map<String, IRoom> allRoomData = new HashMap<String, IRoom>();
    private static final Map<String, Collection<Reservation>> allReservationData = new HashMap<String, Collection<Reservation>>();

    private ReservationService(){}

    public static ReservationService getInstance(){
        if (INSTANCE == null){
            INSTANCE = new ReservationService();
        }
        return INSTANCE;
    }

    /**
     * Admin scenario - Add a room
     * The method adds a room to allRoom
     *
     * @param iRoom all room to the map
     *
     */
    public static void addRoom(IRoom iRoom){
        allRoomData.put(iRoom.getRoomNumber(), iRoom);
    }

    /**
     * Admin scenario - display the details of the specific room
     * The method gets a specific room data
     *
     * @param roomNumber
     * @return if roomId is available - return room data
     *         if roomId is not available - display error and return null
     */
    public static IRoom getARoom(String roomNumber) {
        return allRoomData.get(roomNumber);
    }

    /**
     * The method reserves a room
     *
     * @param customer
     * @param iRoom
     * @param checkInDate
     * @param checkOutDate
     * @return reservation
     */
    public static Reservation reserveARoom(Customer customer, IRoom iRoom, Date checkInDate, Date checkOutDate){
        // already exists
        if (isRoomReserved(iRoom, checkInDate, checkOutDate)) {
            return null;
        }

        Reservation reservation = new Reservation(customer, iRoom, checkInDate, checkOutDate);
        Collection<Reservation> customerReservations = getCustomersReservation(customer);
        if (customerReservations == null) {
            customerReservations = new LinkedList<>();
        }

        customerReservations.add(reservation);
        allReservationData.put(customer.getEmail(), customerReservations);
        return reservation;
    }

    /**
     * Customer scenario - Find a room
     * The method finds the available rooms
     *
     * @param checkInDate
     * @param checkOutDate
     * @return available rooms
     */
    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Collection<IRoom> reservedRooms = getAllReservedRooms(checkInDate, checkOutDate);
        Collection<IRoom> availableRooms = new LinkedList<>();
        for (IRoom iRoom : getAllRooms()) {
            if (!reservedRooms.contains(iRoom)) {
                availableRooms.add(iRoom);
            }
        }
        return availableRooms;
    }

    /**
     * The method to get specific reservation
     *
     * @param customer
     * @return specific reservation
     */
    public static Collection<Reservation> getCustomersReservation(Customer customer){
        return allReservationData.get(customer.getEmail());
    }

    /**
     * The method displays all the reservations.
     */
    public static void printAllReservation(){
        if (allReservationData.isEmpty()){
            System.out.println("Sorry! No reservations available!");
        } else {
            for (Collection<Reservation> reservation : allReservationData.values()){
                System.out.println(reservation);
            }
        }
    }

    /**
     * Admin scenario - display all the rooms data
     *
     * @return all the rooms details
     */
    public static Collection<IRoom> getAllRooms() {
        return allRoomData.values();
    }

    public static Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new LinkedList<>();
        for (Collection<Reservation> customerReservations : allReservationData.values()) {
            allReservations.addAll(customerReservations);
        }
        return allReservations;
    }

    private static Collection<IRoom> getAllReservedRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> reservedRooms = new LinkedList<>();
        for (Reservation reservation : getAllReservations()) {
            if (reservation.isRoomReserved(checkInDate, checkOutDate)) {
                reservedRooms.add(reservation.getARoom());
            }
        }
        return reservedRooms;
    }

    private static boolean isRoomReserved(IRoom iRoom, Date checkInDate, Date checkOutDate) {
        Collection<IRoom> reservedRooms = getAllReservedRooms(checkInDate, checkOutDate);
        return reservedRooms.contains(iRoom);
    }
}