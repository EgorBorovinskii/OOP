import java.util.Scanner;

public class Army {
    public static Scanner in = new Scanner(System.in);

    public static int power = 10;
    public static int needMoney = 5;
    public static int powerIncrease = 5;

    public static void IncreasePower()
    {
        if (Economy.money < needMoney)
        {
            System.out.println(Messages.notEnoughMoney);
            System.out.println(Messages.needMoney + ": " + needMoney);
        }
        else
        {
            Economy.money -= needMoney;
            power += powerIncrease;
        }
    }

    public static void ShowPower()
    {
        System.out.println(power);
    }

    public static void MainArmy()
    {
        System.out.println(Messages.categoriesForArmy);
        String message = in.nextLine().toLowerCase();
        while (!message.equals("/back"))
        {
            switch ((message)){
                case "1":
                {
                    message = "Увеличить силу";
                    break;
                }
                case "2":
                {
                    message = "Показать уровень силы";
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
                case "Увеличить силу":
                {
                    IncreasePower();
                    break;
                }
                case "Показать уровень силы":
                {
                    ShowPower();
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
    }
}
