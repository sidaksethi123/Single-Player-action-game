import java.util.Map;
import java.util.Properties;
import bagel.*;

/**
 * abstract class for all rooms
 */
public abstract class Room {
    /**
     * Variables to be inherited, protected not allowed
     */
    public boolean stopCurrentUpdateCall = false;
    public boolean isComplete = false;
    public Player player;


    /**
     * initialise relevant fiels
     * @param gameProperties properties of game
     */
    public void initEntities(Properties gameProperties) {
    }

    /**
     * update objects in room
     * @param input key event presses
     */
    public void update(Input input){
    }

    /**
     * stop updating early if needed
     * @return whether required or not
     */
    public boolean stopUpdatingEarlyIfNeeded() {
        if (stopCurrentUpdateCall) {
            player = null;
            stopCurrentUpdateCall = false;
            return true;
        }
        return false;
    }

    /**
     * stop the current room update call
     */
    public void stopCurrentUpdateCall() {
        stopCurrentUpdateCall = true;
    }

    /**
     * check if complete
     * @return if complete
     */
    public boolean isComplete() {
        return isComplete;
    }

}
