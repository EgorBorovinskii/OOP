import java.util.List;

public class Event {
    private String event;
    private List<String> versions;
    private List<List<Long>> edit;

    public void setEvent(String event)
    {
        this.event = event;
    }

    public void setVersions(List<String> versions)
    {
        this.versions = versions;
    }

    public void setEdit(List<List<Long>> edit)
    {
        this.edit = edit;
    }

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
