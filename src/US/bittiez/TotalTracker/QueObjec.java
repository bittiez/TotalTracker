package US.bittiez.TotalTracker;


public class QueObject {
    public String QueType;
    public String Player;

    public QueObject(String PlayerUUID, String queType){
        Player = PlayerUUID;
        QueType = queType;
    }
}
