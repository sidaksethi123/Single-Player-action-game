import java.util.Map;
import java.util.Properties;

public abstract class Room {
    private boolean stopCurrentUpdateCall = false;
    private boolean isComplete = false;




    public void initEntities(Properties gameProperties) {
    }

    public void update(Input input){
    }

    private boolean stopUpdatingEarlyIfNeeded() {
        if (stopCurrentUpdateCall) {
            player = null;
            stopCurrentUpdateCall = false;
            return true;
        }
        return false;
    }

    public void stopCurrentUpdateCall() {
        stopCurrentUpdateCall = true;
    }

    public boolean isComplete() {
        return isComplete;
    }

}
