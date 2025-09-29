import java.util.HashMap;
import java.util.Map;

public class UserData {
    public class User {
        private Economy economy;
        private Army army;
        private Population population;

        User() {
            economy = new Economy(20, 5, 10, 10);
            army = new Army(0, 5);
            population = new Population(10, 1);
        }

        public Economy getEconomy(){return this.economy;}
        public Army getArmy(){return this.army;}
        public Population getPopulation(){return this.population;}

    }
    public static User currentUser;
    private Map<String, User> list;
    UserData(){
        list = new HashMap<String, User>();
    }

    private void userAdd(String s){
        list.put(s, new User());
        currentUser = list.get(s);
    }

    private boolean userCheck(String s){
        for(String name : list.keySet()){
            if(name.equals(s)){
                return true;
            }
        }
        return false;
    }

    private void userSwap(String s){
        currentUser = list.get(s);
    }

    public void userChange(String s){
        if(userCheck(s)){
            userSwap(s);
            return;
        }
        userAdd(s);
    }

}
