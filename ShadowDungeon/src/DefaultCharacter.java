import bagel.util.Point;
import bagel.Image;

public class DefaultCharacter extends Player {

    private static final Image RIGHT_IMAGE = new Image("res/player_right.png");
    private static final Image LEFT_IMAGE = new Image("res/player_left.png");


    public DefaultCharacter(Point position) {
        super(position, RIGHT_IMAGE);


    }




}
