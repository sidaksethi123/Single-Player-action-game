import bagel.Image;
import bagel.util.Point;
import bagel.*;


public class Bullet extends Projectile {


    private static final Image bulletImage = new Image("res/bullet.png");

    public Bullet(double damage, Point position, Point direction){
        super(damage,position,direction,bulletImage,
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletSpeed")));
        double initDistance = position.distanceTo(direction);
    }





}
