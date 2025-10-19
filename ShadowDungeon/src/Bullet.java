import bagel.Image;
import bagel.util.Point;
import bagel.*;


public class Bullet {

    private final double damage;
    private final double BulletSpeed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletSpeed"));
    private final double BulletFreq = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletFreq"));
    private static final Image image = new Image("res/bullet.png");
    private final Point endDirection;
    private final double unitX;
    private final double unitY;
    private final double initDistance;
    private Point position;

    public Bullet(double damage, Point position, Point direction){
        this.damage = damage;
        this.position = position;
        this.endDirection = direction;
        this.initDistance = position.distanceTo(direction);
        this.unitX = (direction.x-position.x)*(1/initDistance);
        this.unitY = (direction.y-position.y)*(1/initDistance);
    }

    public void update(Input input){
        double currX = position.x;
        double currY = position.y;
        
        currX += Math.sqrt(BulletSpeed)* unitX;
        currY += Math.sqrt(BulletSpeed)* unitY;

        move(currX,currY);
    }

    public void move(double x, double y) {
        position = new Point(x, y);
    }

    public void draw() {
        image.draw(position.x, position.y);
    }


}
