package US.bittiez.TotalTracker;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;

public class ImportProcessor extends BukkitRunnable {
    private Player player;
    private FileConfiguration playerVersion;
    private ArrayList<QueObject> QueObjects;

    public ImportProcessor(Player player, FileConfiguration playerVersion, ArrayList<QueObject> QueObjects) {
        this.player = player;
        this.playerVersion = playerVersion;
        this.QueObjects = QueObjects;
    }

    @Override
    public void run() {
        int v = playerVersion.getInt(player.getUniqueId().toString() + ".version", 1);


        if (v == 1) {
            QueObjects.add(new QueObject(player, SQLTABLE.PVP_KILLS, player.getStatistic(Statistic.PLAYER_KILLS)));
            QueObjects.add(new QueObject(player, SQLTABLE.DEATHS, player.getStatistic(Statistic.DEATHS)));
            QueObjects.add(new QueObject(player, SQLTABLE.MOB_KILLS, player.getStatistic(Statistic.MOB_KILLS)));
            //Missing: Blocks Placed
            v++;
        }
        if (v == 2)
            v++;
        if (v == 3) {
            QueObject qe = new QueObject(player.getUniqueId().toString(), SQLTABLE.JOINS, player.getName());
            qe.Quantity = player.getStatistic(Statistic.LEAVE_GAME);
            QueObjects.add(qe);
            v++;
        }
        if (v == 4) {
            QueObject qe = new QueObject(player.getUniqueId().toString(), SQLTABLE.DAMAGE_TAKEN, player.getName());
            qe.Quantity = player.getStatistic(Statistic.DAMAGE_TAKEN) / 10;
            QueObjects.add(qe);
            v++;
        }
        if (v == 5) {
            QueObject qe = new QueObject(player.getUniqueId().toString(), SQLTABLE.DAMAGE_CAUSED, player.getName());
            qe.Quantity = player.getStatistic(Statistic.DAMAGE_DEALT) / 10;
            QueObjects.add(qe);
            v++;
        }
        if (v == 6)
            v++; //Items picked up had to be per individual items
        if (v == 7)
            v++; //Items crafted statistic has to be per individual item
        if (v == 8)
            v++; //No total xp gained statistic
        if (v == 9) {
            QueObject qe = new QueObject(player, SQLTABLE.TIME_PLAYED);
            // 20 ticks per second
            qe.Quantity = (player.getStatistic(Statistic.PLAY_ONE_TICK) / 20) / 60;
            QueObjects.add(qe);
            v++;
        }
        if (v == 10) {
            QueObjects.add(new QueObject(player, SQLTABLE.FOOD_EATEN, player.getStatistic(Statistic.CAKE_SLICES_EATEN)));
            v++;
        }
        if (v == 11) {
            try {
                for (Material m : Material.values())
                    if (m.isBlock())
                        QueObjects.add(new QueObject(player, SQLTABLE.BLOCKS_BROKEN, player.getStatistic(Statistic.MINE_BLOCK, m)));
            } catch(Exception e) {}

            v++;
        }
        if (v == 12) {
            QueObjects.add(new QueObject(player, SQLTABLE.ITEMS_ENCHANTED, player.getStatistic(Statistic.ITEM_ENCHANTED)));
            v++;
        }
        if (v == 13) {
            try {
                for (Material m : Material.values())
                    if (m.isBlock())
                        QueObjects.add(new QueObject(player, SQLTABLE.BLOCKS_PLACED, player.getStatistic(Statistic.USE_ITEM, m)));
            } catch(Exception e) {}
            v++;
        }

        if (v != playerVersion.getInt(player.getUniqueId().toString() + ".version")) {
            playerVersion.set(player.getUniqueId().toString() + ".version", v);
            try {
                playerVersion.save(main.playerVersionFile);
            } catch (IOException e) {
                if (main.debug)
                    e.printStackTrace();
            }
        }
    }
}
