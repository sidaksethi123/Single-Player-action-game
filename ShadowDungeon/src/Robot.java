import bagel.util.Point;
import bagel.Image;

public class Robot extends Player {

    private static final Image RIGHT_IMAGE = new Image("res/robot_right.png");
    private static final Image LEFT_IMAGE = new Image("res/robot_left.png");

    public Robot(Point position) {
        super(position, RIGHT_IMAGE, LEFT_IMAGE);
    }


}
