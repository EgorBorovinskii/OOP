import java.util.Scanner;

public class Economy {
    private float money;
    private int moneyForPower;
    private int moneyForLoyality;

    Economy(float _money, int money_for_power, int money_for_loyality)
    {
        this.money = _money;
        this.moneyForPower = money_for_power;
        this.moneyForLoyality = money_for_loyality;
    }

    public float getMoney()
    {
        return money;
    }

    public void setMoney(float addMoney)
    {
        this.money += addMoney;
    }

    public int getMoneyForPower(){return moneyForPower;}

    public int getMoneyForLoyality(){return moneyForLoyality;}

    private void showMoney()
    {
        System.out.println((int)money);
    }

    public void buyPower(){
        this.money -= this.moneyForPower;
        this.moneyForPower += 5;
    }

    public void buyLoyality(){
        this.money -= this.moneyForLoyality;
        this.moneyForLoyality += 5;
    }

    public String mainEconomy(String message)
    {
        Money.addMoney();
        switch (message) {
            case "/help": {
                break;
            }
            case "/exit": {
                Logic.exit();
                break;
            }
            case "показать количество денег": {
                return String.valueOf(getMoney());
            }
        }
        EventCheck.check();
        return Messages.unknownCommand;
    }
}

