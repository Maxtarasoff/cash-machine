import org.apache.log4j.Logger;

import java.util.*;

public class CurrencyContainer {

    private final Logger log;
    private final List<Integer> validValues = List.of(1, 5, 10, 50, 100, 500, 1000, 5000);
    private final Map<Integer, Integer> notes = new HashMap<>();
    private final String currency;

    public CurrencyContainer(String currency) {
        this.currency = currency;
        log = Logger.getLogger("CurrencyContainer(" + currency + ")");
    }

    public String getCurrencyBalance() {

        log.info("call getCurrencyBalance()");

        String result = "";
        ArrayList<Integer> listNotes = getSortedListOfNotes();

        for (Integer note : listNotes){
            if (notes.get(note) > 0)
                result = result.concat(currency + " " + note + " " + notes.get(note) + "\n");
        }

        return result;
    }

    public String depositAmount(int note, int count){
        log.info("call depositAmount(" + note + ", " + count + ")");
        if (!validValues.contains(note)) {
            log.error( note + " not in " + validValues.toString());
            return "ERROR";
        }
        if (notes.containsKey(note)){
            notes.put(note, notes.get(note) + count);
        } else {
            notes.put(note, count);
        }
        log.info("Success deposit");
        return "OK";
    }

    public String withdrawAmount(int expectedAmount){
        log.info("call withdrawAmount (" + expectedAmount + ")");
        String result = "ERROR";

        if (expectedAmount > getTotalAmount()) {
            log.error("expectedAmount(" + expectedAmount + ") > totalAmount (" + getTotalAmount() + ")");
            return result;
        }
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        ArrayList<Integer> list = getSortedListOfNotes();
        Collections.reverse(list);
        log.info("Available notes " + list.toString());

        for (Integer note : list) {
            int x = expectedAmount / note;      // х = запрашиваемую сумму делим на доступный номинал
            if (notes.get(note) > x) {          // если доступно х банкнот
                map.put(note, x);                   // записываем в карту х банкнот
                expectedAmount -= note * x;         // из запрашиваемой суммы вычитаем выданную сумму
            } else {                            // если банкнот меньше
                map.put(note, notes.get(note));     // записываем в карту столько банкнот, сколько есть в банке
                expectedAmount -= note * notes.get(note); // из запрашиваемой суммы вычитаем выданную сумму
            }
            if (expectedAmount == 0) break;     // если на данном этапе выбрали всю сумму, то останавливаем цикл
        }
        if (expectedAmount > 0) {
            log.error("there is no change in ATM (" + expectedAmount + ")");
            return result; // если после всех итераций вся сумма не выдана, то ERROR
        }

        result = "";

        for (Integer note : map.keySet()){
            int balance = notes.get(note)-map.get(note);
            notes.remove(note);
            if (balance > 0) notes.put(note, balance);
            result = result.concat(note + " " + map.get(note) + "\n");
        }

        result = result.concat("OK");
        log.info(result);
        return result;
    }

    public int getTotalAmount(){
        int amount = 0;
    for (Integer note : notes.keySet())
            amount += note * notes.get(note);
        return amount;
    }

    private ArrayList<Integer> getSortedListOfNotes() {
        ArrayList<Integer> listNotes = new ArrayList<>(notes.keySet());
        Collections.sort(listNotes);
        return listNotes;
    }

}