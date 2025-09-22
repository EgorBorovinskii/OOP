import java.util.Scanner;

public class Logic {
    public static void Start()
    {
        UserData user = new UserData();
        System.out.println(Messages.firstMessage + Messages.whatIsYourName);
        Scanner in = new Scanner(System.in);
        String nickname = in.nextLine();
        user.userChange(nickname);
        while (true)
        {
            System.out.println(Messages.categories);
            startTime = System.currentTimeMillis();
            String message = in.nextLine().toLowerCase();
            switch ((message)){
                case "1":
                {
                    message = "экономика";
                    break;
                }
                case "2":
                {
                    message = "армия";
                    break;
                }
                case "3": {
                    message = "население";
                    break;
                }
            }
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
                case "экономика":
                {
                    Economy.MainEconomy();
                    break;
                }
                case "армия":
                {
                    Army.MainArmy();
                    break;
                }
                case "население":
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
