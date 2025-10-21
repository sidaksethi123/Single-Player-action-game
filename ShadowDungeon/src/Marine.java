import bagel.util.Point;
import bagel.Image;

/**
 * Subclass of player that can walk on rivers
 */
public class Marine extends Player {

    private static final Image RIGHT_IMAGE = new Image("res/marine_right.png");
    private static final Image LEFT_IMAGE = new Image("res/marine_left.png");

    /**
     * create object
     * @param prevPlayer previous state
     */
    public Marine(Player prevPlayer) {
        super(RIGHT_IMAGE, LEFT_IMAGE, prevPlayer);
    }




}
