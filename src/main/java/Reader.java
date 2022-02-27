import org.apache.log4j.Logger;

/**
 *  Recognize commands from standard input and sends reply according Main class description.
 */

public class Reader {

    private static final Logger log = Logger.getLogger(Reader.class);

    public static String execute(String command){

        log.info("call execute(" + command + ")");

        if (command.equalsIgnoreCase("exit")) {
            return "EXIT";
        }

        if (command.equals("?")) {
            return Storage.printCash();
        }

        if (isValidDepositCommand(command)) {
            String[] args = command.split("\\s");
            String currency = args[1];
            int noteValue = Integer.parseInt(args[2]);
            int noteCount = Integer.parseInt(args[3]);
            return Storage.deposit(currency, noteValue, noteCount);
        }

        if (isValidWithdrawCommand(command)){
            String[] args = command.split("\\s");
            String currency = args[1];
            int amount = Integer.parseInt(args[2]);
            return Storage.withdraw(currency, amount);
        }

        log.error("invalid command");
        return "ERROR";
    }

    private static boolean isValidDepositCommand(String command) {
        return command.matches("^\\+\\s[A-Z]{3}\\s\\d+\\s\\d+$");
    }

    private static boolean isValidWithdrawCommand(String command) {
        return command.matches("^-\\s[A-Z]{3}\\s\\d+$");
    }

}