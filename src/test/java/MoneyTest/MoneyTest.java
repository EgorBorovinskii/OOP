package MoneyTest;

import Money.Money;
import Telegram.TGKeyboards;
import UserData.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoneyTest {
    private String nickHighLoyalty;
    private String nickLowLoyalty;

    @BeforeEach
    void setUp()
    {
        nickHighLoyalty = "playerHigh";
        nickLowLoyalty = "playerLow";

        UserData.list.put(nickHighLoyalty, new UserData.User(100));
        UserData.list.put(nickLowLoyalty, new UserData.User(200));

        UserData.list.get(nickHighLoyalty).getPopulation().setLoyalty(5L);
        UserData.list.get(nickLowLoyalty).getPopulation().setLoyalty(2L);

        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
    }

    private Update mockUpdate(String nick, String text)
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

    @Test
    void testRaiseTaxesHighLoyalty()
    {
        Money money = new Money(10, 1000);
        Update update = mockUpdate(nickHighLoyalty, "поднять налоги");

        SendMessage result = money.raiseTaxes(update);

        Assertions.assertEquals("Налоги увеличены.\nЛюди негодуют (-3 лояльности)", result.getText());
        Assertions.assertEquals(2, UserData.list.get(nickHighLoyalty).getPopulation().getLoyalty());
    }

    @Test
    void testRaiseTaxesLowLoyalty()
    {
        Money money = new Money(10, 1000);
        Update update = mockUpdate(nickLowLoyalty, "поднять налоги");

        SendMessage result = money.raiseTaxes(update);

        Assertions.assertEquals("Люди не согласны(лояльность слишком низкая)", result.getText());
        Assertions.assertEquals(2, UserData.list.get(nickLowLoyalty).getPopulation().getLoyalty());
    }

    @Test
    void testAddMoney() throws Exception
    {
        Money money = new Money(10, 100);

        float before = UserData.list.get(nickLowLoyalty).getEconomy().getMoney();

        Thread.sleep(2000);
        money.addMoney(nickLowLoyalty);

        float after = UserData.list.get(nickLowLoyalty).getEconomy().getMoney();
        Assertions.assertTrue(after > before);
    }
}
