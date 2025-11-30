package ResourcesCountry;

import Telegram.TGKeyboards;
import UserData.UserData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import GetterMessanges.*;
import Messages.*;

public class Army implements GetterMessanges {
    private Long power;
    private final int powerIncrease;

    public Army(Long power, int powerIncrease)
    {
        this.power = power;
        this.powerIncrease = powerIncrease;
    }

    public void setPower(Long power)
    {
        this.power = power;
    }

    public void addPower(Long powerAdd)
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
            return "Успешно\nТекущая сила:" + getPower();
        }
    }

    public long getPower()
    {
        return this.power;
    }

    public SendMessage handlerMessage(Update up)
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
                outMess.setText(String.valueOf(getPower()));
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

