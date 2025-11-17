package ResourcesCountryTest;

import Messages.Messages;
import ResourcesCountry.Economy;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EconomyTest {
    private final String nick = "player";
    Economy economy = new Economy(20, 10, 10);

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
        economy.setMoneyForPower(10);

        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
    }

    @Test
    void testBuyPower()
    {
        float expectedMoney = 10;
        economy.buyPower();
        Assertions.assertEquals(expectedMoney, economy.getMoney());
    }

    @Test
    void testBuyLoyality()
    {
        float expectedMoney = 10;
        economy.buyLoyality();
        Assertions.assertEquals(expectedMoney, economy.getMoney());
    }

    @Test
    void testShowMoney()
    {
        Update update = mockUpdate("показать количество денег");

        SendMessage message = economy.handlerMessage(update);

        Assertions.assertEquals("20.0", message.getText());
    }

    @Test
    void testRaiseTaxes()
    {
        Update update = mockUpdate("поднять налоги");

        SendMessage message = economy.handlerMessage(update);

        Assertions.assertEquals("Налоги увеличены.\nЛюди негодуют (-3 лояльности)", message.getText());
    }

    @Test
    void testBackCommand()
    {
        Update update = mockUpdate("назад");

        SendMessage message = economy.handlerMessage(update);

        Assertions.assertEquals("Главное меню", message.getText());
    }

    @Test
    void testUnknownCommand()
    {
        Update update = mockUpdate("привет");

        SendMessage message = economy.handlerMessage(update);
        String expected = Messages.unknownCommand;

        Assertions.assertEquals(expected, message.getText());
    }
}