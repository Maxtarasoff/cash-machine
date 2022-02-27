import org.apache.log4j.Logger;

import java.util.*;

public class CurrencyContainer {

    private final Logger log;
    private final List<Integer> validNoteValues = List.of(1, 5, 10, 50, 100, 500, 1000, 5000);
    private final Map<Integer, Integer> containerNotes = new HashMap<>();
    private final String currency;

    public CurrencyContainer(String currency) {
        this.currency = currency;
        log = Logger.getLogger("CurrencyContainer(" + currency + ")");
    }

    public String getCurrencyCash() {

        log.info("call getCurrencyBalance()");

        String result = "";
        ArrayList<Integer> listNotes = getSortedListOfNotes();

        for (Integer note : listNotes){
                result = result.concat(currency + " " + note + " " + containerNotes.get(note) + "\n");
        }

        return result;
    }

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

    private LinkedHashMap<Integer, Integer> getWithdrawNotes(int amount) {

        log.info("call getWithdrawNotes(" + amount + ")");

        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        ArrayList<Integer> listNotes = getSortedListOfNotes();
        Collections.reverse(listNotes);
        log.info("Available notes: " + listNotes);

        for (Integer note : listNotes) {
            int count = amount / note;      // запрашиваемую сумму делим на доступный номинал
            if (containerNotes.get(note) > count) {          // если доступно столько банкнот
                map.put(note, count);                   // записываем в карту это количество
                amount -= note * count;         // из запрашиваемой суммы вычитаем выданную сумму
            } else {                            // если банкнот меньше
                map.put(note, containerNotes.get(note));     // записываем в карту столько банкнот, сколько есть в банке
                amount -= note * containerNotes.get(note); // из запрашиваемой суммы вычитаем выданную сумму
            }
            if (amount == 0) break;     // если на данном этапе выбрали всю сумму, то останавливаем цикл
        }

        if (amount > 0) {
            log.error("there is no change in ATM (" + amount + ")");
            map.clear(); // если после всех итераций вся сумма не выдана, то очищаем карту
        }

        return map;
    }

    public int getTotalAmount(){
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