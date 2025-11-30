package DataModels;

public class Session {
    public long id;
    public long playerOneID;
    public long playerTwoID;

    public Session(){}

    public Session(long playerOneID, long playerTwoID)
    {
        this.playerOneID = playerOneID;
        this.playerTwoID = playerTwoID;
    }

    public Session(long id, long playerOneID, long playerTwoID)
    {
        this.id = id;
        this.playerOneID = playerOneID;
        this.playerTwoID = playerTwoID;
    }
}
