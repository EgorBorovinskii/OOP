import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Creator implements GetterMessanges{
        private GetterMessanges state;
        private boolean event;

        public SendMessage getMess(Update up){
            event = false;
            return state.getMess(up);
        }

        public Creator(){
            state = new Logic();
            event = false;
        }

        public GetterMessanges getState()
        {
            return state;
        }

        public void swap(Update up){
            if (event)
                return;
            Message inMess = up.getMessage();
            String message = inMess.getText();
            String nickname = inMess.getChat().getUserName();
            message = message.toLowerCase();
            switch (message){
                case "назад":{
                    state = new Logic();
                    return;
                }
                case "экономика":{
                    state = UserData.list.get(nickname).getEconomy();
                    return;
                }
                case "армия":{
                    state = UserData.list.get(nickname).getArmy();
                    return;
                }
                case "население":{
                    state = UserData.list.get(nickname).getPopulation();
                }
            }
        }
}
