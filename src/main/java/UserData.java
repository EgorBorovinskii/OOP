import java.util.HashMap;
import java.util.Map;

public class UserData {
    public static class User {
        private final Economy economy;
        private final Army army;
        private final Population population;
        private final Events events;
        private Creator creator;

        User() {
            economy = new Economy(20,  10, 10);
            army = new Army(0L, 5);
            population = new Population(10L, 1);
            ParserJsonEvents parser = new ParserJsonEvents();
            events = parser.parse();
            creator = new Creator();
        }

        public Economy getEconomy(){return this.economy;}
        public Army getArmy(){return this.army;}
        public Population getPopulation(){return this.population;}
        public Events getEvents(){return this.events;}
        public Creator getCreator(){return creator;}


    }
    public static Map<String, User> list = new HashMap<String, User>();

    private static void userAdd(String userName){
        list.put(userName, new User());
    }

    private static boolean userCheck(String userName){
        for(String name : list.keySet()){
            if(name.equals(userName)){
                return true;
            }
        }
        return false;
    }

    public static void  userChange(String userName){
        if(userCheck(userName)){
            return;
        }
        userAdd(userName);
    }
}
