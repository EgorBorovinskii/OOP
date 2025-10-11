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
    private ReplyKeyboardMarkup replyKeyboardMarkupMenu;

    public TGbot(){
        logic = new Logic();
        initKeyboardMenu();
    }

    @Override
    public String getBotUsername(){
        return BOT_NAME;
    }

    @Override
    public String getBotToken(){
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMess = update.getMessage();
                String mes = inMess.getText();
                String username = inMess.getChat().getUserName();
                String response = logic.Answer(mes, username);
                SendMessage outMess = new SendMessage();
                outMess.setChatId(inMess.getChatId());
                outMess.setText(response);
                outMess.setReplyMarkup(replyKeyboardMarkupMenu);

                execute(outMess);
            }
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    void initKeyboardMenu(){
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyrows = new ArrayList<>();
        KeyboardRow keyrowe = new KeyboardRow();
        keyrows.add(keyrowe);
        keyrowe.add(new KeyboardButton("Экономика"));

        KeyboardRow keyrowa = new KeyboardRow();
        keyrows.add(keyrowa);
        keyrowa.add(new KeyboardButton("Армия"));

        KeyboardRow keyrowp = new KeyboardRow();
        keyrows.add(keyrowp);
        keyrowp.add(new KeyboardButton("Население"));

        replyKeyboardMarkup.setKeyboard(keyrows);
    }

}
