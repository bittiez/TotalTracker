package US.bittiez.TotalTracker;

import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by gamer on 3/24/2017.
 */
public class ImportProcessor extends BukkitRunnable {
    private Player player;
    private FileConfiguration playerVersion;
    private ArrayList<QueObject> QueObjects;

    public ImportProcessor(Player player, FileConfiguration playerVersion, ArrayList<QueObject> QueObjects){
        this.player = player;
        this.playerVersion = playerVersion;
        this.QueObjects = QueObjects;
    }

    @Override
    public void run() {
        int v = 1;
        if(playerVersion.contains(player.getUniqueId().toString() + ".version")){
            v = playerVersion.getInt(player.getUniqueId().toString() + ".version");
        }


        if(v == 1) {
            QueObject qe = new QueObject(player.getUniqueId().toString(), SQLTABLE.PVP_KILLS, player.getDisplayName());
            qe.Quantity = player.getStatistic(Statistic.PLAYER_KILLS);
            QueObjects.add(qe);

            qe = new QueObject(player.getUniqueId().toString(), SQLTABLE.DEATHS, player.getDisplayName());
            qe.Quantity = player.getStatistic(Statistic.DEATHS);
            QueObjects.add(qe);

            qe = new QueObject(player.getUniqueId().toString(), SQLTABLE.MOB_KILLS, player.getDisplayName());
            qe.Quantity = player.getStatistic(Statistic.MOB_KILLS);
            QueObjects.add(qe);
            //Missing: Blocks Placed
            v++;
        }
        if(v == 2)
        {
         v++;
        }
        if(v == 3) {
            QueObject qee = new QueObject(player.getUniqueId().toString(), SQLTABLE.JOINS, player.getDisplayName());
            qee.Quantity = player.getStatistic(Statistic.LEAVE_GAME);
            QueObjects.add(qee);
            v++;
        }
        if(v == 4) {
            QueObject qeee = new QueObject(player.getUniqueId().toString(), SQLTABLE.DAMAGE_TAKEN, player.getDisplayName());
            qeee.Quantity = player.getStatistic(Statistic.DAMAGE_TAKEN)/10;
            QueObjects.add(qeee);
            v++;
        }
        if(v == 5) {
            QueObject we = new QueObject(player.getUniqueId().toString(), SQLTABLE.DAMAGE_CAUSED, player.getDisplayName());
            we.Quantity = player.getStatistic(Statistic.DAMAGE_DEALT)/10;
            QueObjects.add(we);
            v++;
        }


        playerVersion.set(player.getUniqueId().toString() + ".version", v);
        try {
            playerVersion.save(main.playerVersionFile);
        } catch (IOException e) {
            if(main.debug)
                e.printStackTrace();
        }
    }
}
