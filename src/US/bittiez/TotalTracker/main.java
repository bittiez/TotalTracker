package US.bittiez.TotalTracker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

public class main extends JavaPlugin implements Listener{
    public static boolean debug = true;
    public static Logger log;
    private static Long processEveryMinutes = 5L;
    private static int MaxCapacity = 150;
    private ArrayList<QueObject> QueObjects;

    public FileConfiguration playerVersion;
    public FileConfiguration config = getConfig();
    public static String prefix;
    public static String database;
    public static File playerVersionFile;

    @Override
    public void onEnable() {
        log = getLogger();
        QueObjects = new ArrayList<QueObject>();
        createConfig();
        playerVersionFile = new File(Paths.get(this.getDataFolder().getAbsolutePath().toString(), "players.yml").toAbsolutePath().toString());

        if(!config.getBoolean("setup_complete")){
            log.warning("You must edit your config file and restart the server to finish setting up TotalTracker. Make sure to change setup_complete to true when you are finished.");
            setEnabled(false);
        } else {

            prefix = config.getString("mysql_db_prefix", "") + "TotalTracker";
            database = config.getString("mysql_database");
            processEveryMinutes = config.getLong("sync_interval", 5);
            MaxCapacity = config.getInt("max_before_sync", 150);
            debug = config.getBoolean("debug", false);

            PluginManager pm = getServer().getPluginManager();
            pm.registerEvents(this, this);

            BukkitScheduler scheduler = getServer().getScheduler();
            scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    if (QueObjects.size() > 0) {
                        runQue();
                    }
                }
            }, (20L * 60L) * processEveryMinutes, (20L * 60L) * processEveryMinutes);

            tableSetup();

            playerVersion = YamlConfiguration.loadConfiguration(playerVersionFile);

        }
    }

    public void tableSetup(){
        ArrayList<String> queries = SQLTABLE.genSQL(config, getDataFolder());
        if(queries.size() > 0) {
            Sql2o SQL = new Sql2o(genMySQLUrl(config), config.getString("mysql_username"), config.getString("mysql_password"));
            try(Connection con = SQL.open()) {
                for (String q : queries) {
                    if (debug)
                        log.info("RUN SQL: " + q);
                    con.createQuery(q).executeUpdate();
                }
            } catch (Exception e) {
                log.severe("Failed to connect to the database, make sure your connection information is correct!");
                if(debug)
                    e.printStackTrace();
            }
        }
    }

    public static String genMySQLUrl(FileConfiguration config){
        return "jdbc:mysql://" + config.getString("mysql_address") + ":" + config.getString("mysql_port") + "/" + config.getString("mysql_database");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (cmd.getName().equalsIgnoreCase("tt")) {
            if (args[0].equalsIgnoreCase("sync") && sender.hasPermission("TotalTracker.sync")) {
                runQue();
                sender.sendMessage("Processing the que now!");
                return true;
            } else if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("TotalTracker.reload")){
                this.reloadConfig();
                config = getConfig();
                sender.sendMessage("Config reloaded!");
                return true;
            }
        }
        return false;
    }

    private void runQue(){
        QueProcessor qp = new QueProcessor(new ArrayList<>(QueObjects), log, config);
        QueObjects.clear();
        qp.runTaskAsynchronously(this);
    }

    private void createConfig() {
        config.options().copyDefaults();
        saveDefaultConfig();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();
            Entity ekiller = nEvent.getDamager();
            if (ekiller instanceof Player) {
                //Player killed entity
                Player p = (Player)ekiller;
                QueObject queObject = new QueObject(p.getUniqueId().toString(), SQLTABLE.MOB_KILLS, p.getDisplayName());
                QueObjects.add(queObject);
            }
        }

    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();
        if(killer != null){
            QueObject queObject = new QueObject(killer.getUniqueId().toString(), SQLTABLE.PVP_KILLS, killer.getDisplayName());
            QueObjects.add(queObject);
        }
        if(victim != null){
            QueObject queObject = new QueObject(victim.getUniqueId().toString(), SQLTABLE.DEATHS, victim.getDisplayName());
            QueObjects.add(queObject);
        }
        if(QueObjects.size() >= MaxCapacity)
            runQue();
    }
    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e){
        QueObjects.add(new QueObject(e.getPlayer().getUniqueId().toString(), SQLTABLE.JOINS, e.getPlayer().getDisplayName()));
        checkQue();

        if(config.getBoolean("auto_import", true)){
            new ImportProcessor(e.getPlayer(), playerVersion, QueObjects).run();
        }
    }
    @EventHandler
    public void OnItemPickedUp(PlayerPickupItemEvent e){
        QueObjects.add(new QueObject(e.getPlayer().getUniqueId().toString(), SQLTABLE.ITEM_PICKUP, e.getPlayer().getDisplayName()));
        checkQue();
    }
    @EventHandler
    public void OnEntityDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player)e.getEntity();
            QueObject de = new QueObject(p.getUniqueId().toString(), SQLTABLE.DAMAGE_TAKEN, p.getDisplayName());
            de.Quantity = (int)e.getFinalDamage();
            QueObjects.add(de);
            checkQue();
        }
    }
    @EventHandler
    public void OnEntityDamageByEntity(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player p = (Player)e.getDamager();
            QueObject de = new QueObject(p.getUniqueId().toString(), SQLTABLE.DAMAGE_CAUSED, p.getDisplayName());
            de.Quantity = (int)e.getFinalDamage();
            QueObjects.add(de);
            checkQue();
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(e.getPlayer() != null)
            QueObjects.add(new QueObject(e.getPlayer().getUniqueId().toString(), SQLTABLE.BLOCKS_BROKEN, e.getPlayer().getDisplayName()));
        checkQue();
    }
    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent e){
        if(e.getPlayer() != null)
            QueObjects.add(new QueObject(e.getPlayer().getUniqueId().toString(), SQLTABLE.BLOCKS_PLACED, e.getPlayer().getDisplayName()));
        checkQue();
    }

    private void checkQue(){
        if(QueObjects.size() >= MaxCapacity)
            runQue();
    }
}
