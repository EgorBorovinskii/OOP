import javax.xml.stream.events.StartDocument;

public class EventCheck {
    public static long StartTime = System.currentTimeMillis();
    public static void Check(){
        long EndTime = System.currentTimeMillis();
        if(EndTime - StartTime > 20000) {
            System.out.println(Messages.event);
            StartTime = System.currentTimeMillis();
        }
    }
}
