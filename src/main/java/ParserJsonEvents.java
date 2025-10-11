import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;

public class ParserJsonEvents {
    public Events parse()
    {
        Gson gson = new Gson();
        String filePath = "C:\\Users\\Egor\\Desktop\\Java\\Test\\src\\main\\resources\\Test.json";
        try (FileReader reader = new FileReader(filePath)) {
            Events events = gson.fromJson(reader, Events.class);
            events.fillingChance();
            return events;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
