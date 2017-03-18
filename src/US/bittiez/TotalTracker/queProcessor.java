package US.bittiez.TotalTracker;

import java.util.logging.Logger;
import java.util.ArrayList;

public class queProcessor implements Runnable {
    public ArrayList<queObject> queObjects;
    public Logger log;

    @Override
    public void run() {
        //Is there a que? Does it have anything in it?
        if(queObjects != null && queObjects.size() > 0){
            if(main.debug)
                log.info("Trying to process a que of " + queObjects.size() + " queObjects.");
            
        }
    }
}
