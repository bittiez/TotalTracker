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
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.logging.Logger;

//
// Plan
// Main Thread
// Every 5? minutes create a processor thread, send the array list to it and clear the original

public class main extends JavaPlugin implements Listener{
    public final static boolean debug = true;
    private static Logger log;
    private final static Long processEveryMinutes = 10L;
    private ArrayList<queObject> queObjects;

    @Override
    public void onEnable() {
        log = getLogger();
        queObjects = new ArrayList<queObject>();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if(queObjects.size() > 0){
                    runQue();
                }
            }
        }, (20L * 60L)*processEveryMinutes, (20L * 60L)*processEveryMinutes);
    }

    private void runQue(){
        queProcessor qp = new queProcessor(queObjects, log);
        qp.runTaskAsynchronously(this);
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
