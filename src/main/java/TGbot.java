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
    private ArrayList<ReplyKeyboardMarkup> replyKeyboardMarkups;

    public TGbot(){
        logic = new Logic();
        initKeyboards();
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
                outMess.setReplyMarkup(replyKeyboardMarkups.get(logic.getState()));

                execute(outMess);
            }
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    void initKeyboards(){
        KeyboardRow keyrowBack = new KeyboardRow();
        keyrowBack.add(new KeyboardButton("Назад"));
        replyKeyboardMarkups = new ArrayList<>();

        ReplyKeyboardMarkup replyKeyboardMarkupMenu = new ReplyKeyboardMarkup();
        replyKeyboardMarkupMenu.setResizeKeyboard(true);
        replyKeyboardMarkupMenu.setOneTimeKeyboard(true);

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

        replyKeyboardMarkupMenu.setKeyboard(keyrows);
        replyKeyboardMarkups.add(replyKeyboardMarkupMenu);

        ReplyKeyboardMarkup replyKeyboardMarkupEconomy = new ReplyKeyboardMarkup();
        replyKeyboardMarkupEconomy.setResizeKeyboard(true);
        replyKeyboardMarkupEconomy.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyrowsE = new ArrayList<>();

        KeyboardRow keyrowm = new KeyboardRow();
        keyrowsE.add(keyrowm);
        keyrowm.add(new KeyboardButton("Показать количество денег"));

        KeyboardRow keyrowN = new KeyboardRow();
        keyrowsE.add(keyrowN);
        keyrowN.add(new KeyboardButton("Поднять Налоги?"));

        keyrowsE.add(keyrowBack);

        replyKeyboardMarkupEconomy.setKeyboard(keyrowsE);
        replyKeyboardMarkups.add(replyKeyboardMarkupEconomy);

        ReplyKeyboardMarkup replyKeyboardMarkupArmy = new ReplyKeyboardMarkup();
        replyKeyboardMarkupArmy.setResizeKeyboard(true);
        replyKeyboardMarkupArmy.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyrowsA = new ArrayList<>();

        KeyboardRow keyrowS = new KeyboardRow();
        keyrowsA.add(keyrowS);
        keyrowS.add(new KeyboardButton("Показать уровень силы"));

        KeyboardRow keyrowU = new KeyboardRow();
        keyrowsA.add(keyrowU);
        keyrowU.add(new KeyboardButton("Увеличить силу"));

        keyrowsA.add(keyrowBack);

        replyKeyboardMarkupArmy.setKeyboard(keyrowsA);
        replyKeyboardMarkups.add(replyKeyboardMarkupArmy);


        ReplyKeyboardMarkup replyKeyboardMarkupPopulation = new ReplyKeyboardMarkup();
        replyKeyboardMarkupPopulation.setResizeKeyboard(true);
        replyKeyboardMarkupPopulation.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyrowsP = new ArrayList<>();

        KeyboardRow keyrowShow = new KeyboardRow();
        keyrowsP.add(keyrowShow);
        keyrowShow.add(new KeyboardButton("Показать уровень лояльности"));

        KeyboardRow keyrowUp = new KeyboardRow();
        keyrowsP.add(keyrowUp);
        keyrowUp.add(new KeyboardButton("Увеличить лояльность"));

        keyrowsP.add(keyrowBack);

        replyKeyboardMarkupPopulation.setKeyboard(keyrowsP);
        replyKeyboardMarkups.add(replyKeyboardMarkupPopulation);

        ReplyKeyboardMarkup replyKeyboardMarkupChoice = new ReplyKeyboardMarkup();
        replyKeyboardMarkupChoice.setResizeKeyboard(true);
        replyKeyboardMarkupChoice.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyrowsC = new ArrayList<>();

        KeyboardRow keyrow1 = new KeyboardRow();
        keyrowsC.add(keyrow1);
        keyrow1.add(new KeyboardButton("1"));
        keyrow1.add(new KeyboardButton("2"));

        replyKeyboardMarkupChoice.setKeyboard(keyrowsC);
        replyKeyboardMarkups.add(replyKeyboardMarkupChoice);



    }

}
