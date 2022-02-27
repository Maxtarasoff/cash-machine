import org.apache.log4j.Logger;
import java.util.*;

public final class Storage {

    private static final Logger log = Logger.getLogger(Storage.class);
    private static final Map<String, CurrencyContainer> ATM = new HashMap<>();

    public static CurrencyContainer getContainerByCurrency(String currency){

        CurrencyContainer container;

        if (ATM.containsKey(currency)){
            container = ATM.get(currency);
        } else {
            container = new CurrencyContainer(currency);
            ATM.put(currency, container);
        }

        return container;
    }

    public static String printCash() {

        log.info("call printCash()");

        String result = "";
        List<String> currencies = new ArrayList<>(ATM.keySet());
        Collections.sort(currencies);

        for (String currency : currencies) {
            result = result.concat(ATM.get(currency).getCurrencyCash());
        }

        result = result.concat("OK");
        log.info(result);
        return result;
    }

    public static String deposit(String currency, int noteValue, int noteCount){

        log.info("call deposit(" + currency + ", " + noteValue + ", " + noteCount + ")");

        if (noteCount >0) return getContainerByCurrency(currency).depositNotes(noteValue, noteCount);

        log.error("noteCount <= 0");
        return "ERROR";
    }

    public static String withdraw(String currency, int amount){

        log.info("call Storage.withdraw (" + currency + ", " + amount + ")");

        if(amount>0) return getContainerByCurrency(currency).withdrawAmount(amount);

        log.error("amount <= 0");
        return "ERROR";
    }

}