package ResourcesCountryTest;

import DataModels.Session;
import Messages.Messages;
import ResourcesCountry.Army;
import ResourcesCountry.Economy;
import Telegram.TGKeyboards;
import UserData.UserData;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import static org.mockito.Mockito.*;

public class ArmyTest {
    private final String nick = "player";
    private final Army army = new Army(0L, 5);

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
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
    }

    @Test
    void testIncreasePowerSuccess()
    {
        Update update = mockUpdate("увеличить силу");

        SendMessage message = army.handlerMessage(update);

        Assertions.assertEquals("Успешно\nТекущая сила:5", message.getText());
    }

    @Test
    void testIncreasePowerNotEnoughMoney()
    {
        UserData.list.get(nick).getEconomy().setMoney(5);
        Update update = mockUpdate("увеличить силу");

        SendMessage message = army.handlerMessage(update);
        String expected = Messages.notEnoughMoney + '\n' + Messages.needMoney + ": "
                + (UserData.list.get(nick).getEconomy().getMoneyForPower() - UserData.list.get(nick).getEconomy().getMoney());

        Assertions.assertEquals(expected, message.getText());
    }

    @Test
    void testShowPower()
    {
        Update update = mockUpdate("показать уровень силы");

        SendMessage message = army.handlerMessage(update);

        Assertions.assertEquals("0", message.getText());
    }

    @Test
    void testBackCommand()
    {
        Update update = mockUpdate("назад");

        SendMessage message = army.handlerMessage(update);

        Assertions.assertEquals("Главное меню", message.getText());
    }

    @Test
    void testUnknownCommand()
    {
        Update update = mockUpdate("привет");

        SendMessage message = army.handlerMessage(update);
        String expected = Messages.unknownCommand;

        Assertions.assertEquals(expected, message.getText());
    }
}
