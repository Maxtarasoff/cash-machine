import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReaderTest {

    @Test
    @Order(1)
    void testRequestEmptyBalance(){
        String actual = Reader.execute("?");
        String expected = "OK";
        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void testLegalDepositUSD10_30(){
        String actual = Reader.execute("+ USD 100 30");
        String expected = "OK";
        assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    void testDepositWithWrongValue(){
        String actual = Reader.execute("+ RUR 250 10");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }

    @Test
    @Order(4)
    void testLegalDepositCHF100_5(){
        String actual = Reader.execute("+ CHF 100 5");
        String expected = "OK";
        assertEquals(expected, actual);
    }

    @Test
    @Order(5)
    void testLegalDepositUSD10_50(){
        String actual = Reader.execute("+ USD 10 50");
        String expected = "OK";
        assertEquals(expected, actual);
    }

    @Test
    @Order(6)
    void testSecondRequestBalance(){
        String actual = Reader.execute("?");
        String expected = "CHF 100 5\n" +
                "USD 10 50\n" +
                "USD 100 30\n" +
                "OK";
        assertEquals(expected, actual);
    }

    @Test
    @Order(7)
    void testLegalWithdrawUSD120(){
        String actual = Reader.execute("- USD 120");
        String expected = "100 1\n" +
                "10 2\n" +
                "OK";
        assertEquals(expected, actual);
    }

    @Test
    @Order(8)
    void testWithdrawAbsentCurrency(){
        String actual = Reader.execute("- RUR 500");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }

    @Test
    @Order(9)
    void testWithdrawAbsentValues(){
        String actual = Reader.execute("- CHF 250");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }

    @Test
    @Order(10)
    void testThirdRequestBalance(){
        String actual = Reader.execute("?");
        String expected = "CHF 100 5\n" +
                "USD 10 48\n" +
                "USD 100 29\n" +
                "OK";
        assertEquals(expected, actual);
    }

    @Test
    @Order(11)
    void testDepositWithLowerCaseCurrency(){
        String actual = Reader.execute("+ eur 100 5");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }

    @Test
    @Order(12)
    void testLegalWithdrawCHF400(){
        String actual = Reader.execute("- CHF 400");
        String expected = "100 4\n" +
                "OK";
        assertEquals(expected, actual);
    }

    @Test
    @Order(13)
    void testLegalDepositCHF10_50(){
        String actual = Reader.execute("+ CHF 10 50");
        String expected = "OK";
        assertEquals(expected, actual);
    }

    @Test
    @Order(14)
    void testFourthRequestBalance(){
        String actual = Reader.execute("?");
        String expected = "CHF 10 50\n" +
                "CHF 100 1\n" +
                "USD 10 48\n" +
                "USD 100 29\n" +
                "OK";
        assertEquals(expected, actual);
    }

    @Test
    @Order(15)
    void testWithdrawWithNotEnoughValues(){
        String actual = Reader.execute("- USD 3000");
        String expected = "100 29\n" +
                "10 10\n" +
                "OK";
        assertEquals(expected, actual);
    }

    @Test
    @Order(16)
    void testDepositExistValues(){
        String actual = Reader.execute("+ USD 10 10");
        String expected = "OK";
        assertEquals(expected, actual);
    }

    @Test
    void testEmptyCommand(){
        String actual = Reader.execute("");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }

    @Test
    void testExitCommand(){
        assertEquals("EXIT", Reader.execute("exit"));
    }

    @Test
    void testWrongCommand(){
        String actual = Reader.execute("wrong command");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }

    @Test
    void testDepositWithNegativeNumber(){
        String actual = Reader.execute("+ USD 100 -10");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }

    @Test
    void testDepositWithZeroNumber(){
        String actual = Reader.execute("+ USD 100 0");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }

    @Test
    void testWithdrawWithNegativeAmount(){
        String actual = Reader.execute("- USD -1000");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }

    @Test
    void testWithdrawWithZeroAmount(){
        String actual = Reader.execute("- USD 0");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }

    @Test
    void testTooManyArguments(){
        String actual = Reader.execute("+ USD 10 10 10");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }

    @Test
    void testNotEnoughArguments(){
        String actual = Reader.execute("+ USD 1000");
        String expected = "ERROR";
        assertEquals(expected, actual);
    }


}