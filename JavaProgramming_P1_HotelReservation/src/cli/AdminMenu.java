package cli;

import api.AdminResource;
import api.HotelResource;
import model.*;
import java.util.*;
import java.util.regex.Pattern;

public class AdminMenu {
    public static void displayAdminMenu(){
        System.out.println("--------------------------------------------");
        System.out.println("Admin menu");
        System.out.println("--------------------------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Add test data");
        System.out.println("6. Back to Main menu");
        System.out.println("--------------------------------------------");
        System.out.println("Please select an option");
    }

    public static boolean adminMenuCli(Scanner scanner, String option) {
        boolean keepRunningAdmin = true;

        switch (option){
            case "1":
                seeAllCustomers();
                break;
            case "2":
                seeAllRooms();
                break;
            case "3":
                seeAllReservations();
                break;
            case "4":
                addARoom(scanner);
                break;
            case "5":
                addTestData();
                break;
            case "6":
                keepRunningAdmin = false;
                break;
            default:
                System.out.println("Select a number between 1 to 6");
        }
        return keepRunningAdmin;
    }

    private static void seeAllCustomers() {
        /*
        1. fetch all the customer.
            if available - display all the customer details and back to main menu
            if not available - display error message and back to main menu
         */
        Collection<Customer> allCustomers = AdminResource.getAllCustomers();
        if (allCustomers.isEmpty()) {
            System.out.println("Sorry! No customers available.");
        } else {
            for (Customer customer: allCustomers) {
                System.out.println(customer.toString());
            }
        }
    }

    private static void seeAllRooms() {
        /*
        1. fetch all the rooms.
            if available - display all the room details and back to main menu
            if not available - display error message and back to main menu
         */
        Collection<IRoom> allRooms = AdminResource.getAllRooms();
        if (allRooms.isEmpty()) {
            System.out.println("Sorry! No rooms available.");
        } else {
            for (IRoom iRoom : allRooms) {
                System.out.println(iRoom.toString());
            }
        }
    }

    private static void seeAllReservations() {
        /*
        1. display all the reservations
         */
        Collection<Reservation> allReservations = AdminResource.getAllReservation();
        if (allReservations.isEmpty()) {
            System.out.println("Sorry! No reservations found.");
        } else {
            for (Reservation reservation: allReservations) {
                System.out.println(reservation.toString());
            }
        }
    }

    private static void addARoom(Scanner scanner) {
        /*
        1. validate room number
            - if invalid input then return error and ask again
            - if room number already exists then display error message and ask again for new room number
            - if above cases fail then store the room number
         2. validate room price
            - if invalid input then display error and ask again
            - if price is a negative number then display error and ask again
            - if above cases fail then store the room price
         3. validate room type
            - if room type is 1 then store SINGLE
            - if room type is 2 then store DOUBLE
            - if it's not (1/2) then display error and ask again
         4. Once all the validation is successful then add the room. and display success message.
         */

        String roomNumber = validateRoomNumber();
        Double price = validatePrice();
        RoomType roomType = validateRoomType();

        Room newRoom = new Room(roomNumber, price, roomType);
        AdminResource.addRoom(newRoom);
        System.out.println("Hooray! Room added successfully.");
    }

    private static String validateRoomNumber() {
        /*
        validate room number
         */
        String roomNumber = null;
        String roomNumberRegEx = "^[0-9]*$";
        boolean checkRoomNumber = false;

        Scanner scannerRoomNumber = new Scanner(System.in);
        System.out.println("Enter room number");

        while (!checkRoomNumber) {
            roomNumber = scannerRoomNumber.nextLine();
            Pattern pattern = Pattern.compile(roomNumberRegEx);
            if (roomNumber.isEmpty()) {
                System.out.println("Invalid! Empty room number.");
            } else if (!pattern.matcher(roomNumber).matches()) {
                System.out.println("Invalid! Enter a numeric value.");
            } else {
                IRoom roomExist = HotelResource.getARoom(roomNumber);
                if (roomExist == null) {
                    checkRoomNumber = true;
                    return roomNumber;
                } else {
                    System.out.println("Sorry! Room already exists.");
                }
            }
            System.out.println("Try again!");
        }
        return roomNumber;
    }

    private static Double validatePrice(){
        /*
        validate price
         */
        Double price = 0.00;
        boolean checkPrice = false;

        Scanner scannerPrice = new Scanner(System.in);
        System.out.println("Enter price per night");

        while (!checkPrice) {
            try {
                price = Double.parseDouble(scannerPrice.nextLine());
                if (price.isNaN()){
                    System.out.println("Invalid! Empty price.");
                } else {
                    if (price < 0.0) {
                        System.out.println("Invalid! Enter a valid price.");
                    } else {
                        checkPrice = true;
                        return price;
                    }
                }
            } catch (NumberFormatException ex){
                System.out.println("Invalid! Enter a valid price.");
            } catch (Exception ex) {
                System.out.println("Please enter a valid Price");
            }
            System.out.println("Try again!");
        }
        return price;
    }

    private static RoomType validateRoomType(){
        /*
        validate room type
         */
        RoomType roomType = null;
        boolean checkRoomType = false;

        Scanner scannerRoomType = new Scanner(System.in);
        System.out.println("Enter room type (1 for single bed / 2 for double bed)");

        while (!checkRoomType) {
            try {
                roomType = RoomType.valueOfBedCount((scannerRoomType.nextLine()));
                if (roomType == null) {
                    System.out.println("Invalid! Enter valid room type (1/2).");
                } else {
                    checkRoomType = true;
                    return roomType;
                }
            } catch (Exception ex) {
                System.out.println("Invalid! Enter valid room type (1/2).");
            }
            System.out.println("Try again!");
        }
        return roomType;
    }

    private static void addTestData() {
        /*
        Dummy data
        1. add rooms
        2. add customers
        3. reservations
         */

        String roomNumber;
        Double price;
        RoomType roomType;

        roomNumber = "100";
        price = 100.0;
        roomType = RoomType.valueOfBedCount("1");
        Room newRoom100 = new Room(roomNumber, price, roomType);
        AdminResource.addRoom(newRoom100);

        roomNumber = "200";
        price = 200.0;
        roomType = RoomType.valueOfBedCount("2");
        Room newRoom200 = new Room(roomNumber, price, roomType);
        AdminResource.addRoom(newRoom200);

        HotelResource.createACustomer("aria_bell@xyz.com", "Aria", "Bell");
        HotelResource.createACustomer("blake_miller@xyz.com", "Blake", "Miller");

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        Date checkInDate;
        Date checkOutDate;

        c.setTime(today);
        c.add(Calendar.DATE, 1);
        checkInDate = c.getTime();
        c.setTime(checkInDate);
        c.add(Calendar.DATE, 2);
        checkOutDate = c.getTime();
        HotelResource.bookARoom("aria_bell@xyz.com", HotelResource.getARoom("100"), checkInDate, checkOutDate);

        // reservation 2
        c.setTime(today);
        c.add(Calendar.DATE, 3);
        checkInDate = c.getTime();
        c.setTime(checkInDate);
        c.add(Calendar.DATE, 7);
        checkOutDate = c.getTime();
        HotelResource.bookARoom("blake_miller@xyz.com", HotelResource.getARoom("200"), checkInDate, checkOutDate);

        System.out.println("Hooray! Test data added!");
    }
}