package US.bittiez.TotalTracker;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.logging.Logger;

public class QueProcessor extends BukkitRunnable {
    public ArrayList<QueObject> QueObjects;
    public Logger log;

    private ArrayList<QueObject> ConsolidatedQueObjects;

    public QueProcessor(ArrayList<QueObject> QueObjects, Logger log){
        this.QueObjects = QueObjects;
        ConsolidatedQueObjects = new ArrayList<>();
        this.log = log;
    }

    @Override
    public void run() {
        //Is there a que? Does it have anything in it?
        if(QueObjects != null && QueObjects.size() > 0){
            if(main.debug)
                log.info("Trying to process a que of " + QueObjects.size() + " QueObjects.");

            for(QueObject o : QueObjects){
                Boolean addNew = true;

                //Combine same QueTypes
                for(QueObject co : ConsolidatedQueObjects){
                    if (co.Player == o.Player) {
                        if (co.QueType == o.QueType) {
                            co.Quantity += o.Quantity;
                            addNew = false;
                            break;
                        }
                    }
                }

                if(addNew) {
                    ConsolidatedQueObjects.add(o);
                }
            }
            //Done consolidating, time to do the MySQL
            for(QueObject co : ConsolidatedQueObjects) {
                if(main.debug)
                    log.info("Saving [" + co.Quantity + "] x [" + co.QueType + "] for [[UUID]" + co.Player + "]");
            }
        }
    }
}
