package ResourcesCountry;

import GetterMessanges.GetterMessanges;
import Repositories.DataContext;
import Repositories.UsersRepository;
import Repositories.SessionsRepository;
import Messages.Messages;
import Telegram.TGKeyboards;
import UserData.UserData;
import DataModels.UserDto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.SQLException;

public class Logic implements GetterMessanges {

    public SendMessage handlerMessage(Update up) {
        Message inMess = up.getMessage();
        String message = inMess.getText();
        String nickname = inMess.getChat().getUserName();
        message = message.toLowerCase();
        UserData.userChange(nickname, inMess.getChatId());

        SendMessage outMess = new SendMessage();
        outMess.setChatId(inMess.getChatId());
        switch (message) {
            case "/help": {
                outMess.setText(Messages.helpMessage);
            }
            case "экономика": {
                outMess.setText("Вы вошли в экономику");
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(1));
                break;
            }
            case "армия": {
                outMess.setText("Вы вошли в армию");
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(2));
                break;
            }
            case "население": {
                outMess.setText("Вы вошли в население");
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(3));
                break;
            }
            case "посмотреть ресурсы противника":{
                try
                {
                    DataContext dataContext = new DataContext();
                    UserDto userDto = UsersRepository.getByTelegramID(dataContext, UserData.list.get(nickname).getOpponentID());
                    String resourcesText = userDto.getResourcesText();
                    outMess.setText(resourcesText);
                    outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.getFirst());
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                    outMess.setText("Произошла ошибка");
                }
                break;
            }
            case "выйти из игры":{
                try
                {
                    DataContext dataContext = new DataContext();
                    SessionsRepository.delete(dataContext, Long.parseLong(UserData.list.get(nickname).getSessionID()));
                    UsersRepository.deleteByTelegramID(dataContext, UserData.list.get(nickname).getChatId());
                    UsersRepository.deleteByTelegramID(dataContext, UserData.list.get(nickname).getOpponentID());
                    outMess.setText("Игра завершена");
                    outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(5));
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                    outMess.setText("Произошла ошибка");
                }
                break;
            }
            default:{
                outMess.setText(Messages.unknownCommand);
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.getFirst());
            }
        }
        return outMess;
    }
}
