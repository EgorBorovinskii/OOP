package ResourcesCountry;

import Communication.CheckConnect;
import DataModels.Session;
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

public class MainMenu implements GetterMessanges {

    public SendMessage handlerMessage(Update up)
    {
        Message inMess = up.getMessage();
        String message = inMess.getText();
        String nickname = inMess.getChat().getUserName();
        message = message.toLowerCase();
        UserData.userChange(nickname, inMess.getChatId());

        SendMessage outMess = new SendMessage();
        outMess.setChatId(inMess.getChatId());
        try
        {
            DataContext dataContext = new DataContext();
            UserDto user = UsersRepository.getByTelegramID(dataContext, inMess.getChatId());
            if (user == null) {
                user = new UserDto(inMess.getChatId(), 20, 0, 10);
                UsersRepository.add(dataContext, user);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            outMess.setText("Произошла ошибка");
            return outMess;
        }
        switch (message)
        {
            case "создать комнату":
            {
                long sessionID = creatSession(inMess);
                if (sessionID == 0)
                {
                    outMess.setText("Не удалось создать комнату");
                    break;
                }
                outMess.setText("Комната создана.\nID комнаты: " + sessionID);
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(0));
                break;
            }
            case "подключиться к комнате":
            {
                outMess.setText("Введите id комнаты");
                break;
            }
            default:
            {
                outMess.setText(Messages.unknownCommand);
                outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(5));
            }
        }
        return outMess;
    }

    public long creatSession(Message message)
    {
        try
        {
            String nickname = message.getChat().getUserName();
            DataContext dataContext = new DataContext();
            Session session = new Session(message.getChatId(), 0);
            SessionsRepository.add(dataContext, session);
            Runnable task = ()->{
                long opponentID = CheckConnect.waitPlayerTwoConnect(session.id);
                UserData.list.get(nickname).setOpponentID(opponentID);
            };
            var t = new Thread(task);
            t.start();
            UserData.list.get(nickname).setSessionID(Long.toString(session.id));
            return session.id;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
}
