public class EventCheck {
    public static long startTime = System.currentTimeMillis();
    public static void check(){
        long endTime = System.currentTimeMillis();
        if(endTime - startTime > 20000) {
            System.out.println(Messages.event);
            startTime = System.currentTimeMillis();
        }
    }
}
