package UsersInteraction;

import Repositories.SessionsRepository;
import UserData.UserData;
import org.telegram.telegrambots.meta.api.objects.Update;
import DataModels.UserDto;
import GetterMessanges.GetterMessanges;
import Messages.Messages;
import Repositories.DataContext;
import Repositories.SessionsRepository;
import Repositories.UsersRepository;
import Telegram.TGKeyboards;
import UserData.UserData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import Repositories.SessionsRepository;

import java.sql.SQLException;

public class EndGame {
    public static void endGame(Update up){
        String nickname = up.getMessage().getChat().getUserName();
        SendMessage outMess = new SendMessage();
        try{
            DataContext dataContext = new DataContext();

            UserDto user = UsersRepository.getByTelegramID(dataContext, UserData.list.get(nickname).getChatId());
            UserDto opponent = UsersRepository.getByTelegramID(dataContext, UserData.list.get(nickname).getOpponentID());

            Long sessionId = SessionsRepository.getSessionIDbyUser(dataContext, user);
            SessionsRepository.delete(dataContext, sessionId);
            UsersRepository.deleteByTelegramID(dataContext, user.telegramID);
            UsersRepository.deleteByTelegramID(dataContext, opponent.telegramID);
            UserData.deleteUser(user);
            UserData.deleteUser(opponent);

            outMess.setChatId(opponent.telegramID);
            outMess.setText("Противник вышел");
            outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(5));
            try {
                UserData.bot.execute(outMess);
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
