package Money;

import Telegram.TGKeyboards;
import UserData.UserData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Money {
    private long startTime = System.currentTimeMillis();
    private int moneyIncrease;
    private final float needTime;

    public Money(int inc, int time){
        moneyIncrease = inc;
        needTime = time;
    }

    public SendMessage raiseTaxes(Update up)
    {
        Message inMess = up.getMessage();
        String message = inMess.getText();
        String nickname = inMess.getChat().getUserName();
        message = message.toLowerCase();

        SendMessage outMess = new SendMessage();

        Long loyalty = UserData.list.get(nickname).getPopulation().getLoyalty();
        if (loyalty > 2)
        {
            this.moneyIncrease += 6;
            UserData.list.get(nickname).getPopulation().setLoyalty(loyalty - 3);
            outMess.setText("Налоги увеличены.\nЛюди негодуют (-3 лояльности)");
        }
        else
        {
            outMess.setText("Люди не согласны(лояльность слишком низкая)");
        }
        outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(1));
        return outMess;
    }

    public void addMoney(String nick)
    {
        long endTime = System.currentTimeMillis();
        long differenceTime = endTime - startTime;
        UserData.list.get(nick).getEconomy().addMoney(moneyIncrease * (differenceTime / needTime));
        startTime = System.currentTimeMillis();
    }
}
