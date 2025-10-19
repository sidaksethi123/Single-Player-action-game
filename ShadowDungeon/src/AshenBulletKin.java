import bagel.Image;
import bagel.util.Point;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class AshenBulletKin extends Enemy{

    private static final Image AshenBKimage= new Image("res/ashen_bullet_kin.png");

    public AshenBulletKin(Point startPos) {
        super(startPos, AshenBKimage,
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("ashenBulletKinHealth")),
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("ashenBulletKinCoin"))
        );
    }

}
