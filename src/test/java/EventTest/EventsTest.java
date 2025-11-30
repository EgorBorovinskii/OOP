package EventTest;

import Event.Event;
import Event.Events;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EventsTest {
    private Events events;

    @BeforeEach
    void setUp()
    {
        events = new Events();

        List<Event> eventList = new ArrayList<>();
        for (int i = 0; i < 6; i++)
        {
            Event event = new Event();
            event.setEvent("Event " + (i + 1));
            event.setVersions(List.of("Option 1", "Option 2"));
            event.setEdit(List.of(List.of(10L, 5L, 0L), List.of(5L, 0L, 1L)));
            eventList.add(event);
        }

        events.setEvents(eventList);
        events.fillingChance();
    }

    @Test
    void testFillingChance()
    {
        double[] chance = events.getChance();
        double sum = 0;
        for (double ch : chance)
        {
            sum += ch;
        }
        Assertions.assertEquals(100, sum, 0.01);
    }
}
