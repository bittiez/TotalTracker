package US.bittiez.TotalTracker.Balance;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHandler {
    private Boolean isSupported = false;
    private Plugin plugin;
    private Economy economy;

    public VaultHandler(Plugin plugin){

        this.plugin = plugin;
    }

    public void setup(){
        if(plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            setSupported(false);
            return;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            setSupported(false);
        }
        economy = rsp.getProvider();
        setSupported(true);

    }

    public Double getPlayerBalance(Player player){
        if(getSupported())
            return economy.getBalance(player);
        else
            return 0d;
    }

    public Boolean getSupported() {
        return isSupported;
    }

    public void setSupported(Boolean supported) {
        isSupported = supported;
    }
}
