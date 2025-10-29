import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

public class UserData {
    public static class User {
        private final Economy economy;
        private final Army army;
        private final Population population;
        private final Events events;
        private final Creator creator;
        private final long chatid;
        private final Money money;

        User(long id) {
            economy = new Economy(20,  10, 10);
            army = new Army(0L, 5);
            population = new Population(10L, 1);
            ParserJsonEvents parser = new ParserJsonEvents();
            events = parser.parse();
            creator = new Creator();
            chatid = id;
            money = new Money(5, 10000);
        }

        public Economy getEconomy(){return this.economy;}
        public Army getArmy(){return this.army;}
        public Population getPopulation(){return this.population;}
        public Events getEvents(){return this.events;}
        public Creator getCreator(){return creator;}
        public long getChatId(){return chatid;}
        public Money getMoney(){return money;}


    }
    public static Map<String, User> list = new HashMap<String, User>();
    public static Map<Long, Boolean> waiting = new ConcurrentHashMap<>();

    private static void userAdd(String userName, long id){
        list.put(userName, new User(id));
    }

    private static boolean userCheck(String userName){
        for(String name : list.keySet()){
            if(name.equals(userName)){
                return true;
            }
        }
        return false;
    }

    public static void  userChange(String userName, long id){
        if(userCheck(userName)){
            return;
        }
        userAdd(userName, id);
    }

    public static void block(String nick){
        list.get(nick).getCreator().event = true;
    }
    public static void unblock(String nick){
        list.get(nick).getCreator().event = false;
    }
}
