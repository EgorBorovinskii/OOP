public class Logic implements GetterMessanges {
    private UserData data;
    private int state;
    public Logic(){
        data = new UserData();
        state = 0;
    }
    public String getMess(String message, String nickname) {
        if (message.equals("/start")) {
            return Messages.firstMessage + Messages.whatIsYourName;
        }
        message = message.toLowerCase();
        data.userChange(nickname);
        if (EventCheck.check()){
            state = 4;
            return UserData.currentUser.getEvents().getEvent();
        }
        if(message.equals("назад")){
            state = 0;
            return "Главное меню";
        }
        switch (state) {
            case 0: {
                switch (message) {
                    case "/exit": {
                        exit();
                        break;
                    }
                    case "/help": {
                        return Messages.helpMessage;
                    }
                    case "экономика": {
                        state = 1;
                        return "вы вошли в экономику";
                    }
                    case "армия": {
                        state = 2;
                        return "вы вошли в армию";
                    }
                    case "население": {
                        state = 3;
                        return "вы вошли в население";
                    }
                }
                return Messages.unknownCommand;
            }
            case 1:
                return UserData.currentUser.getEconomy().mainEconomy(message);
            case 2:
                return UserData.currentUser.getArmy().mainArmy(message);
            case 3:
                return UserData.currentUser.getPopulation().mainPopulation(message);
            case 4:
                state = 0;
                return UserData.currentUser.getEvents().doEvent(message);
        }
        return Messages.unknownCommand;
    }

    public void swap(String message, String nickname){
        switch (message) {
            case "экономика\uD83D\uDCB0": {
                state = 1;
            }
            case "армия": {
                state = 2;
            }
            case "население": {
                state = 3;
            }
        }
    }


    public static void exit()
    {
        System.exit(0);
    }

    public int getState() { return state;}
}
