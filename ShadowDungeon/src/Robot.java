import bagel.util.Point;
import bagel.Image;

public class Robot extends Player {

    private static final Image ROBOT_RIGHT = new Image("res/robot_right.png");
    private static final Image ROBOT_LEFT = new Image("res/robot_left.png");


    public Robot(Point position) {
        super(position, ROBOT_RIGHT);

    }




}
