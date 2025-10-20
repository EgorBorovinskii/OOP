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

    private ReplyKeyboardMarkup initKeyboardMenu()
    {
        ReplyKeyboardMarkup replyKeyboardMarkupMenu = new ReplyKeyboardMarkup();
        replyKeyboardMarkupMenu.setResizeKeyboard(true);
        replyKeyboardMarkupMenu.setOneTimeKeyboard(true);

        ArrayList<KeyboardRow> keyrowsMenu = new ArrayList<>();
        KeyboardRow keyrowEconomy = new KeyboardRow();
        keyrowsMenu.add(keyrowEconomy);
        keyrowEconomy.add(new KeyboardButton("Экономика"));

        KeyboardRow keyrowArmy = new KeyboardRow();
        keyrowsMenu.add(keyrowArmy);
        keyrowArmy.add(new KeyboardButton("Армия"));

        KeyboardRow keyrowPopulation = new KeyboardRow();
        keyrowsMenu.add(keyrowPopulation);
        keyrowPopulation.add(new KeyboardButton("Население"));

        replyKeyboardMarkupMenu.setKeyboard(keyrowsMenu);
        return replyKeyboardMarkupMenu;
    }

    private ReplyKeyboardMarkup initKeyboardEconomy()
    {
        ReplyKeyboardMarkup replyKeyboardMarkupEconomy = new ReplyKeyboardMarkup();
        replyKeyboardMarkupEconomy.setResizeKeyboard(true);
        replyKeyboardMarkupEconomy.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyrowsEconomy = new ArrayList<>();

        KeyboardRow keyrowShowMoney = new KeyboardRow();
        keyrowsEconomy.add(keyrowShowMoney);
        keyrowShowMoney.add(new KeyboardButton("Показать количество денег"));

        KeyboardRow keyrowTax = new KeyboardRow();
        keyrowsEconomy.add(keyrowTax);
        keyrowTax.add(new KeyboardButton("Поднять Налоги?"));

        KeyboardRow keyrowBack = new KeyboardRow();
        keyrowBack.add(new KeyboardButton("Назад"));

        keyrowsEconomy.add(keyrowBack);

        replyKeyboardMarkupEconomy.setKeyboard(keyrowsEconomy);
        return replyKeyboardMarkupEconomy;
    }

    private ReplyKeyboardMarkup initKeyboardArmy()
    {
        ReplyKeyboardMarkup replyKeyboardMarkupArmy = new ReplyKeyboardMarkup();
        replyKeyboardMarkupArmy.setResizeKeyboard(true);
        replyKeyboardMarkupArmy.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyrowsArmy = new ArrayList<>();

        KeyboardRow keyrowShowPower = new KeyboardRow();
        keyrowsArmy.add(keyrowShowPower);
        keyrowShowPower.add(new KeyboardButton("Показать уровень силы"));

        KeyboardRow keyrowIncreasePower = new KeyboardRow();
        keyrowsArmy.add(keyrowIncreasePower);
        keyrowIncreasePower.add(new KeyboardButton("Увеличить силу"));

        KeyboardRow keyrowBack = new KeyboardRow();
        keyrowBack.add(new KeyboardButton("Назад"));

        keyrowsArmy.add(keyrowBack);

        replyKeyboardMarkupArmy.setKeyboard(keyrowsArmy);

        return replyKeyboardMarkupArmy;
    }

    private ReplyKeyboardMarkup initKeyboardPopulation()
    {
        ReplyKeyboardMarkup replyKeyboardMarkupPopulation = new ReplyKeyboardMarkup();
        replyKeyboardMarkupPopulation.setResizeKeyboard(true);
        replyKeyboardMarkupPopulation.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyrowsPopulation = new ArrayList<>();

        KeyboardRow keyrowShowLoyalty = new KeyboardRow();
        keyrowsPopulation.add(keyrowShowLoyalty);
        keyrowShowLoyalty.add(new KeyboardButton("Показать уровень лояльности"));

        KeyboardRow keyrowIncreaseLoyalty = new KeyboardRow();
        keyrowsPopulation.add(keyrowIncreaseLoyalty);
        keyrowIncreaseLoyalty.add(new KeyboardButton("Увеличить лояльность"));

        KeyboardRow keyrowBack = new KeyboardRow();
        keyrowBack.add(new KeyboardButton("Назад"));

        keyrowsPopulation.add(keyrowBack);

        replyKeyboardMarkupPopulation.setKeyboard(keyrowsPopulation);
        return replyKeyboardMarkupPopulation;
    }

    private ReplyKeyboardMarkup initKeyboardChoice()
    {
        ReplyKeyboardMarkup replyKeyboardMarkupChoice = new ReplyKeyboardMarkup();
        replyKeyboardMarkupChoice.setResizeKeyboard(true);
        replyKeyboardMarkupChoice.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyrowsChoice = new ArrayList<>();

        KeyboardRow keyrowChoices = new KeyboardRow();
        keyrowsChoice.add(keyrowChoices);
        keyrowChoices.add(new KeyboardButton("1"));
        keyrowChoices.add(new KeyboardButton("2"));

        replyKeyboardMarkupChoice.setKeyboard(keyrowsChoice);

        return replyKeyboardMarkupChoice;
    }

    void initKeyboards(){
        replyKeyboardMarkups = new ArrayList<>();

        replyKeyboardMarkups.add(initKeyboardMenu());

        replyKeyboardMarkups.add(initKeyboardEconomy());

        replyKeyboardMarkups.add(initKeyboardArmy());

        replyKeyboardMarkups.add(initKeyboardPopulation());

        replyKeyboardMarkups.add(initKeyboardChoice());
    }

}
