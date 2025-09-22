import java.util.Scanner;

public class Economy {
    private int money;
    private int moneyIncrease;

    Economy(int mone, int moneyIncr)
    {
        this.money = mone;
        this.moneyIncrease = moneyIncr;
    }

    public int getMoney()
    {
        return money;
    }

    private void IncreaseMoney()
    {
        money += moneyIncrease;
    }

    private void ShowMoney()
    {
        System.out.println(money);
    }

    private void MainEconomy()
    {
        Scanner in = new Scanner(System.in);
        System.out.println(Messages.categoriesForEconomy);
        String message = in.nextLine().toLowerCase();
        while (!message.equals("/back"))
        {
            switch ((message)){
                case "1":
                {
                    message = "Добавить деньги";
                    break;
                }
                case "2":
                {
                    message = "Показать количество денег";
                    break;
                }
            }
            switch (message)
            {
                case "/help":
                {
                    Logic.Help();
                    break;
                }
                case "/exit":
                {
                    Logic.Exit();
                    break;
                }
                case "Добавить деньги":
                {
                    IncreaseMoney();
                    break;
                }
                case "Показать количество денег":
                {
                    ShowMoney();
                    break;
                }
                default:
                {
                    System.out.println(Messages.unknownCommand);
                    break;
                }
            }
            EventCheck.Check();
            message = in.nextLine();
        }
        in.close();
    }
}
