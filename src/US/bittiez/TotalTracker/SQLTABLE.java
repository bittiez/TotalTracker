package US.bittiez.TotalTracker;

import US.bittiez.TotalTracker.Models.TotalStats;
import US.bittiez.TotalTracker.Sql.Stats;
import org.bukkit.configuration.file.FileConfiguration;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SQLTABLE {
    private final static String[][] STATS = {
            {}
    };
    private final static String SERVER_SUFFIX = "_server";
    public final static String PLAYER = Stats.PLAYER.toString();
    public final static String PVP_KILLS = Stats.PVP_KILLS.toString();
    public final static String DEATHS = Stats.DEATHS.toString();
    public final static String MOB_KILLS = Stats.MOB_KILLS.toString();
    public final static String BLOCKS_BROKEN = Stats.BLOCKS_BROKEN.toString();
    public final static String BLOCKS_PLACED = Stats.BLOCKS_PLACED.toString();
    public final static String JOINS = Stats.JOINS.toString();
    public final static String DAMAGE_TAKEN = Stats.DAMAGE_TAKEN.toString();
    public final static String DAMAGE_CAUSED = Stats.DAMAGE_CAUSED.toString();
    public final static String ITEM_PICKUP = Stats.ITEM_PICKUP.toString();
    public final static String PLAYER_CHAT = Stats.PLAYER_CHAT.toString();
    public final static String ITEMS_CRAFTED = Stats.ITEMS_CRAFTED.toString();
    public final static String XP_GAINED = Stats.XP_GAINED.toString();
    public final static String TIME_PLAYED = Stats.TIME_PLAYED.toString();
    public final static String FOOD_EATEN = Stats.FOOD_EATEN.toString();
    public final static String ITEMS_DROPPED = Stats.ITEMS_DROPPED.toString();
    public final static String ITEMS_ENCHANTED = Stats.ITEMS_ENCHANTED.toString();
    public final static String ARROWS_SHOT = Stats.ARROW_SHOT.toString();
    public final static String TOOLS_BROKEN = Stats.TOOL_BROKEN.toString();
    public final static String BUCKETS_FILLED = Stats.BUCKETS_FILLED.toString();
    public final static String BUCKETS_EMPTIED = Stats.BUCKETS_EMPTIED.toString();
    public final static String FISH_CAUGHT = Stats.FISH_CAUGHT.toString();
    public final static String WORDS_SPOKEN = Stats.WORDS_SPOKEN.toString();

    public static ArrayList<String> genSQL(FileConfiguration config, File dataPath) {
        int version = config.getInt("db_version", 1);
        ArrayList<String> sqlQueries = new ArrayList<String>();

        if (version == 1) {
            StringBuilder sql = new StringBuilder();

            sqlQueries.add("DROP TABLE IF EXISTS " + genFullTableSQL() + ";");

            sql.append("CREATE TABLE IF NOT EXISTS " + genFullTableSQL() + " (");
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
        if (version == 2) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + BLOCKS_BROKEN + "` int(10) default '0';");
            version++;
        }
        if (version == 3) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + JOINS + "` int(10) default '0';");
            version++;
        }
        if (version == 4) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + DAMAGE_TAKEN + "` int(10) default '0';");
            version++;
        }
        if (version == 5) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + DAMAGE_CAUSED + "` int(10) default '0';");
            version++;
        }
        if (version == 6) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + ITEM_PICKUP + "` int(10) default '0';");
            version++;
        }
        if (version == 7) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + PLAYER_CHAT + "` int(10) default '0';");
            version++;
        }
        if (version == 8) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + ITEMS_CRAFTED + "` int(10) default '0';");
            version++;
        }
        if (version == 9) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + XP_GAINED + "` int(10) default '0';");
            version++;
        }
        if (version == 10) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + TIME_PLAYED + "` int(10) default '0';");
            version++;
        }
        if (version == 11) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + FOOD_EATEN + "` int(10) default '0';");
            version++;
        }
        if (version == 12) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + ITEMS_DROPPED + "` int(10) default '0';");
            version++;
        }
        if (version == 13) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + ITEMS_ENCHANTED + "` int(10) default '0';");
            version++;
        }
        if (version == 14) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + ARROWS_SHOT + "` int(10) default '0';");
            version++;
        }
        if (version == 15) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + TOOLS_BROKEN + "` int(10) default '0';");
            version++;
        }
        if (version == 16) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + BUCKETS_FILLED + "` int(10) default '0';");
            version++;
        }
        if (version == 17) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + BUCKETS_EMPTIED + "` int(10) default '0';");
            version++;
        }
        if (version == 18) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + FISH_CAUGHT + "` int(10) default '0';");
            version++;
        }
        if (version == 19) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + WORDS_SPOKEN + "` int(10) default '0';");
            version++;
        }
        if (version == 20) {
            StringBuilder sql = new StringBuilder();

            sqlQueries.add("DROP TABLE IF EXISTS " + genFullTableSQL(true) + ";");

            sql.append("CREATE TABLE IF NOT EXISTS ").append(genFullTableSQL(true)).append(" (");
            sql.append("`id` int(10) not null auto_increment, ");
            sql.append("`" + PVP_KILLS + "` BIGINT(20) default '0', ");
            sql.append("`" + DEATHS + "` BIGINT(20) default '0', ");
            sql.append("`" + MOB_KILLS + "` BIGINT(20) default '0', ");
            sql.append("`" + BLOCKS_PLACED + "` BIGINT(20) default '0', ");
            sql.append("`" + BLOCKS_BROKEN + "` BIGINT(20) default '0', ");
            sql.append("`" + JOINS + "` BIGINT(20) default '0', ");
            sql.append("`" + DAMAGE_TAKEN + "` BIGINT(20) default '0', ");
            sql.append("`" + DAMAGE_CAUSED + "` BIGINT(20) default '0', ");
            sql.append("`" + ITEM_PICKUP + "` BIGINT(20) default '0', ");
            sql.append("`" + PLAYER_CHAT + "` BIGINT(20) default '0', ");
            sql.append("`" + ITEMS_CRAFTED + "` BIGINT(20) default '0', ");
            sql.append("`" + XP_GAINED + "` BIGINT(20) default '0', ");
            sql.append("`" + TIME_PLAYED + "` BIGINT(20) default '0', ");
            sql.append("`" + FOOD_EATEN + "` BIGINT(20) default '0', ");
            sql.append("`" + ITEMS_DROPPED + "` BIGINT(20) default '0', ");
            sql.append("`" + ITEMS_ENCHANTED + "` BIGINT(20) default '0', ");
            sql.append("`" + ARROWS_SHOT + "` BIGINT(20) default '0', ");
            sql.append("`" + TOOLS_BROKEN + "` BIGINT(20) default '0', ");
            sql.append("`" + BUCKETS_FILLED + "` BIGINT(20) default '0', ");
            sql.append("`" + BUCKETS_EMPTIED + "` BIGINT(20) default '0', ");
            sql.append("`" + FISH_CAUGHT + "` BIGINT(20) default '0', ");
            sql.append("`" + WORDS_SPOKEN + "` BIGINT(20) default '0', ");
            sql.append("PRIMARY KEY (`id`)");
            sql.append(") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;");
            sqlQueries.add(sql.toString());
            sqlQueries.add("INSERT INTO " + genFullTableSQL(true) + " (pvp_kills) VALUES (0);");
            version++;
        }
        if (version == 21) {
            sqlQueries.add("ALTER TABLE " + genFullTableSQL() + " ADD `" + Stats.CURRENT_MONEY.toString() + "` double(12,2) default '0.0';");
            sqlQueries.add("ALTER TABLE " + genFullTableSQL(true) + " ADD `" + Stats.CURRENT_MONEY.toString() + "` double(12,2) default '0.0';");
            version++;
        }


        if (version != config.getInt("db_version", 1)) {
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

    public static String genINSERT(QueObject queObject) {
        String insert = "INSERT INTO "
                + genFullTableSQL()
                + " (" + PLAYER + ", " + PLAYER + "_name, " + queObject.QueType + ")"
                + " VALUES (" + MySQLQuotes(queObject.PlayerUUID) + ", " + MySQLQuotes(queObject.PlayerName) + ", " + queObject.Quantity + ") "
                + "ON DUPLICATE KEY UPDATE "
                + queObject.QueType + "=" + queObject.QueType + "+" + queObject.Quantity + ", "
                + PLAYER + "_name=" + MySQLQuotes(queObject.PlayerName) + ";";

        return insert;
    }

    public static String genServerInsert(QueObject queObject, long rowID) {
        if(queObject.QueType.equals(Stats.CURRENT_MONEY)) {
            String insert = "INSERT INTO "
                    + genFullTableSQL(true)
                    + " (id, " + queObject.QueType + ")"
                    + " VALUES (" + rowID + ", " + queObject.Quantity + ")"
                    + " ON DUPLICATE KEY UPDATE "
                    + queObject.QueType + "=" + queObject.Quantity + ";";
            return insert;
        } else {
            String insert = "INSERT INTO "
                    + genFullTableSQL(true)
                    + " (id, " + queObject.QueType + ")"
                    + " VALUES (" + rowID + ", " + queObject.Quantity + ")"
                    + " ON DUPLICATE KEY UPDATE "
                    + queObject.QueType + "=" + queObject.QueType + "+" + queObject.Quantity + ";";
            return insert;
        }
    }

    public static TotalStats getServerStatId(FileConfiguration config) {
        Sql2o SQL = new Sql2o(main.genMySQLUrl(config), config.getString("mysql_username"), config.getString("mysql_password"));
        String sql = "SELECT id FROM " + SQLTABLE.genFullTableSQL(true) + " LIMIT 1";
        TotalStats totalStats;

        try (Connection con = SQL.open()) {
            totalStats = con.createQuery(sql).executeAndFetchFirst(TotalStats.class);
        } catch (Exception e) {
            try (Connection con = SQL.open()) {
                con.createQuery("INSERT INTO " + genFullTableSQL(true) + " (pvp_kills) VALUES (0);").executeUpdate();
                totalStats = con.createQuery(sql).executeAndFetchFirst(TotalStats.class);
            }
        }
        return totalStats;

    }

    public static String genFullTableSQL() {
        return "`" + main.database + "`.`" + main.prefix + "`";
    }

    public static String genFullTableSQL(boolean serverStats) {
        if (serverStats)
            return "`" + main.database + "`.`" + main.prefix + SERVER_SUFFIX + "`";
        else
            return genFullTableSQL();
    }

    private static String MySQLQuotes(String original) {
        return "'" + original + "'";
    }
}
