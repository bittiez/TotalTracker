package US.bittiez.TotalTracker;


public class QueObject {
    public String QueType;
    public String Player;
    public int Quantity = 1;

    public QueObject(String PlayerUUID, String queType){
        Player = PlayerUUID;
        QueType = queType;
    }
}
