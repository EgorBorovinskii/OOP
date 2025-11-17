package Communication;

import DataModels.Session;
import GetterMessanges.GetterMessanges;
import Repositories.DataContext;
import Repositories.SessionsRepository;
import Telegram.TGKeyboards;
import UserData.UserData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.SQLException;

public class Connect implements GetterMessanges {

    public Connect(){}

    public SendMessage handlerMessage(Update up)
    {
        Message inMess = up.getMessage();
        String message = inMess.getText();
        String nickname = inMess.getChat().getUserName();
        message = message.toLowerCase();
        UserData.userChange(nickname, inMess.getChatId());

        SendMessage outMess = new SendMessage();
        outMess.setChatId(inMess.getChatId());
        long sessionID = Long.parseLong(message);

        try
        {
            DataContext dataContext = new DataContext();
            Session session = SessionsRepository.get(dataContext, sessionID);
            if (session == null)
            {
                outMess.setText("Сессия не найдена");
                return outMess;
            }
            session.playerTwoID = inMess.getChatId();
            SessionsRepository.update(dataContext, session);
            UserData.list.get(nickname).setOpponentID(session.playerOneID);
            UserData.list.get(nickname).setSessionID(message);
            outMess.setText("Вы подключились");
            outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(0));
            return outMess;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            outMess.setText("Ошибка с подключением");
            return outMess;
        }
    }
}
