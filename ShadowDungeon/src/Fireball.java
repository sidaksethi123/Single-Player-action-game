import bagel.Image;
import bagel.util.Point;
import bagel.*;


public class Fireball extends Projectile{

    private static final Image fireImage = new Image("res/fireball.png");

    public Fireball(Point position, Point direction){
        super(Double.parseDouble(ShadowDungeon.getGameProps().getProperty("fireballDamage")), position, direction,
        fireImage, Double.parseDouble(ShadowDungeon.getGameProps().getProperty("fireballSpeed")));
        double initDistance = position.distanceTo(direction);
    }

    public void update(Player player){
        if (playercollision(player)){
            if (isActive) {
                player.receiveDamage(40);
                isActive = false;
            }
        }
        projection();

    }

    public boolean playercollision(Player player){
        return fireImage.getBoundingBoxAt(position).intersects
                (player.currImage.getBoundingBoxAt(player.position));
    }


}
