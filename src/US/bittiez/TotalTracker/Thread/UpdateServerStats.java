package US.bittiez.TotalTracker.Thread;

import US.bittiez.TotalTracker.Models.TotalStats;
import US.bittiez.TotalTracker.SQLTABLE;
import US.bittiez.TotalTracker.main;
import org.bukkit.configuration.file.FileConfiguration;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class UpdateServerStats implements Runnable{
    private FileConfiguration config;
    private String sql = "SELECT id FROM " + SQLTABLE.genFullTableSQL(true) + " LIMIT 1";

    public UpdateServerStats(FileConfiguration config){

        this.config = config;
    }

    @Override
    public void run() {
        Sql2o SQL = new Sql2o(main.genMySQLUrl(config), config.getString("mysql_username"), config.getString("mysql_password"));
        TotalStats totalStats;
            try (Connection con = SQL.open()) {
                totalStats = con.createQuery(sql).executeAndFetchFirst(TotalStats.class);
            }
        if(totalStats != null){

        }
    }
}
