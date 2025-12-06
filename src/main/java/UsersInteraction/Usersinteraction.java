package UsersInteraction;

import DataModels.UserDto;
import GetterMessanges.GetterMessanges;
import Messages.Messages;
import Repositories.DataContext;
import Repositories.UsersRepository;
import Telegram.TGKeyboards;
import UserData.UserData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

public class Usersinteraction implements GetterMessanges {
    private long shpionCooldown;
    private long warCooldown;
    private long ultimatunCooldown;


    public Usersinteraction() {
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
            case "нанять шпиона(75 денег)": {
                if (System.currentTimeMillis() - shpionCooldown > 120000) {
                    outMess = shpion(up);
                    shpionCooldown = System.currentTimeMillis();
                } else {
                    outMess.setText("Перезарядка");
                }
                break;
            }
            // case "предложить сделку": {
            // outMess = exchange(up);
            //   break;
            // }
            case "объявить войну": {
                if (System.currentTimeMillis() - warCooldown > 180000) {
                    outMess = war(up);
                    warCooldown = System.currentTimeMillis();
                } else {
                    outMess.setText("Перезарядка");
                }
                break;
            }
            case "поставить ультиматум": {
                if (System.currentTimeMillis() - ultimatunCooldown > 240000) {
                    outMess = ultimatum(up);
                } else {
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
        return outMess;
    }

    public SendMessage shpion(Update up) {
        String nickname = up.getMessage().getChat().getUserName();
        Long id = up.getMessage().getChatId();
        SendMessage outMess = new SendMessage();
        outMess.setChatId(id);
        try {
            DataContext dataContext = new DataContext();

            UserDto user = UsersRepository.getByTelegramID(dataContext, UserData.list.get(nickname).getChatId());
            UserData.list.get(nickname).getEconomy().setMoney(UserData.list.get(nickname).getEconomy().getMoney() - 75);
            user.money = UserData.list.get(nickname).getEconomy().getMoney();
            UsersRepository.updateByTelegramID(dataContext, user);
            UserDto userDto = UsersRepository.getByTelegramID(dataContext, UserData.list.get(nickname).getOpponentID());
            String resourcesText = userDto.getResourcesText();
            outMess.setText(resourcesText);

            SendMessage warningOpponent = new SendMessage();
            warningOpponent.setChatId(UserData.list.get(nickname).getOpponentID());
            warningOpponent.setText("Враг подослал к вам шпиона!\n Враг знает сколько у вас ресурсов");
            try {
                UserData.bot.execute(warningOpponent);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            outMess.setText("Произошла ошибка");
        }
        return outMess;
    }

    // public String exchange(String nick){
    //   return null;
    //}

    public SendMessage war(Update up) {
        String nickname = up.getMessage().getChat().getUserName();
        Long id = up.getMessage().getChatId();
        SendMessage outMess = new SendMessage();
        outMess.setChatId(id);
        try {
            DataContext dataContext = new DataContext();

            UserDto user = UsersRepository.getByTelegramID(dataContext, UserData.list.get(nickname).getChatId());
            UserDto opponent = UsersRepository.getByTelegramID(dataContext, UserData.list.get(nickname).getOpponentID());

            double warCoefUser = user.power * Math.log(Math.max(0.01, (double) user.loyalty)) / Math.log(15.0) + user.money * 0.2;
            double warCoefOpponent = opponent.power * Math.log(Math.max(0.01, (double) opponent.loyalty)) / Math.log(15.0) + opponent.money * 0.2;
            double rawProbability = warCoefUser / (warCoefUser + warCoefOpponent);
            double probability = Math.max(0.05, Math.min(0.95, rawProbability));

            double value = Math.random();

            if (value < probability) {
                outMess = userWon(up, user, opponent);
            } else {
                outMess = userLose(up, user, opponent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            outMess.setText("Ошибка");
        }
        return outMess;
    }

    public SendMessage ultimatum(Update up) {
        return null;
    }

    private SendMessage userWon(Update up, UserDto user, UserDto opponent){
        SendMessage outMess =  new SendMessage();
        outMess.setChatId(up.getMessage().getChatId());

        float fmoneyUser = user.money;
        float fmoneyOpponent = opponent.money;
        user.money = (float)(user.money * 0.8);
        opponent.money = (float)(opponent.money * 0.8);
        user.money += (float)(opponent.money * 0.25 + fmoneyOpponent * 0.05);
        opponent.money = (float)(opponent.money * 0.75);

        long fpowerUser = user.power;
        long fpowerOpponent = opponent.power;
        user.power = (long)(user.power*0.8);
        opponent.power = (long)(opponent.power*0.6);

        user.loyalty += 5;
        opponent.loyalty -= 3;

        UserData.compareWithBD(user);
        UserData.compareWithBD(opponent);

        outMess.setText("Вы Победили! Получено:\n" + (int)(user.money - fmoneyUser) + " Денег\n5 Лояльности\n\nПотеряно:\n" + (int)(fpowerUser - user.power) + " Мощи");

        SendMessage oppNotice = new SendMessage();
        oppNotice.setChatId(opponent.telegramID);
        oppNotice.setText("Противник напал на вас и вы потерпели поражение! Потеряно:\n" + (int)(fmoneyOpponent - opponent.money) + " Денег\n3 Лояльности\n" + (int)(fpowerOpponent - opponent.power));
        try{
            UserData.bot.execute(oppNotice);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return outMess;
    }

    private SendMessage userLose(Update up, UserDto user, UserDto opponent){
        SendMessage outMess =  new SendMessage();
        outMess.setChatId(up.getMessage().getChatId());

        float fmoneyUser = user.money;
        float fmoneyOpponent = opponent.money;
        user.money = (float)(user.money * 0.8);
        opponent.money = (float)(opponent.money * 0.8);
        opponent.money += (float)(user.money * 0.29 + fmoneyUser * 0.07);
        user.money = (float)(user.money * 0.71);

        long fpowerUser = user.power;
        long fpowerOpponent = opponent.power;
        user.power = (long)(user.power*0.7);
        opponent.power = (long)(opponent.power*0.7);

        user.loyalty -= 6;
        opponent.loyalty += 6;

        UserData.compareWithBD(user);
        UserData.compareWithBD(opponent);

        outMess.setText("Вы позорно проиграли! Потеряно:\n" + (int)(0 - user.money + fmoneyUser) + " Денег\n6 Лояльности\n" + (int)(fpowerUser - user.power) + " Мощи");

        SendMessage oppNotice = new SendMessage();
        oppNotice.setChatId(opponent.telegramID);
        oppNotice.setText("Противник напал на вас и вы героически отбились поражение! Получено:\n" + (int)(0 - fmoneyOpponent + opponent.money) + " Денег\n6 Лояльности\n\nПотеряно:" + (int)(fpowerOpponent - opponent.power) + " Мощи");
        try{
            UserData.bot.execute(oppNotice);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return outMess;
    }
}
