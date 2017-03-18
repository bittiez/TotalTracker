package US.bittiez.TotalTracker;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.logging.Logger;

public class QueProcessor extends BukkitRunnable {
    public ArrayList<QueObject> QueObjects;
    public Logger log;

    public QueProcessor(ArrayList<QueObject> QueObjects, Logger log){
        this.QueObjects = QueObjects;
        this.log = log;
    }

    @Override
    public void run() {
        //Is there a que? Does it have anything in it?
        if(QueObjects != null && QueObjects.size() > 0){
            if(main.debug)
                log.info("Trying to process a que of " + QueObjects.size() + " QueObjects.");
        }
    }
}
