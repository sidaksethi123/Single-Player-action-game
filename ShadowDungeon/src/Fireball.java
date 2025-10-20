import bagel.Image;
import bagel.util.Point;
import bagel.*;


public class Fireball extends Projectile{

    private final double damage = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("fireballDamage"));
    private final double fireballSpeed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("fireballSpeed"));
    private static final Image fireImage = new Image("res/fireball.png");

    public Fireball(Point position, Point direction){
        super(Double.parseDouble(ShadowDungeon.getGameProps().getProperty("fireballDamage")), position, direction,
        fireImage);
        double initDistance = position.distanceTo(direction);
    }

    public void update(){
        if (playercollision()){
            if (isActive) {
                ShadowDungeon.player.receiveDamage(40);
                isActive = false;
            }
        }
        projection();

    }

    public boolean playercollision(){
        return fireImage.getBoundingBoxAt(position).intersects
                (ShadowDungeon.player.currImage.getBoundingBoxAt(ShadowDungeon.player.position));
    }


}
