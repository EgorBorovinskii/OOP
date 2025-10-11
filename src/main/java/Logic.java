import java.util.Scanner;

public class Logic {
    public void start()
    {
        UserData data = new UserData();
        System.out.println(Messages.firstMessage + Messages.whatIsYourName);
        Scanner in = new Scanner(System.in);
        String nickname = in.nextLine();
        data.userChange(nickname);
        while (true)
        {
            System.out.println(Messages.categories);
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
                case "4":{
                    message = "сменить пользователя";
                }
            }
            switch (message)
            {
                case "/exit":
                {
                    exit();
                    break;
                }
                case "/help":
                {
                    help();
                    break;
                }
                case "экономика":
                {
                    UserData.currentUser.getEconomy().mainEconomy();
                    break;
                }
                case "армия":
                {
                    UserData.currentUser.getArmy().mainArmy();
                    break;
                }
                case "население":
                {
                    UserData.currentUser.getPopulation().mainPopulation();
                    break;
                }
                case "сменить пользователя":
                {
                    System.out.println(Messages.whatIsYourName);
                    nickname = in.nextLine();
                    EventCheck.updateTime();
                    Money.addMoney();
                    data.userChange(nickname);
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

    public static void help()
    {
        System.out.println(Messages.helpMessage);
    }

    public static void exit()
    {
        System.exit(0);
    }
}
