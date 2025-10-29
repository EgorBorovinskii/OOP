import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Sceduel {
    private final ScheduledExecutorService scheduel;
    private final AbsSender bot;

    Sceduel(AbsSender bot){
        this.bot = bot;
        scheduel = Executors.newScheduledThreadPool(1);
    }

    public void startEvents(){
        scheduel.scheduleAtFixedRate(this::sendEvents, 1, 1, TimeUnit.MINUTES);
    }

    private void sendEvents(){
        for(String nick : UserData.list.keySet()){
            UserData.User u = UserData.list.get(nick);
            if(UserData.waiting.getOrDefault(u.getChatId(), false)){
                continue;
            }
            else {
                try {
                    u.getEvents().messid = bot.execute(u.getEvents().getEvent(u.getChatId())).getMessageId();
                    UserData.block(nick);
                    UserData.waiting.put(u.getChatId(), true);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
