import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Scanner;

public class Population implements GetterMessanges{

    private Long loyalty;
    private int loyaltyIncrease;

    Population(Long _loyalty, int loyalty_Increase){
        this.loyalty = _loyalty;
        this.loyaltyIncrease = loyalty_Increase;
    }

    public void setLoyalty(Long loyaltyAdd)
    {
        this.loyalty += loyaltyAdd;
    }

    private String increaseLoyalty(String nick)
    {
        if (UserData.list.get(nick).getEconomy().getMoney() < UserData.list.get(nick).getEconomy().getMoneyForLoyality())
        {
            return Messages.notEnoughMoney + '\n' + Messages.needMoney + ": " + UserData.list.get(nick).getEconomy().getMoneyForLoyality();
        }
        else
        {
            UserData.list.get(nick).getEconomy().buyLoyality();
            loyalty += loyaltyIncrease;
            return "Успешно\nТекущая лояльность: " + String.valueOf(GetLoyalty());
        }
    }

    private long GetLoyalty()
    {
        return loyalty;
    }

    public SendMessage getMess(Update up)
    {
         Message inMess = up.getMessage();
         String message = inMess.getText();
         String nick = inMess.getChat().getUserName();
         message = message.toLowerCase();

         SendMessage outMess = new SendMessage();
         UserData.list.get(nick).getMoney().addMoney(nick);
         switch (message) {
             case "увеличить лояльность": {
                 outMess.setText(increaseLoyalty(nick));
                 outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(3));
                 break;
             }
             case "показать уровень лояльности": {
                 outMess.setText(String.valueOf(GetLoyalty()));
                 outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(3));
                 break;
             }
             case"назад":{
                 outMess.setText("Главное меню");
                 outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(0));
                 break;
             }
             default: {
                 outMess.setText(Messages.unknownCommand);
                 outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(3));
             }
         }
         EventCheck.check();
         return outMess;
    }
}

