package Communication;

import Repositories.DataContext;
import Repositories.SessionsRepository;
import UserData.UserData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CheckConnect {
    public static Long waitPlayerTwoConnect(long sessionID)
    {
        try
        {
            var dataContext = new DataContext();
            var session = SessionsRepository.get(dataContext, sessionID);
            while (session == null || session.playerTwoID == 0)
            {
                session = SessionsRepository.get(dataContext, sessionID);
                Thread.sleep(1000);
            }
            SendMessage notice = new SendMessage();
            notice.setText("противник подключился");
            notice.setChatId(session.playerOneID);
            try{
                UserData.bot.execute(notice);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            return session.playerTwoID;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0L;
        }
    }
}
