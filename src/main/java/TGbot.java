import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TGbot extends TelegramLongPollingBot {
    final private String BOT_TOKEN = "8465877064:AAHSnu0oOhuoK9po3iHLQvaTFW4srGYmvo4";
    final private String BOT_NAME = "FoEmpire_bot";
    private Sceduel sceduel;

    public TGbot() {
        sceduel = new Sceduel(this);
        sceduel.startEvents();
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
            if (update.hasCallbackQuery()) {
                execute(doEvent(update));
            } else if (update.hasMessage() && update.getMessage().hasText()) {
                execute(creatAns(update));
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage creatAns(Update up){
        String nick = up.getMessage().getChat().getUserName();
        UserData.userChange(nick, up.getMessage().getChatId());
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

    private SendMessage doEvent(Update up){
        UserData.User u = UserData.list.get(up.getCallbackQuery().getMessage().getChat().getUserName());
        DeleteMessage del = new DeleteMessage();
        del.setChatId(up.getCallbackQuery().getMessage().getChatId());
        del.setMessageId((int)u.getEvents().messid);
        try{
            execute(del);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
        SendMessage outm = u.getEvents().getMess(up);
        outm.setChatId(up.getCallbackQuery().getMessage().getChatId());
        return outm;
    }
}
