import bagel.Image;
import bagel.util.Point;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class BulletKin extends Enemy{

    private static final Image BKimage= new Image("res/bullet_kin.png");

    public BulletKin(Point startPos) {
        super(startPos, BKimage);
    }

}
