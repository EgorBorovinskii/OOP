package GetterMessanges;

import Communication.Connect;
import Repositories.DataContext;
import Repositories.UsersRepository;
import ResourcesCountry.Logic;
import UserData.UserData;
import DataModels.UserDto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import ResourcesCountry.*;

import java.sql.SQLException;

public class Creator implements GetterMessanges {
        private GetterMessanges state;
        public boolean event;

        public SendMessage handlerMessage(Update up){
            if(event){
                return new SendMessage(String.valueOf(up.getMessage().getChatId()), "Выберите варинт события");
            }
            return state.handlerMessage(up);
        }

        public Creator(){
            state = new MainMenu();
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
            float money = UserData.list.get(nickname).getEconomy().getMoney();
            long power = UserData.list.get(nickname).getArmy().getPower();
            long loyalty = UserData.list.get(nickname).getPopulation().getLoyalty();
            try
            {
                DataContext dataContext = new DataContext();
                UserDto userDto = new UserDto(inMess.getChatId(), money, power, loyalty);
                UsersRepository.updateByTelegramID(dataContext, userDto);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return;
            }
            if (message.equals(UserData.list.get(nickname).getSessionID()))
            {
                state = new Logic();
                return;
            }
            switch (message){
                case "назад", "создать комнату":{
                    state = new Logic();
                    return;
                }
                case "подключиться к комнате":{
                    state = new Connect();
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
