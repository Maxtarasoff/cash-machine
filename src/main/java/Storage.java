import java.util.*;

public final class Storage {

    private static final Map<String, CurrencyContainer> CashMachine = new HashMap<>();

    public static CurrencyContainer getContainerByCurrency(String currency){
        CurrencyContainer container;
        if (CashMachine.containsKey(currency)){
            container = CashMachine.get(currency);
        } else {
            container = new CurrencyContainer(currency);
            CashMachine.put(currency, container);
        }
        return container;
    }

    public static String printCash() {
        String result = "";
        List<String> currencies = new ArrayList<>(CashMachine.keySet());
        Collections.sort(currencies);
        for (String currency : currencies) {
            result = result.concat(CashMachine.get(currency).getCurrencyBalance());
        }
        return result;
    }

    public static String deposit(String currency, int noteValue, int noteCount){
        if (noteCount >0) return getContainerByCurrency(currency).depositAmount(noteValue, noteCount);
        else return "ERROR";
    }

    public static String withdraw(String currency, int amount){
        if(amount>0) return getContainerByCurrency(currency).withdrawAmount(amount);
        else return "ERROR";
    }

}