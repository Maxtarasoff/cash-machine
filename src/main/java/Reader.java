import org.apache.log4j.Logger;

public class Reader {

    private static final Logger log = Logger.getLogger(Reader.class);

    public static String execute(String command){

        log.info("call execute(" + command + ")");
        String result = "ERROR";

        if (command.equalsIgnoreCase("exit")) {
            result = "EXIT";
        } else

        if (command.equals("?")) {
            result = Storage.printCash();
        } else

        if (isValidDepositCommand(command)) {
            String[] args = command.split("\\s");
            String currency = args[1];
            int noteValue = Integer.parseInt(args[2]);
            int noteCount = Integer.parseInt(args[3]);
            result = Storage.deposit(currency, noteValue, noteCount);
        } else

        if (isValidWithdrawCommand(command)){
            String[] args = command.split("\\s");
            String currency = args[1];
            int amount = Integer.parseInt(args[2]);
            result = Storage.withdraw(currency, amount);
        }
        return result;
    }

    private static boolean isValidDepositCommand(String command) {
        return command.matches("^\\+\\s[A-Z]{3}\\s\\d+\\s\\d+$");
    }

    private static boolean isValidWithdrawCommand(String command) {
        return command.matches("^-\\s[A-Z]{3}\\s\\d+$");
    }

}