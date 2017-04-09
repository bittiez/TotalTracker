package US.bittiez.TotalTracker;

import US.bittiez.TotalTracker.Updater.UpdateChecker;
import US.bittiez.TotalTracker.Updater.UpdateStatus;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

public class main extends JavaPlugin implements Listener {
    public static boolean debug = true;
    public static Logger log;
    public static String prefix;
    public static String database;
    public static File playerVersionFile;
    private static Long processEveryMinutes = 5L;
    private static int MaxCapacity = 150;
    public FileConfiguration playerVersion;
    public FileConfiguration config = getConfig();
    private ArrayList<QueObject> queObjects;
    private boolean ignoreBrokenCreative = false;
    private boolean ignorePlacedCreative = false;
    private BukkitScheduler scheduler = getServer().getScheduler();
    private static UpdateStatus updateStatus;

    public static String genMySQLUrl(FileConfiguration config) {
        return "jdbc:mysql://" + config.getString("mysql_address") + ":" + config.getString("mysql_port") + "/" + config.getString("mysql_database");
    }

    @Override
    public void onEnable() {
        log = getLogger();
        queObjects = new ArrayList<QueObject>();
        createConfig();
        playerVersionFile = new File(Paths.get(this.getDataFolder().getAbsolutePath().toString(), "players.yml").toAbsolutePath().toString());

        if (!config.getBoolean("setup_complete")) {
            log.warning("You must edit your config file and restart the server to finish setting up TotalTracker. Make sure to change setup_complete to true when you are finished.");
            setEnabled(false);
        } else {

            prefix = config.getString("mysql_db_prefix", "") + "TotalTracker";
            database = config.getString("mysql_database");
            processEveryMinutes = config.getLong("sync_interval", 5);
            MaxCapacity = config.getInt("max_before_sync", 150);
            debug = config.getBoolean("debug", false);
            ignoreBrokenCreative = config.getBoolean("ignore_broken_creative", false);
            ignorePlacedCreative = config.getBoolean("ignore_placed_creative", false);

            PluginManager pm = getServer().getPluginManager();
            pm.registerEvents(this, this);

            tableSetup();
            playerVersion = YamlConfiguration.loadConfiguration(playerVersionFile);


            scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    if (queObjects.size() > 0) {
                        runQue();
                    }
                }
            }, (20L * 60L) * processEveryMinutes, (20L * 60L) * processEveryMinutes);
            scheduler.scheduleSyncRepeatingTask(this, () -> {
                for (Player player : getServer().getOnlinePlayers()) {
                    queObjects.add(new QueObject(player, SQLTABLE.TIME_PLAYED, 2));
                }
                checkQue();
            }, (20L * 60L) * 2, (20L * 60L) * 2); //Run every 2 minutes, starting in 2 minutes
            updateStatus = new UpdateChecker("https://raw.githubusercontent.com/bittiez/TotalTracker/master/src/plugin.yml", getDescription().getVersion()).getStatus();
            if (updateStatus.HasUpdate) {
                scheduler.scheduleSyncDelayedTask(this, () -> log.info(genVersionOutdatedMessage(updateStatus.LocalVersion, updateStatus.RemoteVersion)), (20 * 60) * 5);
            }
        }
    }

    public void tableSetup() {
        ArrayList<String> queries = SQLTABLE.genSQL(config, getDataFolder());
        if (queries.size() > 0) {
            Sql2o SQL = new Sql2o(genMySQLUrl(config), config.getString("mysql_username"), config.getString("mysql_password"));
            try {
                Connection con = SQL.open();
                for (String q : queries) {
                    if (debug)
                        log.info("RUN SQL: " + q);
                    con.createQuery(q).executeUpdate();
                }
            } catch (Exception e) {
                log.severe("Failed to connect to the database, make sure your connection information is correct!");
                log.severe("Due to a connection failure, there may be query's that were not run, please check your database and manually run these:");
                for (String q : queries)
                    log.severe(q);
                if (debug)
                    e.printStackTrace();
            }

        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (cmd.getName().equalsIgnoreCase("tt")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("sync") && sender.hasPermission("TotalTracker.sync")) {
                    runQue();
                    sender.sendMessage("Processing the que now!");
                    return true;
                } else if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("TotalTracker.reload")) {
                    this.reloadConfig();
                    config = getConfig();
                    sender.sendMessage("Config reloaded!");
                    return true;
                } else {
                    sender.sendMessage("It looks like you don't have permission to use that command!");
                }
            } else {
                sender.sendMessage("/tt usage: /tt ( sync || reload )");
            }
            return false;
        }
        return false;
    }

    private void runQue() {
        QueProcessor qp = new QueProcessor(new ArrayList<>(queObjects), log, config);
        queObjects.clear();
        qp.runTaskAsynchronously(this);
    }

    private String genVersionOutdatedMessage(String version, String updatedVersion) {
        return "Your version(" + version + ") of " + ChatColor.GOLD + " TotalTracker " + ChatColor.RESET + "is not up to date(" + updatedVersion + "), you can get the latest version at https://github.com/bittiez/TotalTracker/releases or https://www.spigotmc.org/resources/totaltracker.38304/";
    }

    private void createConfig() {
        config.options().copyDefaults();
        saveDefaultConfig();
    }

    @EventHandler
    public void onBucketFilled(PlayerBucketFillEvent e) {
        queObjects.add(new QueObject(e.getPlayer(), SQLTABLE.BUCKETS_FILLED));
    }

    @EventHandler
    public void onBucketEmptied(PlayerBucketEmptyEvent e) {
        queObjects.add(new QueObject(e.getPlayer(), SQLTABLE.BUCKETS_EMPTIED));
    }

    @EventHandler
    public void onArrowShot(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();
            queObjects.add(new QueObject(p, SQLTABLE.ARROWS_SHOT));
        }
    }

    @EventHandler
    public void onItemEnchanted(PrepareItemEnchantEvent e) {
        if (!e.isCancelled()) {
            queObjects.add(new QueObject(e.getEnchanter(), SQLTABLE.ITEMS_ENCHANTED));
            checkQue();
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();
            Entity ekiller = nEvent.getDamager();
            if (ekiller instanceof Player) {
                //Player killed entity
                Player p = (Player) ekiller;
                queObjects.add(new QueObject(p, SQLTABLE.MOB_KILLS));
                checkQue();
            }
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();
        if (killer != null) {
            QueObject queObject = new QueObject(killer.getUniqueId().toString(), SQLTABLE.PVP_KILLS, killer.getName());
            queObjects.add(queObject);
        }
        if (victim != null) {
            QueObject queObject = new QueObject(victim.getUniqueId().toString(), SQLTABLE.DEATHS, victim.getName());
            queObjects.add(queObject);
        }
        if (queObjects.size() >= MaxCapacity)
            runQue();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        queObjects.add(new QueObject(e.getPlayer(), SQLTABLE.JOINS));
        if (config.getBoolean("auto_import", true)) {
            new ImportProcessor(e.getPlayer(), playerVersion, queObjects).run();
        }
        if (e.getPlayer().isOp() || e.getPlayer().hasPermission("TotalTracker.updates")) {
            scheduler.scheduleSyncDelayedTask(this, () -> {
                if (e != null && e.getPlayer() != null)
                    e.getPlayer().sendMessage(genVersionOutdatedMessage(updateStatus.LocalVersion, updateStatus.RemoteVersion));
            }, (20 * 60) * 2);
        }
        checkQue();
    }

    @EventHandler
    public void onToolBreak(PlayerItemBreakEvent e) {
        queObjects.add(new QueObject(e.getPlayer(), SQLTABLE.TOOLS_BROKEN));
        checkQue();
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        queObjects.add(new QueObject(e.getPlayer(), SQLTABLE.ITEMS_DROPPED, e.getItemDrop().getItemStack().getAmount()));
        checkQue();
    }

    @EventHandler
    public void onItemPickedUp(PlayerPickupItemEvent e) {
        queObjects.add(new QueObject(e.getPlayer(), SQLTABLE.ITEM_PICKUP));
        checkQue();
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            QueObject de = new QueObject(p.getUniqueId().toString(), SQLTABLE.DAMAGE_TAKEN, p.getName());
            de.Quantity = (int) e.getFinalDamage();
            queObjects.add(de);
            checkQue();
        }
    }

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        queObjects.add(new QueObject(e.getPlayer().getUniqueId().toString(), SQLTABLE.PLAYER_CHAT, e.getPlayer().getName()));
        checkQue();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            QueObject de = new QueObject(p.getUniqueId().toString(), SQLTABLE.DAMAGE_CAUSED, p.getName());
            de.Quantity = (int) e.getFinalDamage();
            queObjects.add(de);
            checkQue();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (ignoreBrokenCreative && e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            return;
        if (e.getPlayer() != null) {
            queObjects.add(new QueObject(e.getPlayer().getUniqueId().toString(), SQLTABLE.BLOCKS_BROKEN, e.getPlayer().getName()));
            checkQue();
        }
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent e) {
        if (ignorePlacedCreative && e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            return;
        if (e.getPlayer() != null) {
            queObjects.add(new QueObject(e.getPlayer().getUniqueId().toString(), SQLTABLE.BLOCKS_PLACED, e.getPlayer().getName()));
            checkQue();
        }
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent e) {
        if (e.getWhoClicked() != null && e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            queObjects.add(new QueObject(p.getUniqueId().toString(), SQLTABLE.ITEMS_CRAFTED, p.getName()));
            checkQue();
        }
    }

    @EventHandler
    public void onXPGained(PlayerExpChangeEvent e) {
        QueObject qe = new QueObject(e.getPlayer(), SQLTABLE.XP_GAINED);
        qe.Quantity = e.getAmount();
        queObjects.add(qe);
        checkQue();
    }

    @EventHandler
    public void onFoodEated(PlayerItemConsumeEvent e) {
        ItemStack IS = e.getItem();
        if (IS.getType().isEdible()) {
            queObjects.add(new QueObject(e.getPlayer(), SQLTABLE.FOOD_EATEN));
        }
    }

    private void checkQue() {
        if (queObjects.size() >= MaxCapacity)
            runQue();
    }
}
