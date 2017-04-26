package US.bittiez.TotalTracker;

import US.bittiez.TotalTracker.Models.TotalStats;
import US.bittiez.TotalTracker.Sql.Stats;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.logging.Logger;

public class QueProcessor extends BukkitRunnable {
    public ArrayList<QueObject> QueObjects;
    public Logger log;

    private ArrayList<QueObject> ConsolidatedQueObjects;
    private FileConfiguration config;

    public QueProcessor(ArrayList<QueObject> QueObjects, Logger log, FileConfiguration config) {
        this.QueObjects = QueObjects;
        ConsolidatedQueObjects = new ArrayList<>();
        this.log = log;
        this.config = config;
    }

    @Override
    public void run() {
        //Is there a que? Does it have anything in it?
        if (QueObjects != null && QueObjects.size() > 0) {
            if (main.debug)
                log.info("Trying to process a que of " + QueObjects.size() + " QueObjects.");

            TotalStats totalStats = SQLTABLE.getServerStatId(config);

            for (QueObject o : QueObjects) {
                Boolean addNew = true;

                //Combine same QueTypes
                for (QueObject co : ConsolidatedQueObjects) {
                    if (co.PlayerUUID.equals(o.PlayerUUID)) {
                        if (co.QueType.equals(o.QueType)) {
                            co.Quantity += o.Quantity;
                            addNew = false;
                            break;
                        }
                    }
                }

                if (addNew) {
                    ConsolidatedQueObjects.add(o);
                }
            }
            //Done consolidating, time to do the MySQL
            if (ConsolidatedQueObjects.size() > 0) {
                Sql2o SQL = new Sql2o(main.genMySQLUrl(config), config.getString("mysql_username"), config.getString("mysql_password"));
                try (Connection con = SQL.open()) {
                    for (QueObject co : ConsolidatedQueObjects) {
                        if (co.Quantity > 0) {
                            if (main.debug) {
                                log.info("Saving [" + co.Quantity + "] x [" + co.QueType + "] for [" + co.PlayerName + "]");
                                log.info("RUNSQL: " + SQLTABLE.genINSERT(co));
                                log.info("RUNSQL: " + SQLTABLE.genServerInsert(co, totalStats.id));
                            }

                            con.createQuery(SQLTABLE.genINSERT(co)).executeUpdate();
                            if (totalStats != null && !co.QueType.equals(Stats.CURRENT_MONEY.toString()))
                                con.createQuery(SQLTABLE.genServerInsert(co, totalStats.id)).executeUpdate();
                        }
                        co.sentToDataBase = true;
                    }
                } catch (Exception e) {
                    log.severe("Failed to connect to the database, make sure your connection information is correct!");
                    log.severe("Due to a connection failure, there may be query's that were not run, please check your database and manually run these:");
                    for (QueObject co : ConsolidatedQueObjects)
                        if (!co.sentToDataBase) {
                            log.severe(SQLTABLE.genINSERT(co));
                            if (totalStats != null)
                                log.severe(SQLTABLE.genServerInsert(co, totalStats.id));
                        }
                    if (main.debug)
                        e.printStackTrace();
                }
            }
        }
    }
}
