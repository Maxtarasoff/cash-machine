import java.util.*;

public class CurrencyContainer {

    private final List<Integer> validValues = List.of(1, 5, 10, 50, 100, 500, 1000, 5000);
    private final Map<Integer, Integer> notes = new HashMap<>();
    private final String currency;

    public CurrencyContainer(String currency) {
        this.currency = currency;
    }

    public String getCurrencyBalance() {
        String result = "";
        ArrayList<Integer> listNotes = new ArrayList<>(notes.keySet());
        Collections.sort(listNotes);
        for (Integer note : listNotes){
            if (notes.get(note) > 0)
                result = result.concat(currency + " " + note + " " + notes.get(note) + "\n");
        }
        return result;
    }

    public String depositAmount(int note, int count){
        if (!validValues.contains(note)) return "ERROR";
        if (notes.containsKey(note)){
            notes.put(note, notes.get(note) + count);
        } else {
            notes.put(note, count);
        }
        return "OK";
    }

    public String withdrawAmount(int expectedAmount){

        String answer = "ERROR";

        if (expectedAmount > getTotalAmount()) {
            return answer;
        }
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>(notes.keySet());
        Collections.sort(list);
        Collections.reverse(list);

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
        if (expectedAmount > 0) return answer; // если после всех итераций вся сумма не выдана, то ERROR

        answer = "";
        for (Integer note : map.keySet()){
            int balance = notes.get(note)-map.get(note);
            notes.remove(note);
            notes.put(note, balance);
            answer = answer.concat(note + " " + map.get(note) + "\n");
        }

        answer = answer.concat("OK");

        return answer;
    }

    public int getTotalAmount(){
        int amount = 0;
    for (Integer note : notes.keySet())
            amount += note * notes.get(note);
        return amount;
    }

}