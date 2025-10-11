public class EventCheck {
    public static long startTime = System.currentTimeMillis();
    public static void check(){
        long endTime = System.currentTimeMillis();
        if(endTime - startTime > 20000) {
            UserData.currentUser.getEvents().printRandom();
            startTime = System.currentTimeMillis();
        }
    }

    public static void updateTime()
    {
        startTime = System.currentTimeMillis();
    }
}
