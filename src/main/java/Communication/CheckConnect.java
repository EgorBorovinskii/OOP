package Communication;

import Repositories.DataContext;
import Repositories.SessionsRepository;

public class CheckConnect {
    public static Long waitPlayerTwoConnect(long sessionID)
    {
        try
        {
            var dataContext = new DataContext();
            var session = SessionsRepository.get(dataContext, sessionID);
            while (session == null || session.playerTwoID == 0)
            {
                session = SessionsRepository.get(dataContext, sessionID);
                Thread.sleep(1000);
            }

            return session.playerTwoID;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0L;
        }
    }
}
