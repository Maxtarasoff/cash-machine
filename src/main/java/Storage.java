import org.apache.log4j.Logger;
import java.util.*;

/**
 * Storage of multiple currency containers.
 * Provides public methods to get storage balance, deposit and withdraw
 * @see #printCash()
 * @see #deposit(String, int, int)
 * @see #withdraw(String, int)
 */

public final class Storage {

    private static final Logger log = Logger.getLogger(Storage.class);
    private static final Map<String, CurrencyContainer> ATM = new HashMap<>();

    private static CurrencyContainer getContainerByCurrency(String currency){

        CurrencyContainer container;

        if (ATM.containsKey(currency)){
            container = ATM.get(currency);
        } else {
            container = new CurrencyContainer(currency);
            ATM.put(currency, container);
        }

        return container;
    }

    /**
     * Collect balance of each currency container
     * @return Balance of storage
     * <br>One line for each currency/value pair
     * <br>[currency] [value] [number]
     * <br>followed by the line OK
     * <br>Lines are ordered by first currency, then by value.
     */

    public static String printCash() {

        log.info("call printCash()");

        String result = "";
        List<String> currencies = new ArrayList<>(ATM.keySet());
        Collections.sort(currencies);

        for (String currency : currencies) {
            result = result.concat(ATM.get(currency).getBalance());
        }

        result = result.concat("OK");
        log.info(result);
        return result;
    }

    /**
     * Deposit notes into their own container
     * @param currency currency
     * @param noteValue value of notes
     * @param noteCount count of notes, positive number
     * @return OK on success, ERROR on fail
     */

    public static String deposit(String currency, int noteValue, int noteCount){

        log.info("call deposit(" + currency + ", " + noteValue + ", " + noteCount + ")");

        if (noteCount >0) return getContainerByCurrency(currency).depositNotes(noteValue, noteCount);

        log.error("noteCount <= 0");
        return "ERROR";
    }

    /**
     * Gets the sum from the cash if possible.
     * @param currency
     * @param amount
     * @return Way of withdraw amount with available notes.
     * <br>One line per each note value, followed with a line OK
     * <br>ERROR if the amount is unavailable (cash remains unchanged).
     */

    public static String withdraw(String currency, int amount){

        log.info("call Storage.withdraw (" + currency + ", " + amount + ")");

        if(amount>0) return getContainerByCurrency(currency).withdrawAmount(amount);

        log.error("amount <= 0");
        return "ERROR";
    }

}