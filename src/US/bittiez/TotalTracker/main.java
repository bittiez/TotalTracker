package US.bittiez.TotalTracker;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

//
// Plan
// Main Thread
// Every 5? minutes create a processor thread, send the array list to it and clear the original

public class main extends JavaPlugin implements Listener{
    public final static boolean debug = true;
    private static Logger log;

    @Override
    public void onEnable() {
        log = getLogger();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();
            Entity ekiller = nEvent.getDamager();
            if (ekiller instanceof Player) {
                //Player killed entity
            }
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();
        if(killer != null){

        }
    }
}
