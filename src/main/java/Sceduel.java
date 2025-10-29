import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Sceduel {
    private ScheduledExecutorService scheduel;
    private final AbsSender bot;

    Sceduel(AbsSender bot){
        this.bot = bot;
        scheduel = Executors.newScheduledThreadPool(1);
    }

    public void startEvents(){
        scheduel.scheduleAtFixedRate(this::sendEvents, 1, 1, TimeUnit.MINUTES);
    }

    private void sendEvents(){
        for(UserData.User u : UserData.list.values()){
            try {
                u.getEvents().messid = bot.execute(u.getEvents().getEvent(u.getChatId())).getMessageId();
            } catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

}
