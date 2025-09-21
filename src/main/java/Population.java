import java.util.Scanner;

public class Population {
    public static Scanner in = new Scanner(System.in);
    String st = new String("НАЧАЛЬНИК ХУЕСОС");

    public static int loyalty = 20;
    public static int needMoney = 5;
    public static int loyaltyIncrease = 5;

    public static void IncreaseLoyalty()
    {
        if (Economy.money < needMoney)
        {
            System.out.println(Messages.notEnoughMoney);
            System.out.println(Messages.needMoney + ": " + needMoney);
        }
        else
        {
            Economy.money -= needMoney;
            loyalty += loyaltyIncrease;
        }
    }

    public static void ShowLoyalty()
    {
        System.out.println(loyalty);
    }

    public static void MainPopulation()
    {
        System.out.println(Messages.categoriesForPopulation);
        String message = in.nextLine();
        while (!message.equals("/back")) {
            switch (message) {
                case "/help": {
                    Logic.Help();
                    break;
                }
                case "/exit": {
                    Logic.Exit();
                    break;
                }
                case "Увеличить лояльность": {
                    IncreaseLoyalty();
                    break;
                }
                case "Показать уровень лояльности": {
                    ShowLoyalty();
                    break;
                }
                default: {
                    System.out.println(Messages.unknownCommand);
                    break;
                }
            }
            message = in.nextLine();
        }
    }
}
