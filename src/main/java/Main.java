import java.util.Scanner;

/**
 * <h2>Cash Machine</h2>
 * Implement interface to ATM cash.
 * The device received commands from standard input and sends replies to standard output.
 * <br>
 * <h3>Accepted commands:</h3>
 * <p>
 * <b>Add notes: + [currency] [value] [number] </b>
 * <br>[currency] is 3 uppercase letters, any combination is valid
 * <br>[value] is the value of notes. Valid values are 10n and 5*10n, 0<=n<=3
 * <br>[number] is any positive integer
 * <br>Semantics: put notes into cash
 * <br>Reply: OK if successful, ERROR if validation fails
 * </p>
 * <p><b>Get cash: - [currency] [amount] </b>
 * <br>[currency] as described above,
 * <br>[amount] any positive integer
 * <br>Semantics: get the sum from the cash if possible.
 * <br>Reply: one line per each note value, followed with a line OK
 * <br>ERROR if the amount is unavailable (cash remains unchanged).
 * <br>Example:
 * <br>- USD 2000
 * <br>100 20
 * <br>OK
 * </p>
 * <p><b>Print cash: ?</b>
 * <br>Reply: one line for each currency/value pair
 * <br>[currency] [value] [number]
 * <br>followed by the line OK
 * <br>Lines are ordered by first currency, then by value.
 * <br>Semantics: what is currently in the cash
 * </p>
 * <p><b>Exit program: exit</b></b>
 * </p>
 * @author Maxim Tarasov, maxtarasoff@gmail.com
 * @version 1.0
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