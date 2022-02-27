import org.apache.log4j.Logger;
import java.util.*;

/**
 * Individual container for each currency.
 * Stores information about numbers of available notes.
 * Provides public methods to get balance, deposit, withdraw.
 * @see #getBalance()
 * @see #depositNotes(int, int)
 * @see #withdrawAmount(int)
 */

public class CurrencyContainer {

    private final Logger log;
    private final List<Integer> validNoteValues = List.of(1, 5, 10, 50, 100, 500, 1000, 5000);
    private final Map<Integer, Integer> containerNotes = new HashMap<>();
    private final String currency;

    public CurrencyContainer(String currency) {
        this.currency = currency;
        log = Logger.getLogger("CurrencyContainer(" + currency + ")");
    }

    /** Gets what is currently in the cash.
     *
     * @return
     * Lines are ordered by note value, from less to greater.
     * Followed by the line OK
     * For example:
     * <br>USD 10 50
     * <br>USD 100 30
     * <br>OK
     */

    public String getBalance() {

        log.info("call getBalance()");

        String result = "";
        ArrayList<Integer> listNotes = getSortedListOfNotes();

        for (Integer note : listNotes){
                result = result.concat(currency + " " + note + " " + containerNotes.get(note) + "\n");
        }

        return result;
    }

    /**
     * Add notes to container
     * @param note  value of notes
     * @param count count of notes
     * @return OK on success
     * <br>ERROR if value not included in validNoteValues
     * @see CurrencyContainer#validNoteValues
     */

    public String depositNotes(int note, int count){

        log.info("call depositNotes(" + note + ", " + count + ")");

        if (!validNoteValues.contains(note)) {
            log.error( note + " not in " + validNoteValues);
            return "ERROR";
        }

        if (containerNotes.containsKey(note)){
            containerNotes.put(note, containerNotes.get(note) + count);
        } else {
            containerNotes.put(note, count);
        }

        log.info("Success deposit");
        return "OK";
    }

    /**
     * Try to withdraw amount from container.
     * @param amount - requested amount of money.
     * @return Strings of value-count pair on success, followed by OK
     * for example:
     * withdrawAmount(250)
     * <br>100 2
     * <br>50 1
     * <br>OK
     * <br>or ERROR on fail
     */

    public String withdrawAmount(int amount){

        log.info("call withdrawAmount(" + amount + ")");

        if (amount > getTotalAmount()) {
            log.error("amount(" + amount + ") > totalAmount (" + getTotalAmount() + ")");
            return "ERROR";
        }

        LinkedHashMap<Integer, Integer> withdrawNotes = getWithdrawNotes(amount);
        if (withdrawNotes.isEmpty()) return "ERROR";

        String result = "";

        for (Integer note : withdrawNotes.keySet()){
            int remains = containerNotes.get(note) - withdrawNotes.get(note);
            containerNotes.remove(note);
            if (remains > 0) containerNotes.put(note, remains);
            result = result.concat(note + " " + withdrawNotes.get(note) + "\n");
        }

        result = result.concat("OK");
        log.info(result);
        return result;
    }

    /**
     * Find way to withdraw requested amount from container.
     * @param amount requested amount of money
     * @return Linked Map of note-count pair of notes, necessary to get requested amount.
     * Map sorted by key(note) from greater to less.
     * <br>Empty map on fail
     */

    private LinkedHashMap<Integer, Integer> getWithdrawNotes(int amount) {

        log.info("call getWithdrawNotes(" + amount + ")");

        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        ArrayList<Integer> listNotes = getSortedListOfNotes();
        Collections.reverse(listNotes);
        log.info("Available notes: " + listNotes);

        for (Integer note : listNotes) {
            int count = amount / note;
            if (containerNotes.get(note) > count) {
                map.put(note, count);
                amount -= note * count;
            } else {
                map.put(note, containerNotes.get(note));
                amount -= note * containerNotes.get(note);
            }
            if (amount == 0) break;
        }

        if (amount > 0) {
            log.error("there is no change in ATM (" + amount + ")");
            map.clear();
        }

        return map;
    }

    private int getTotalAmount(){
        int amount = 0;
    for (Integer note : containerNotes.keySet())
            amount += note * containerNotes.get(note);
        return amount;
    }

    private ArrayList<Integer> getSortedListOfNotes() {
        ArrayList<Integer> listNotes = new ArrayList<>(containerNotes.keySet());
        Collections.sort(listNotes);
        return listNotes;
    }

}