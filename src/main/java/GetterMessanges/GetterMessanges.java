package GetterMessanges;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface GetterMessanges {
    public SendMessage handlerMessage(Update up);
}
