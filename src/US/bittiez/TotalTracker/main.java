package US.bittiez.TotalTracker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.logging.Logger;

public class main extends JavaPlugin implements Listener{
    public final static boolean debug = true;
    private static Logger log;
    private final static Long processEveryMinutes = 10L;
    private final static int MaxCapacity = 250;
    private ArrayList<QueObject> QueObjects;

    @Override
    public void onEnable() {
        log = getLogger();
        QueObjects = new ArrayList<QueObject>();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if(QueObjects.size() > 0){
                    runQue();
                }
            }
        }, (20L * 60L)*processEveryMinutes, (20L * 60L)*processEveryMinutes);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (cmd.getName().equalsIgnoreCase("tt")) {
            if (args[0].equalsIgnoreCase("sync") && sender.hasPermission("TotalTracker.sync")) {
                runQue();
                return true;
            }
        }
        return false;
    }

    private void runQue(){
        QueProcessor qp = new QueProcessor(new ArrayList<>(QueObjects), log);
        QueObjects.clear();
        qp.runTaskAsynchronously(this);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();
            Entity ekiller = nEvent.getDamager();
            if (ekiller instanceof Player) {
                //Player killed entity
                Player p = (Player)ekiller;
                QueObject queObject = new QueObject(p.getUniqueId().toString(), SQLTABLE.MOB_KILLS);
                QueObjects.add(queObject);
            }
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();
        if(killer != null){
            QueObject queObject = new QueObject(killer.getUniqueId().toString(), SQLTABLE.PVP_KILLS);
            QueObjects.add(queObject);
        }
        if(victim != null){
            QueObject queObject = new QueObject(victim.getUniqueId().toString(), SQLTABLE.DEATHS);
            QueObjects.add(queObject);
        }
        if(QueObjects.size() >= MaxCapacity)
            runQue();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(e.getPlayer() != null)
            QueObjects.add(new QueObject(e.getPlayer().getUniqueId().toString(), SQLTABLE.BLOCKS_BROKEN));
        if(QueObjects.size() >= MaxCapacity)
            runQue();
    }
    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent e){
        if(e.getPlayer() != null)
            QueObjects.add(new QueObject(e.getPlayer().getUniqueId().toString(), SQLTABLE.BLOCKS_PLACED));
        if(QueObjects.size() >= MaxCapacity)
            runQue();
    }
}
