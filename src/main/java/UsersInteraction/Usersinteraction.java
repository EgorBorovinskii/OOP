package UsersInteraction;

import GetterMessanges.GetterMessanges;
import Messages.Messages;
import Telegram.TGKeyboards;
import UserData.UserData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Usersinteraction implements GetterMessanges {
    private long shpionCooldown;
    private long warCooldown;
    private long ultimatunCooldown;

    public Usersinteraction(){
        shpionCooldown = System.currentTimeMillis();
        warCooldown = System.currentTimeMillis();
        ultimatunCooldown = System.currentTimeMillis();
    }
    public SendMessage handlerMessage(Update up) {
        Message inMess = up.getMessage();
        String message = inMess.getText();
        String nickname = inMess.getChat().getUserName();
        message = message.toLowerCase();
        UserData.userChange(nickname, inMess.getChatId());

        SendMessage outMess = new SendMessage();
        outMess.setChatId(inMess.getChatId());

        UserData.list.get(nickname).getMoney().addMoney(nickname);
        switch (message) {
            case "нанять шпиона(500 денег)": {
                if(System.currentTimeMillis() - shpionCooldown > 120000){
                    outMess.setText(shpion(nickname));
                }
                else{
                    outMess.setText("Перезарядка");
                }
                break;
            }
            case "предложить сделку": {
                outMess.setText(exchange(nickname));
                break;
            }
            case "объявить войну": {
                if(System.currentTimeMillis() - warCooldown > 180000){
                    outMess.setText(war(nickname));
                }
                else{
                    outMess.setText("Перезарядка");
                }
                break;
            }
            case "поставить ультиматум": {
                if(System.currentTimeMillis() - ultimatunCooldown > 240000){
                    outMess.setText(ultimatum(nickname));
                }
                else{
                    outMess.setText("Перезарядка");
                }
                break;
            }
            case "назад": {
                outMess.setText("Главное меню");
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(0));
                break;
            }
            default: {
                outMess.setText(Messages.unknownCommand);
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(1));
            }
        }
        outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.getLast());
        return  outMess;
    }

    public String shpion(String nick){
        return null;
    }

    public String exchange(String nick){
        return null;
    }

    public String war(String nick){
        return null;
    }

    public String ultimatum(String nick){
        return null;
    }



}
