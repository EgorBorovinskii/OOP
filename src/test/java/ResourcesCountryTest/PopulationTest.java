package ResourcesCountryTest;

import Messages.Messages;
import ResourcesCountry.Economy;
import ResourcesCountry.Population;
import Telegram.TGKeyboards;
import UserData.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import javax.script.ScriptEngine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PopulationTest {
    private final String nick = "player";
    private final Population population = new Population(10L, 5);

    private Update mockUpdate(String text)
    {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(update.getMessage()).thenReturn(message);
        when(message.getText()).thenReturn(text);
        when(message.getChat()).thenReturn(chat);
        when(chat.getUserName()).thenReturn(nick);

        return update;
    }

    @BeforeEach
    void setUp()
    {
        UserData.list.put(nick, new UserData.User(100));
        Economy economy = UserData.list.get(nick).getEconomy();
        economy.setMoney(100);
        economy.setMoneyForLoyality(10);

        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
    }

    @Test
    void testIncreaseLoyaltySuccess()
    {
        Update update = mockUpdate("увеличить лояльность");

        SendMessage message = population.handlerMessage(update);

        Assertions.assertEquals("Успешно\nТекущая лояльность: 15", message.getText());
    }

    @Test
    void testIncreaseLoyaltyNotEnoughMoney()
    {
        UserData.list.get(nick).getEconomy().setMoney(5);
        Update update = mockUpdate("увеличить лояльность");

        SendMessage message = population.handlerMessage(update);
        String expected = Messages.notEnoughMoney + '\n' + Messages.needMoney + ": "
                + (UserData.list.get(nick).getEconomy().getMoneyForLoyality() - UserData.list.get(nick).getEconomy().getMoney());

        Assertions.assertEquals(expected, message.getText());
    }

    @Test
    void testShowPower()
    {
        Update update = mockUpdate("показать уровень лояльности");

        SendMessage message = population.handlerMessage(update);

        Assertions.assertEquals("10", message.getText());
    }

    @Test
    void testBackCommand()
    {
        Update update = mockUpdate("назад");

        SendMessage message = population.handlerMessage(update);

        Assertions.assertEquals("Главное меню", message.getText());
    }

    @Test
    void testUnknownCommand()
    {
        Update update = mockUpdate("привет");

        SendMessage message = population.handlerMessage(update);
        String expected = Messages.unknownCommand;

        Assertions.assertEquals(expected, message.getText());
    }
}
