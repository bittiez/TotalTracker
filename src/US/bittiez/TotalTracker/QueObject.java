package US.bittiez.TotalTracker;


import org.bukkit.entity.Player;

public class QueObject {
    public String QueType;
    public String PlayerUUID;
    public double Quantity = 1;
    public String PlayerName;
    public boolean sentToDataBase = false;

    public QueObject(String PlayerUUID, String queType, String PlayerName) {
        this.PlayerUUID = PlayerUUID;
        this.QueType = queType;
        this.PlayerName = PlayerName;
    }

    public QueObject(String PlayerUUID, String queType, String PlayerName, int Quantity) {
        this.PlayerUUID = PlayerUUID;
        this.QueType = queType;
        this.PlayerName = PlayerName;
        this.Quantity = Quantity;
    }

    public QueObject(String PlayerUUID, String queType, String PlayerName, double Quantity) {
        this.PlayerUUID = PlayerUUID;
        this.QueType = queType;
        this.PlayerName = PlayerName;
        this.Quantity = Quantity;
    }


    public QueObject(Player player, String queType) {
        this(player.getUniqueId().toString(), queType, player.getName());
    }

    public QueObject(Player player, String queType, int Quantity) {
        this(player.getUniqueId().toString(), queType, player.getName(), Quantity);
    }

    public QueObject(Player player, String queType, double Quantity) {
        this(player.getUniqueId().toString(), queType, player.getName(), Quantity);
    }
}
