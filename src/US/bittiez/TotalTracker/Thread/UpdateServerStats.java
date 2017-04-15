package US.bittiez.TotalTracker.Thread;

import org.bukkit.configuration.file.FileConfiguration;

public class UpdateServerStats implements Runnable{
    private FileConfiguration config;

    public UpdateServerStats(FileConfiguration config){

        this.config = config;
    }

    @Override
    public void run() {

    }
}
