package US.bittiez.TotalTracker.Thread;

import US.bittiez.TotalTracker.Models.TotalStats;
import US.bittiez.TotalTracker.SQLTABLE;
import US.bittiez.TotalTracker.Sql.Stats;
import US.bittiez.TotalTracker.main;
import org.bukkit.configuration.file.FileConfiguration;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.logging.Logger;

public class UpdateServerStats implements Runnable{
    private FileConfiguration config;
    private Logger log;

    public UpdateServerStats(FileConfiguration config, Logger log){

        this.config = config;
        this.log = log;
    }
//CREATE GET ID METHOD IN SQLTABLE...
    @Override
    public void run() {
        Sql2o SQL = new Sql2o(main.genMySQLUrl(config), config.getString("mysql_username"), config.getString("mysql_password"));
        TotalStats totalStats = SQLTABLE.getServerStatId(config);
        if(totalStats != null){
            if(main.debug)
                log.info("Total Stats ID: " + totalStats.id);
            String sql = "UPDATE "
                    + SQLTABLE.genFullTableSQL(true) + " c "
                    + "INNER JOIN ( SELECT SUM(%s) as total FROM " + SQLTABLE.genFullTableSQL() + " ) x "
                    + "SET c.%<s = x.total;";
            try (Connection con = SQL.open()) {
                for (Stats stat : Stats.values()) { //Loop through the stats
                    if (stat.equals(Stats.PLAYER))
                        continue;
                    String compiledSql = String.format(sql, stat.toString());
                    if (main.debug)
                        log.info("RUN SQL: " + compiledSql);
                    con.createQuery(compiledSql).executeUpdate();
                }
            }
        }
    }
}
