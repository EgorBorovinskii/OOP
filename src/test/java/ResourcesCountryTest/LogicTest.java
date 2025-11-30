package ResourcesCountryTest;

import DataModels.UserDto;
import Repositories.DataContext;
import Repositories.SessionsRepository;
import Repositories.UsersRepository;
import ResourcesCountry.Economy;
import ResourcesCountry.Logic;
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

import static org.mockito.Mockito.*;

public class LogicTest {
    private final String nick = "player";
    private final Logic logic = new Logic();

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
        UserData.list.get(nick).setOpponentID(200);
        UserData.list.get(nick).setSessionID("123");

        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
    }

    @Test
    void testEnterTheEconomy()
    {
        Update update = mockUpdate("экономика");

        SendMessage message = logic.handlerMessage(update);

        Assertions.assertEquals("Вы вошли в экономику", message.getText());
    }

    @Test
    void testEnterTheArmy()
    {
        Update update = mockUpdate("армия");

        SendMessage message = logic.handlerMessage(update);

        Assertions.assertEquals("Вы вошли в армию", message.getText());
    }

    @Test
    void testEnterThePopulation()
    {
        Update update = mockUpdate("население");

        SendMessage message = logic.handlerMessage(update);

        Assertions.assertEquals("Вы вошли в население", message.getText());
    }

    @Test
    void testShowResourcesOpponent()
    {
        Update update = mockUpdate("посмотреть ресурсы противника");
        UserDto userDto = mock(UserDto.class);
        when(userDto.getResourcesText()).thenReturn("Деньги: 20\nСила: 0\nЛояльность: 10");

        try(MockedStatic<UsersRepository> repositoryMock = mockStatic(UsersRepository.class))
        {
            repositoryMock.when(() -> UsersRepository.getByTelegramID(any(DataContext.class), eq(200L))).thenReturn(userDto);

            SendMessage message = logic.handlerMessage(update);

            Assertions.assertEquals("Деньги: 20\nСила: 0\nЛояльность: 10", message.getText());
        }
    }
}
