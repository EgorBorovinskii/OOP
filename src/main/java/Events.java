import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Events{
    private List<Event> events;
    private double maxPercent = 100;
    private double[] chance;
    private int[] zeroForEvents = {-1, -1, -1, -1};
    private int indexForZeroEvents = 0;
    private int indexEvent;
    public long messid;

    public void setEvents(List<Event> events)
    {
        this.events = events;
    }

    public List<Event> getEvents()
    {
        return this.events;
    }

    public void fillingChance()
    {
        int countEvents = events.size();
        chance = new double[countEvents];
        Arrays.fill(chance, maxPercent / countEvents);
    }

    private void fillingZero(int index)
    {
        zeroForEvents[indexForZeroEvents % 4] = index;
        indexForZeroEvents++;
        for (int i = 0; i < zeroForEvents.length; i++)
        {
            if (zeroForEvents[i] > -1)
                chance[zeroForEvents[i]] = 0;
        }
    }

    private int random()
    {
        Random random = new Random();
        double randomEvent = random.nextDouble() * maxPercent;
        double sum = 0;
        for (int i = 0; i < chance.length; i++)
        {
            sum += chance[i];
            if (randomEvent < sum)
            {
                double chanceSum = chance[i] / (events.size() - 1);
                for (int j = 0; j < chance.length; j++) {
                    chance[j] += chanceSum;
                }
                fillingZero(i);
                return i;
            }
        }
        return chance.length - 1;
    }

    public SendMessage getEvent(long chatid)
    {
        String eventString;
        indexEvent = random();
        Event event = events.get(indexEvent);
        List<String> versions = event.getVersions();
        eventString = event.getEvent() + "\n" + versions.get(0) + "\n" + versions.get(1);

        SendMessage outMess = new SendMessage();
        outMess.setChatId(chatid);
        outMess.setText(eventString);
        outMess.setReplyMarkup(TGKeyboards.inlineKeyboardMarkups.getFirst());
        return outMess;
    }

    public SendMessage getMess(Update up)
    {

        Message inMess = up.getCallbackQuery().getMessage();
        String mess = up.getCallbackQuery().getData();
        String nick = inMess.getChat().getUserName();
        mess = mess.toLowerCase();
        Money.addMoney(nick);

        SendMessage outMess = new SendMessage();
        if(mess.equals("1") || mess.equals("2")) {
            int version = Integer.parseInt(mess);
            Event event = events.get(indexEvent);
            List<Long> edit = event.getEdit().get(version - 1);
            UserData.list.get(nick).getEconomy().setMoney(edit.get(0));
            UserData.list.get(nick).getPopulation().setLoyalty(edit.get(1));
            UserData.list.get(nick).getArmy().setPower(edit.get(2));
            outMess.setText("Выбор сделан!");
            outMess.setReplyMarkup(TGKeyboards.replyKeyboardMarkups.get(0));
        }
        return outMess;
    }
}
