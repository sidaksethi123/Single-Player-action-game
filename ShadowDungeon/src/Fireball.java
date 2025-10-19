import bagel.Image;
import bagel.util.Point;
import bagel.*;


public class Fireball {

    private final double damage = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("fireballDamage"));
    private final double fireballSpeed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("fireballSpeed"));
    private final double fireballFreq;
    private static final Image image = new Image("res/bullet.png");
    private final double unitX;
    private final double unitY;
    private Point position;

    public Fireball(Point position, Point direction, double frequency){
        this.position = position;
        double initDistance = position.distanceTo(direction);
        this.unitX = (direction.x-position.x)*(1/ initDistance);
        this.unitY = (direction.y-position.y)*(1/ initDistance);
        this.fireballFreq = frequency;
    }

    public void update(Input input){
        double currX = position.x;
        double currY = position.y;

        currX += Math.sqrt(fireballSpeed)* unitX;
        currY += Math.sqrt(fireballFreq)* unitY;

        move(currX,currY);
    }

    public void move(double x, double y) {
        position = new Point(x, y);
    }

    public void draw() {
        image.draw(position.x, position.y);
    }


}
