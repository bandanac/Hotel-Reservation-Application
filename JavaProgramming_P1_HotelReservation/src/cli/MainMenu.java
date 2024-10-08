package cli;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    public static void displayMainMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("Welcome to the Hotel Reservation Application");
        System.out.println("--------------------------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("--------------------------------------------");
        System.out.println("Please select an option");
    }

    public static boolean mainMenuCli(Scanner scanner, String option) {
        boolean keepRunning = true;
        switch (option){
            case "1":
                findAndReserveARoom(scanner);
                break;
            case "2":
                seeMyReservation(scanner);
                break;
            case "3":
                createAnAccount(scanner);
                break;
            case "4":
                admin(scanner);
                break;
            case "5":
                System.out.println("Thank you for visiting!");
                keepRunning = false;
                break;
            default:
                System.out.println("Please select a number between 1 to 5:");
                break;
        }
        return keepRunning;
    }

    private static void findAndReserveARoom(Scanner scanner) {
        /*
        1. enter check-in date
            - check if validity, (should be greater than current date) if not valid ask again.
            - if valid, then continue
        2. enter check-out date
            - check for validity, (should be greater than current data and check-in date), if not ask again.
            - if valid, then continue
        3. display all available rooms.
        4. ask if like to book a room.
        5. ask for email id.
        6. check email id validity.
            - if email id not available ask to create an account.
            - if email id available then continue
        7. ask which room no would you like to book.
        8. display all the booking details with prices and ask for confirmation.
        9. if confirmed then book a room and display success message.
         */

        Date checkInDate = getValidCheckInDate(scanner);
        Date checkOutDate = getValidCheckOutDate(scanner, checkInDate);

        Collection<IRoom> availableRooms = HotelResource.findRooms(checkInDate, checkOutDate);
        boolean wantsToBook = false;
        if (availableRooms.isEmpty()) {
            Date newCheckInDate = getRecommendedDate(checkInDate);
            Date newCheckOutDate = getRecommendedDate(checkOutDate);
            availableRooms = HotelResource.findRooms(newCheckInDate, newCheckOutDate);
            if (!availableRooms.isEmpty()) {
                System.out.println("Error! No rooms available. Try alternative dates, Check-in on " + newCheckInDate + " and Check-out on " + newCheckOutDate);
                wantsToBook = showAvailableRoomsAndAskToBook(scanner, availableRooms);
                checkInDate = newCheckInDate;
                checkOutDate = newCheckOutDate;
            } else {
                System.out.println("Error! No rooms available.");
            }
        } else {
            System.out.println("Hooray! Available rooms for check-in on " + checkInDate + " and check-out on " + checkOutDate);
            wantsToBook = showAvailableRoomsAndAskToBook(scanner, availableRooms);
        }
        if (!wantsToBook) {
            return;
        }

        Customer customer = getCustomerForReservation(scanner);
        if (customer == null) {
            System.out.println("Error! User account doesn't exists.");
            return;
        }

        IRoom room = getRoomForReservation(scanner, availableRooms);

        Reservation reservation = HotelResource.bookARoom(customer.getEmail(), room, checkInDate, checkOutDate);
        if (reservation == null) {
            System.out.println("Error! Booking is not successful.");
        } else {
            System.out.println("Hooray! Room is booked successfully.");
            System.out.println(reservation);
        }
    }

    private static void seeMyReservation(Scanner scanner) {
        System.out.println("Enter the registered Email Id");
        String email = scanner.nextLine();
        Customer customer = HotelResource.getCustomer(email);
        if (customer == null) {
            System.out.println("Sorry! No account found.");
            return;
        }
        Collection<Reservation> reservations = HotelResource.getCustomerReservations(customer.getEmail());
        if (reservations.isEmpty()) {
            System.out.println("Sorry! No reservation found.");
            return;
        }
        for (Reservation reservation: reservations) {
            System.out.println(reservation.toString());
        }
    }

    private static String createAnAccount(Scanner scanner) {
        /*
        1. enter email id
            - if email id available then return error and ask again
            - if email id not available then continue
        2. enter first name
            - check for validity (if valid continue/if not valid ask again)
        3. enter last name
            - check for validity (if valid continue/if not valid ask again)
        4. if all pass then create an account
         */

        System.out.println("First Name");
        String firstName = scanner.nextLine();
        System.out.println("Last Name");
        String lastName = scanner.nextLine();
        String email = null;
        boolean validateEmail = false;
        while (!validateEmail) {
            try {
                System.out.println("Email (format: name@domain.com)");
                email = scanner.nextLine();
                HotelResource.createACustomer(email, firstName, lastName);
                System.out.println("Hooray! The customer account is created successfully.");
                validateEmail = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        return email;
    }

    private static void admin(Scanner scanner) {
        boolean keepAdminRunning = true;
        while (keepAdminRunning) {
            try {
                AdminMenu.displayAdminMenu();
                String adminSelection = scanner.nextLine();
                keepAdminRunning = AdminMenu.adminMenuCli( scanner, adminSelection);
            } catch (Exception ex) {
                System.out.println("Enter a number between 1 and 6");
            }
        }
    }

    private static Customer getCustomerForReservation(Scanner scanner) {
        String email;
        boolean hasAccount = false;
        System.out.println("Do you have an account? (y/n)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
            hasAccount = true;
        }
        if (hasAccount) {
            System.out.println("Enter Email ID");
            email = scanner.nextLine();
        } else {
            email = createAnAccount(scanner);
        }
        return HotelResource.getCustomer(email);
    }

    private static IRoom getRoomForReservation(Scanner scanner, Collection<IRoom> availableRooms) {
        IRoom room = null;
        String roomNumber;
        boolean validRoomNumber = false;
        while (!validRoomNumber) {
            System.out.println("Enter the room number");
            roomNumber = scanner.nextLine();
            room = HotelResource.getARoom(roomNumber);
            if (room == null) {
                System.out.println("Invalid! Enter a valid room number.");
            } else {
                if (!availableRooms.contains(room)) {
                    System.out.println("Error! Room not available. Try again!");
                } else {
                    validRoomNumber = true;
                }
            }
        }
        return room;
    }

    private static boolean showAvailableRoomsAndAskToBook(Scanner scanner, Collection<IRoom> availableRooms) {
        for (IRoom room : availableRooms) {
            System.out.println(room.toString());
        }
        System.out.println();
        System.out.println("Would you like to book a room? (y/n)");
        String choice = scanner.nextLine();
        return choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes");
    }

    private static Date getRecommendedDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 7);
        return c.getTime();
    }

    private static Date getValidCheckInDate(Scanner scanner) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date checkInDate = null;
        boolean validCheckInDate = false;
        while (!validCheckInDate) {
            System.out.println("Enter Check-in Date (MM/dd/yyyy): ");
            String inputCheckInDate = scanner.nextLine();
            try {
                checkInDate = dateFormat.parse(inputCheckInDate);
                Date today = new Date();
                if (checkInDate.before(today)) {
                    System.out.println("Invalid! Check-in date is past today's date (" + dateFormat.format(today) + ")");
                } else {
                    validCheckInDate = true;
                }
            } catch (ParseException ex) {
                System.out.println("Invalid! Date format. Enter MM/dd/yyyy");
            }
        }
        return checkInDate;
    }

    private static Date getValidCheckOutDate(Scanner scanner, Date checkInDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date checkOutDate = null;
        boolean validCheckOutDate = false;
        while (!validCheckOutDate) {
            System.out.println("Enter Check-out Date (MM/dd/yyyy)");
            String inputCheckOutDate = scanner.nextLine();
            try {
                checkOutDate = dateFormat.parse(inputCheckOutDate);
                if (checkOutDate.before(checkInDate)) {
                    System.out.println("Invalid! Check-out date is past the check-in date (" + dateFormat.format(checkInDate) + ")");
                } else {
                    validCheckOutDate = true;
                }
            } catch (ParseException ex) {
                System.out.println("Invalid! Date format. Enter MM/dd/yyyy");
            }
        }
        return checkOutDate;
    }
}
