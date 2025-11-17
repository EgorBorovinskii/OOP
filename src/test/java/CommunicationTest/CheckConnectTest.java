package CommunicationTest;

import Communication.CheckConnect;
import DataModels.Session;
import Repositories.DataContext;
import Repositories.SessionsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;

public class CheckConnectTest {

    @Test
    void testWaitPlayerTwoConnect()
    {

        try (MockedStatic<SessionsRepository> repositoryMock = mockStatic(SessionsRepository.class))
        {
            Session session = new Session(100, 100, 200);

            repositoryMock.when(() -> SessionsRepository.get(any(DataContext.class), eq(100L))).thenReturn(session);

            long result = CheckConnect.waitPlayerTwoConnect(100);
            Assertions.assertEquals(200, result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
