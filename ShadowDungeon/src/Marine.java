import bagel.util.Point;
import bagel.Image;

public class Marine extends Player {

    private static final Image RIGHT_IMAGE = new Image("res/marine_right.png");
    private static final Image LEFT_IMAGE = new Image("res/marine_left.png");


    public Marine(Point position) {

        super(position, RIGHT_IMAGE);
    }




}
