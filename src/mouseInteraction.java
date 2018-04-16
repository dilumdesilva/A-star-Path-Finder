
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author dilumdesilva
 */
public class mouseInteraction implements Runnable {
    
     boolean pressedOnce = false;

    //to track the mouse events such as user's mouse clicks.
    @Override
    public void run() {
        while (true) {
            if (StdDraw.mousePressed()) {
                if (!pressedOnce) {
                    pathFinder.captureMouseClick((StdDraw.mouseX()), (20 - StdDraw.mouseY()));
                    pressedOnce = true;
                }
            } else {
                pressedOnce = false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(mouseInteraction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
   
    
}
