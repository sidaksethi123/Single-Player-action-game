import bagel.Image;
import bagel.util.Point;

/**
 * Obstacle that blocks the player from moving through it
 */
public class Wall extends Obstacle {


    private static final Image wallPhoto = new Image("res/wall.png");

    /**
     * create object
     * @param position coordinates of wall
     */
    public Wall(Point position) {
        super(position, wallPhoto);
    }



}