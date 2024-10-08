import cli.MainMenu;
import java.util.Scanner;

public class HotelReservationApplication {
    public static void main(String[] args){
        boolean keepRunning = true;
        try (Scanner scanner = new Scanner(System.in)){
            while (keepRunning){
                try {
                    MainMenu.displayMainMenu();
                    String option = scanner.nextLine();
                    keepRunning = MainMenu.mainMenuCli(scanner, option);
                } catch (Exception ex) {
                    System.out.println("Enter a number between 1 to 5");
                }
            }
        } catch (Exception ex) {
            System.out.println("Invalid!");
        }
    }
}
