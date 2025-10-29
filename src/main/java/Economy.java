import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Scanner;

public class Economy implements GetterMessanges{
    private float money;
    private int moneyForPower;
    private int moneyForLoyality;

    Economy(float _money, int money_for_power, int money_for_loyality)
    {
        this.money = _money;
        this.moneyForPower = money_for_power;
        this.moneyForLoyality = money_for_loyality;
    }

    public float getMoney()
    {
        return money;
    }

    public void setMoney(float addMoney)
    {
        this.money += addMoney;
    }

    public int getMoneyForPower(){return moneyForPower;}

    public int getMoneyForLoyality(){return moneyForLoyality;}

    private void showMoney()
    {
        System.out.println((int)money);
    }

    public void buyPower(){
        this.money -= this.moneyForPower;
        this.moneyForPower += 5;
    }

    public void buyLoyality(){
        this.money -= this.moneyForLoyality;
        this.moneyForLoyality += 5;
    }

    public SendMessage getMess(Update up)
    {
        Message inMess = up.getMessage();
        String message = inMess.getText();
        String nickname = inMess.getChat().getUserName();
        message = message.toLowerCase();

        SendMessage outMess = new SendMessage();
        UserData.list.get(nickname).getMoney().addMoney(nickname);
        switch (message) {
            case "показать количество денег": {
                outMess.setText(String.valueOf(getMoney()));
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(1));
                break;
            }
            case"поднять налоги":{
                break;
            }
            case"назад":{
                outMess.setText("Главное меню");
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(0));
                break;
            }
            default:{
                outMess.setText(Messages.unknownCommand);
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(1));
            }
        }
        EventCheck.check();
        return outMess;
    }
}

