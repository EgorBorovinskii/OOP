import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class TGbot extends TelegramLongPollingBot {
    final private String BOT_TOKEN = "8465877064:AAHSnu0oOhuoK9po3iHLQvaTFW4srGYmvo4";
    final private String BOT_NAME = "FoEmpire_bot";
    private Logic logic;

    public TGbot() {
        logic = new Logic();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                execute(creatAns(update));
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage creatAns(Update up){
        String nick = up.getMessage().getChat().getUserName();
        UserData.userChange(nick);
        Creator cr = UserData.list.get(nick).getCreator();
        SendMessage ans = cr.getMess(up);
        ans.setChatId(up.getMessage().getChatId());
        if(ans.getText().equals(Messages.unknownCommand)){
            System.out.println(cr.getState());
            return ans;
        }
        else {
            cr.swap(up);
            return ans;
        }
    }
}
