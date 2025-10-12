import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Events {
    private List<Event> events;
    private double maxPercent = 100;
    private double[] chance;
    private int[] zeroForEvents = {-1, -1, -1, -1};
    private int indexForZeroEvents = 0;
    private int indexEvent;

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

    public String getEvent()
    {
        String eventString;
        indexEvent = random();
        Event event = events.get(indexEvent);
        List<String> versions = event.getVersions();
        eventString = event.getEvent() + "\n" + versions.get(0) + "\n" + versions.get(1);
        return eventString;
    }

    public String doEvent(String mess)
    {
        if(mess.equals("1") || mess.equals("2")) {
            int version = Integer.parseInt(mess);
            Event event = events.get(indexEvent);
            List<Long> edit = event.getEdit().get(version - 1);
            UserData.currentUser.getEconomy().setMoney(edit.get(0));
            UserData.currentUser.getPopulation().setLoyalty(edit.get(1));
            UserData.currentUser.getArmy().setPower(edit.get(2));
            return "Выбор сделан!";
        }
        else{
            return Messages.unknownCommand;
        }
    }

    public void printRandom()
    {
        Scanner in = new Scanner(System.in);
        int version;
        int index = random();
        Event event = events.get(index);
        System.out.println(event.getEvent());
        List<String> versions = event.getVersions();
        List<List<Long>> edit = event.getEdit();
        for (int i = 0; i < versions.size(); i++) {
            System.out.println(versions.get(i));
        }
        version = in.nextInt();
        do {
            if (version > 0 && version < edit.size() + 1)
            {
                List<Long> ed = edit.get(version - 1);
                UserData.currentUser.getEconomy().setMoney(ed.get(0));
                UserData.currentUser.getPopulation().setLoyalty(ed.get(1));
                UserData.currentUser.getArmy().setPower(ed.get(2));
            }
            else
            {
                System.out.println(Messages.noOption);
                version = in.nextInt();
            }
        } while (version < 0 && version > edit.size() + 1);
    }
}
