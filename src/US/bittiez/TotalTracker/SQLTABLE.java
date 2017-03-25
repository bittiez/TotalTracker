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
    public final static String JOINS = "logins";
    public final static String DAMAGE_TAKEN = "damage_taken";
    public final static String DAMAGE_CAUSED = "damage_caused";
    public final static String ITEM_PICKUP = "items_picked_up";
    public final static String PLAYER_CHAT = "chat_messages";

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
            sql.append("`" + PVP_KILLS + "` int(10) default '0', ");
            sql.append("`" + DEATHS + "` int(10) default '0', ");
            sql.append("`" + MOB_KILLS + "` int(10) default '0', ");
            sql.append("`" + BLOCKS_PLACED + "` int(10) default '0', ");
            sql.append("PRIMARY KEY (`id`),");
            sql.append("UNIQUE KEY (`player`)");
            sql.append(") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;");
            sqlQueries.add(sql.toString());
            version++;
        }
        if(version == 2){
            sqlQueries.add("ALTER TABLE "+genFullTableSQL()+" ADD `"+ BLOCKS_BROKEN +"` int(10) default '0';");
            version++;
        }
        if(version == 3){
            sqlQueries.add("ALTER TABLE "+genFullTableSQL()+" ADD `"+ JOINS +"` int(10) default '0';");
            version++;
        }
        if(version == 4){
            sqlQueries.add("ALTER TABLE "+genFullTableSQL()+" ADD `"+ DAMAGE_TAKEN +"` int(10) default '0';");
            version++;
        }
        if(version == 5){
            sqlQueries.add("ALTER TABLE "+genFullTableSQL()+" ADD `"+ DAMAGE_CAUSED +"` int(10) default '0';");
            version++;
        }
        if(version == 6){
            sqlQueries.add("ALTER TABLE "+genFullTableSQL()+" ADD `"+ ITEM_PICKUP +"` int(10) default '0';");
            version++;
        }
        if(version == 7){
            sqlQueries.add("ALTER TABLE "+genFullTableSQL()+" ADD `"+ PLAYER_CHAT +"` int(10) default '0';");
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

    public static String genINSERT(QueObject queObject){
        String insert = "INSERT INTO "
                + genFullTableSQL()
                + " (" + PLAYER + ", " + PLAYER + "_name, " + queObject.QueType + ")"
                + " VALUES (" + MySQLQuotes(queObject.Player) + ", " + MySQLQuotes(queObject.PlayerName) + ", " + queObject.Quantity + ") "
                + "ON DUPLICATE KEY UPDATE "
                + queObject.QueType + "=" + queObject.QueType + "+" + queObject.Quantity + ", "
                + PLAYER + "_name=" + MySQLQuotes(queObject.PlayerName) + ";"
            ;

        return insert;
    }

    public static String genFullTableSQL(){
        return "`" + main.database + "`.`" + main.prefix + "`";
    }

    private static String MySQLQuotes(String original){
        return "'" + original + "'";
    }
}
