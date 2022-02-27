import java.util.Scanner;

/**
 * Main class Cash Machine
 * @author Maxim Tarasov, maxtarasoff@gmail.com
 * @version 1.0
 *
 * 4 TODO Refactor variables names
 * 3 TODO Refactor methods names
 * 2 TODO Logging
 * 0 TODO push to Github
 *
 * */

public class Main {

    public static void main(String[] args) {

        System.out.println("CashMachine");
        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            command = scanner.nextLine();
            System.out.println(Reader.execute(command));
        } while (!command.equalsIgnoreCase("exit"));

        scanner.close();
    }
}