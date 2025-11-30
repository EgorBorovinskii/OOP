package CommunicationTest;

import Communication.Connect;
import DataModels.Session;
import Repositories.DataContext;
import Repositories.SessionsRepository;
import Telegram.TGKeyboards;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import static org.mockito.Mockito.*;

public class ConnectTest {

    private Update mockUpdate(String text, long chatId, String username) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(update.getMessage()).thenReturn(message);
        when(message.getText()).thenReturn(text);
        when(message.getChatId()).thenReturn(chatId);
        when(message.getChat()).thenReturn(chat);
        when(chat.getUserName()).thenReturn(username);

        return update;
    }

    @Test
    void testSessionNotFound()
    {
        Update update = mockUpdate("123", 100, "user");
        try (MockedStatic<SessionsRepository> sessionsRepositoryMock = mockStatic(SessionsRepository.class))
        {
            sessionsRepositoryMock.when(() -> SessionsRepository.get(any(DataContext.class), eq(123L))).thenReturn(null);
            SendMessage result = new Connect().handlerMessage(update);

            Assertions.assertEquals("Сессия не найдена", result.getText());
        }
    }

    @Test
    void testSuccessfulConnection()
    {
        Update update = mockUpdate("123", 200, "player");
        Session session = new Session();
        session.playerOneID = 100;
        TGKeyboards.replyKeyboardMarkups.add(mock(ReplyKeyboardMarkup.class));
        try (MockedStatic<SessionsRepository> sessionsRepositoryMock = mockStatic(SessionsRepository.class))
        {
            sessionsRepositoryMock.when(() -> SessionsRepository.get(any(DataContext.class), eq(123L))).thenReturn(session);
            SendMessage result = new Connect().handlerMessage(update);

            Assertions.assertEquals("Вы подключились", result.getText());
            Assertions.assertNotNull(result.getReplyMarkup());
            Assertions.assertEquals(200, session.playerTwoID);
        }
    }
}
