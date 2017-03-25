package US.bittiez.TotalTracker;

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

            for (QueObject o : QueObjects) {
                Boolean addNew = true;

                //Combine same QueTypes
                for (QueObject co : ConsolidatedQueObjects) {
                    if (co.Player.equals(o.Player)) {
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
                        if (main.debug) {
                            log.info("Saving [" + co.Quantity + "] x [" + co.QueType + "] for [" + co.PlayerName + "]");
                            log.info("RUNSQL: " + SQLTABLE.genINSERT(co));
                        }
                        con.createQuery(SQLTABLE.genINSERT(co)).executeUpdate();
                    }
                } catch (Exception e) {
                    log.severe("Failed to connect to the database, make sure your connection information is correct!");
                    if (main.debug)
                        e.printStackTrace();
                }
            }
        }
    }
}
