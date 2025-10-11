import java.util.Scanner;

public class Logic {
    private UserData data;
    private int state;
    public Logic(){
        data = new UserData();
        state = 0;
    }
    public String Answer(String message, String nickname)
    {
        if(message.equals("/start")){
            return Messages.firstMessage + Messages.whatIsYourName;
        }
        System.out.println(Messages.categories);
        message = message.toLowerCase();
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
                return Messages.helpMessage;
            }
            case "экономика":
            {
                UserData.currentUser.getEconomy().mainEconomy();
            }
            case "армия":
            {
                UserData.currentUser.getArmy().mainArmy();
            }
            case "население":
            {
                UserData.currentUser.getPopulation().mainPopulation();
            }
        }
        return Messages.unknownCommand;
    }


    public static void exit()
    {
        System.exit(0);
    }
}
