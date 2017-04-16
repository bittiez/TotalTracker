package US.bittiez.TotalTracker.Sql;

public enum Stats {
    PLAYER("player"),
    PVP_KILLS("pvp_kills"),
    DEATHS("deaths"),
    MOB_KILLS("mob_kills"),
    BLOCKS_BROKEN("blocks_broken"),
    BLOCKS_PLACED("blocks_placed"),
    JOINS("logins"),
    DAMAGE_TAKEN("damage_taken"),
    DAMAGE_CAUSED("damage_caused"),
    ITEM_PICKUP("items_picked_up"),
    PLAYER_CHAT("chat_messages"),
    ITEMS_CRAFTED("items_crafted"),
    XP_GAINED("xp_gained"),
    TIME_PLAYED("time_played"),
    FOOD_EATEN("food_eaten"),
    ITEMS_DROPPED("items_dropped"),
    ITEMS_ENCHANTED("items_enchanted"),
    ARROW_SHOT("arrows_shot"),
    TOOL_BROKEN("tools_broken"),
    BUCKETS_FILLED("buckets_filled"),
    BUCKETS_EMPTIED("buckets_emptied"),
    FISH_CAUGHT("fish_caught"),
    WORDS_SPOKEN("words_spoken")
    ;

    private final String name;
    private Stats(String n){
        name = n;
    }

    @Override
    public String toString(){
        return  this.name;
    }
}
