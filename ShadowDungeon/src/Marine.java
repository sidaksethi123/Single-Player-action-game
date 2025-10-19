import bagel.util.Point;
import bagel.Image;

public class Marine extends Player {

    private static final Image MARINE_RIGHT = new Image("res/marine_right.png");
    private static final Image MARINE_LEFT = new Image("res/marine_left.png");


    public Marine(Point position) {
        super(position, MARINE_RIGHT);

    }




}
