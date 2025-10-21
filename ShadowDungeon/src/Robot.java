import bagel.util.Point;
import bagel.Image;

/**
 * subclass of player that earns extra coins per kill
 */
public class Robot extends Player {

    private static final Image RIGHT_IMAGE = new Image("res/robot_right.png");
    private static final Image LEFT_IMAGE = new Image("res/robot_left.png");

    /**
     * create the robot player
     * @param prevPlayer
     */
    public Robot(Player prevPlayer) {
        super(RIGHT_IMAGE, LEFT_IMAGE, prevPlayer);
    }


}
