package US.bittiez.TotalTracker;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SQLTABLE {
    public final static String PLAYER = "player";
    public final static String PVP_KILLS = "pvp_kills";
    public final static String DEATHS = "deaths";
    public final static String MOB_KILLS = "mob_kills";
    public final static String BLOCKS_BROKEN = "blocks_broken";
    public final static String BLOCKS_PLACED = "blocks_placed";

    public static ArrayList<String> genSQL(FileConfiguration config, File dataPath){
        int version = config.getInt("db_version", 1);
        ArrayList<String> sqlQueries = new ArrayList<String>();

        if(version == 1) {
            StringBuilder sql = new StringBuilder();

            sqlQueries.add("DROP TABLE IF EXISTS " + genFullTableSQL() + ";");

            sql.append("CREATE TABLE IF NOT EXISTS "+genFullTableSQL()+" (");
            sql.append("`id` int(10) not null auto_increment, ");
            sql.append("`" + PLAYER + "` char(36), ");
            sql.append("`" + PLAYER + "_name` text(32), ");
            sql.append("`" + PVP_KILLS + "` int(10), ");
            sql.append("`" + DEATHS + "` int(10), ");
            sql.append("`" + MOB_KILLS + "` int(10), ");
            sql.append("`" + BLOCKS_PLACED + "` int(10), ");
            sql.append("PRIMARY KEY (`id`)");
            sql.append(") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;");
            sqlQueries.add(sql.toString());
            version++;
        }
        if(version == 2){
            sqlQueries.add("ALTER TABLE "+genFullTableSQL()+" ADD `"+ BLOCKS_BROKEN +"` int(10);");
            version++;
        }

        if(version != config.getInt("db_version", 1)){
            try {
                config.set("db_version", version);
                if (main.debug)
                    main.log.info("Saving config file at: " + Paths.get(dataPath.getPath(), "TotalTracker", "config.yml").toString());
                config.save(Paths.get(dataPath.getPath(), "config.yml").toString());
            } catch (Exception e) {
                main.log.severe("Could not save the config file, please manually change db_version to " + version + " before starting your server next!");
                if (main.debug)
                    e.printStackTrace();
            }
        }
        return sqlQueries;
    }

    public static String genINSERT(String player, QueObject queObject){
        return "";
    }

    public static String genFullTableSQL(){
        return "`" + main.database + "`.`" + main.prefix + "`";
    }
}
