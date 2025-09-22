import java.util.HashMap;
import java.util.Map;

public class UserData {
    private class User {
        private Economy economy;
        private Army army;
        private Population population;

        User() {
            Economy economy = new Economy(20, 5);
            Army army = new Army(0, 10, 5);
            Population population = new Population(10, 10, 1);
        }
    }
    private static User currentUser;
    private Map<String, User> list;
    UserData(){
        list = new HashMap<String, User>();
    }

    private void userAdd(String s){
        list.put(s, new User());
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

    public User getUser(){
        return currentUser;
    }
}
