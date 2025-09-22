import java.util.Scanner;

public class Economy {
    private int money;
    private int moneyForPower;
    private int moneyForLoyality;

    Economy(int _money, int pow, int loal)
    {
        this.money = _money;
        this.moneyForPower = pow;
        this.moneyForLoyality = loal;
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int addMoney)
    {
        this.money += addMoney;
    }

    public int getMoneyForPower(){return moneyForPower;}

    public int getMoneyForLoyality(){return moneyForLoyality;}

    private void ShowMoney()
    {
        System.out.println(money);
    }

    public void buyPower(){
        this.money -= this.moneyForPower;
        this.moneyForPower += 5;
    }

    public void buyLoyality(){
        this.money -= this.moneyForLoyality;
        this.moneyForLoyality += 5;
    }

    public void MainEconomy()
    {
        Scanner in = new Scanner(System.in);
        System.out.println(Messages.categoriesForEconomy);
        String message = in.nextLine().toLowerCase();
        while (!message.equals("/back")) {
            switch ((message)) {
                case "1": {
                    message = "Показать количество денег";
                    break;
                }
            }
            switch (message) {
                case "/help": {
                    Logic.Help();
                    break;
                }
                case "/exit": {
                    Logic.Exit();
                    break;
                }
                case "Показать количество денег": {
                    ShowMoney();
                    break;
                }
                default: {
                    System.out.println(Messages.unknownCommand);
                    break;
                }
            }
            EventCheck.Check();
            Money.addMoney();
            message = in.nextLine();
        }
    }
}
