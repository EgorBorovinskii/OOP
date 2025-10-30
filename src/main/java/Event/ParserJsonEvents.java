package Event;

import com.google.gson.Gson;

import java.io.FileReader;

public class ParserJsonEvents {
    public Events parse()
    {
        Gson gson = new Gson();
        String filePath = getClass().getClassLoader().getResource("Events.json").toString().substring(5);
        try (FileReader reader = new FileReader(filePath)) {
            Events events = gson.fromJson(reader, Events.class);
            events.fillingChance();
            return events;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
