import java.util.HashMap;
import java.util.Map;

public class UserData {
    public class User {
        private Economy economy;
        private Army army;
        private Population population;
        private Events events;

        User() {
            economy = new Economy(20,  10, 10);
            army = new Army(0L, 5);
            population = new Population(10L, 1);
            ParserJsonEvents parser = new ParserJsonEvents();
            events = parser.parse();
        }

        public Economy getEconomy(){return this.economy;}
        public Army getArmy(){return this.army;}
        public Population getPopulation(){return this.population;}
        public Events getEvents(){return this.events;}

    }
    public static User currentUser;
    private Map<String, User> list;
    UserData(){
        list = new HashMap<String, User>();
    }

    private void userAdd(String userName){
        list.put(userName, new User());
        currentUser = list.get(userName);
    }

    private boolean userCheck(String userName){
        for(String name : list.keySet()){
            if(name.equals(userName)){
                return true;
            }
        }
        return false;
    }

    private void userSwap(String userName){
        currentUser = list.get(userName);
    }

    public void userChange(String userName){
        if(userCheck(userName)){
            userSwap(userName);
            return;
        }
        userAdd(userName);
    }

}
