package US.bittiez.TotalTracker;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.logging.Logger;

public class queProcessor extends BukkitRunnable {
    public ArrayList<queObject> queObjects;
    public Logger log;

    public queProcessor(ArrayList<queObject> queObjects, Logger log){
        this.queObjects = queObjects;
        this.log = log;
    }

    @Override
    public void run() {
        //Is there a que? Does it have anything in it?
        if(queObjects != null && queObjects.size() > 0){
            if(main.debug)
                log.info("Trying to process a que of " + queObjects.size() + " queObjects.");
        }
    }
}
