import java.util.Scanner;

public class Logic {
    public void Start()
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
                    message = "смена пользователя";
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
                    UserData.currentUser.getEconomy().MainEconomy();
                    break;
                }
                case "армия":
                {
                    UserData.currentUser.getArmy().MainArmy();
                    break;
                }
                case "население":
                {
                    UserData.currentUser.getPopulation().MainPopulation();
                    break;
                }
                case "смена пользователя":{
                    //тебе дописать
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
