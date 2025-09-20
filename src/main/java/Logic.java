import java.util.Scanner;

public class Logic {
    public static void Start()
    {
        System.out.println(Messages.firstMessage);
        Scanner in = new Scanner(System.in);
        while (true)
        {
            System.out.println(Messages.categories);
            String message = in.nextLine();
            switch (message)
            {
                case "/exit":
                {
                    Exit();
                    break;
                }
                case "/help":
                {
                    Help();
                    break;
                }
                case "Экономика":
                {
                    Economy.MainEconomy();
                    break;
                }
                case "Армия":
                {
                    Army.MainArmy();
                    break;
                }
                case "Население":
                {
                    Population.MainPopulation();
                    break;
                }
                default:
                {
                    System.out.println(Messages.unknownCommand);
                    break;
                }
            }
        }
    }

    public static void Help()
    {
        System.out.println(Messages.helpMessage);
    }

    public static void Exit()
    {
        System.exit(0);
    }
}
