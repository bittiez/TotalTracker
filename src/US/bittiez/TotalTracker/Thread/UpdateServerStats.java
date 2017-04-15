package US.bittiez.TotalTracker.Thread;

import US.bittiez.TotalTracker.Models.TotalStats;
import US.bittiez.TotalTracker.SQLTABLE;
import US.bittiez.TotalTracker.main;
import org.bukkit.configuration.file.FileConfiguration;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.logging.Logger;

public class UpdateServerStats implements Runnable{
    private FileConfiguration config;
    private Logger log;
    private String sql = "SELECT id FROM " + SQLTABLE.genFullTableSQL(true) + " LIMIT 1";

    public UpdateServerStats(FileConfiguration config, Logger log){

        this.config = config;
        this.log = log;
    }

    @Override
    public void run() {
        Sql2o SQL = new Sql2o(main.genMySQLUrl(config), config.getString("mysql_username"), config.getString("mysql_password"));
        TotalStats totalStats;
            try (Connection con = SQL.open()) {
                totalStats = con.createQuery(sql).executeAndFetchFirst(TotalStats.class);
            }
        if(totalStats != null){
            if(main.debug)
                log.info("Total Stats ID: " + totalStats.id); //Working
//            UPDATE `teTotalTracker_server` c
//            INNER JOIN (
//                    SELECT SUM(pvp_kills) as total FROM `teTotalTracker`
//            ) x
//            SET c.pvp_kills = x.total
        }
    }
}
