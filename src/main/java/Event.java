import java.util.List;

public class Event {
    private String event;
    private List<String> versions;
    private List<List<Long>> edit;

    public String getEvent()
    {
        return event;
    }

    public List<String> getVersions()
    {
        return versions;
    }

    public List<List<Long>> getEdit()
    {
        return edit;
    }
}
