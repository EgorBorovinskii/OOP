import java.util.Scanner;

public class Economy {
    public static Scanner in = new Scanner(System.in);

    public static int money = 20;
    public static int moneyIncrease = 20;

    public static void IncreaseMoney()
    {
        money += moneyIncrease;
    }

    public static void ShowMoney()
    {
        System.out.println(money);
    }

    public static void MainEconomy()
    {
        System.out.println(Messages.categoriesForEconomy);
        String message = in.nextLine();
        while (!message.equals("/back"))
        {
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
            message = in.nextLine();
        }
    }
}
