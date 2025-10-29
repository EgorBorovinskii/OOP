import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Army implements GetterMessanges {
    private Long power;
    private final int powerIncrease;

    Army(Long _power, int power_Increase)
    {
        this.power = _power;
        this.powerIncrease = power_Increase;
    }

    public void setPower(Long powerAdd)
    {
        this.power += powerAdd;
    }

    private String increasePower(String nick)
    {
        if (UserData.list.get(nick).getEconomy().getMoney() < UserData.list.get(nick).getEconomy().getMoneyForPower())
        {
            return Messages.notEnoughMoney + '\n' + Messages.needMoney + ": " + (UserData.list.get(nick).getEconomy().getMoneyForPower() - UserData.list.get(nick).getEconomy().getMoney());
        }
        else
        {
            UserData.list.get(nick).getEconomy().buyPower();
            power += powerIncrease;
            return "Успешно\nТекущая сила:" + GetPower();
        }
    }

    private long GetPower()
    {
        return power;
    }

    public SendMessage getMess(Update up)
    {

        Message inMess = up.getMessage();
        String message = inMess.getText();
        String nickname = inMess.getChat().getUserName();
        message = message.toLowerCase();
        UserData.list.get(nickname).getMoney().addMoney(nickname);

        SendMessage outMess = new SendMessage();
        switch (message) {
            case "увеличить силу": {
                outMess.setText(increasePower(nickname));
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(2));
                break;
            }
            case "показать уровень силы": {
                outMess.setText(String.valueOf(GetPower()));
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(2));
                break;
            }
            case "назад": {
                outMess.setText("Главное меню");
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(0));
                break;
            }
            default: {
                outMess.setText(Messages.unknownCommand);
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(2));
            }
        }
        return outMess;
    }
}

