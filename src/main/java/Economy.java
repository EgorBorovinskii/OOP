import java.util.Scanner;

public class Economy {
    private int money;
    private int moneyIncrease;
    private int moneyForPower;
    private int moneyForLoyality;

    Economy(int _money, int money_Increase, int pow, int loal)
    {
        this.money = _money;
        this.moneyIncrease = money_Increase;
        this.moneyForPower = pow;
        this.moneyForLoyality = loal;
    }

    public int getMoney()
    {
        return money;
    }

    public int getMoneyIncrease(){ return moneyIncrease; }

    public int getMoneyForPower(){return moneyForPower;}

    public int getMoneyForLoyality(){return moneyForLoyality;}

    private void IncreaseMoney()
    {
        money += moneyIncrease;
    }

    private void ShowMoney()
    {
        System.out.println(money);
    }

    public void buyPower(){
        this.money -= this.moneyForPower;
    }

    public void buyLoyality(){
        this.money -= this.moneyForLoyality;
    }

    public void MainEconomy()
    {
        Scanner in = new Scanner(System.in);
        System.out.println(Messages.categoriesForEconomy);
        String message = in.nextLine().toLowerCase();
        while (!message.equals("/back")) {
            switch ((message)) {
                case "1": {
                    message = "Добавить деньги";
                    break;
                }
                case "2": {
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
                case "Добавить деньги": {
                    IncreaseMoney();
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
            message = in.nextLine();
        }
    }
}
