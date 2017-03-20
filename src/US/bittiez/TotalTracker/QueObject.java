package US.bittiez.TotalTracker;


public class QueObject {
    public String QueType;
    public String Player;
    public int Quantity = 1;
    public String PlayerName;

    public QueObject(String PlayerUUID, String queType, String PlayerName){
        Player = PlayerUUID;
        QueType = queType;
        this.PlayerName = PlayerName;
    }
}
