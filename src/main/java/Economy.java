import java.util.Scanner;

public class Economy {
    private float money;
    private int moneyForPower;
    private int moneyForLoyality;

    Economy(float _money, int pow, int loal)
    {
        this.money = _money;
        this.moneyForPower = pow;
        this.moneyForLoyality = loal;
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

    public void mainEconomy()
    {
        Scanner in = new Scanner(System.in);
        System.out.println(Messages.categoriesForEconomy);
        String message = in.nextLine().toLowerCase();
        while (!message.equals("/back")) {
            Money.addMoney();
            switch ((message)) {
                case "1": {
                    message = "Показать количество денег";
                    break;
                }
            }
            switch (message) {
                case "/help": {
                    Logic.help();
                    break;
                }
                case "/exit": {
                    Logic.exit();
                    break;
                }
                case "Показать количество денег": {
                    showMoney();
                    break;
                }
                default: {
                    System.out.println(Messages.unknownCommand);
                    break;
                }
            }
            EventCheck.check();
            message = in.nextLine();
        }
    }
}
