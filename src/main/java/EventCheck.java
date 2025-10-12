public class EventCheck {
    public static long startTime = System.currentTimeMillis();
    public static boolean check(){
        long endTime = System.currentTimeMillis();
        if(endTime - startTime > 20000) {
            startTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public static void updateTime()
    {
        startTime = System.currentTimeMillis();
    }
}
