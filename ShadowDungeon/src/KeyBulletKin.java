import bagel.Image;
import bagel.util.Point;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class KeyBulletKin extends Enemy{


    private static final Image KeyBKimage= new Image("res/key_bullet_kin.png");

    public KeyBulletKin(Point startPos) {
        super(startPos, KeyBKimage);
    }

}
