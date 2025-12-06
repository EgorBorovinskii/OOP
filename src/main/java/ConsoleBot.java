import Repositories.DataContext;
import Telegram.TGKeyboards;
import Telegram.TGbot;
import UserData.UserData;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.SQLException;

public class ConsoleBot {
    public static void main(String[] arguments)
    {
        try {
            DataContext.obtainDatabase();
            TGKeyboards Keyboards = new TGKeyboards();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            TGbot telegramBot = new TGbot();
            UserData.setBot(telegramBot);
            telegramBotsApi.registerBot(telegramBot);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}