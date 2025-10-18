import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EconomyTest {
    Economy economy = new Economy(20, 10, 10);

    @Test
    void buyPowerTest()
    {
        float expectedMoney = 10;
        economy.buyPower();
        Assertions.assertEquals(expectedMoney, economy.getMoney());
    }

    @Test
    void buyLoyalityTest()
    {
        float expectedMoney = 10;
        economy.buyLoyality();
        Assertions.assertEquals(expectedMoney, economy.getMoney());
    }
}