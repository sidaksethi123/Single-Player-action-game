import bagel.Image;
import bagel.util.Point;
import bagel.*;
import bagel.util.Rectangle;

public class Projectile {

    public final double damage;
    public final double Speed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletSpeed"));
    public final double unitX;
    public final double unitY;
    public Point position;
    private boolean isActive = true;
    public Image image;


    public Projectile(double damage, Point position, Point direction, Image image){
        this.damage = damage;
        this.position = position;
        double initDistance = position.distanceTo(direction);
        this.unitX = (direction.x-position.x)*(1/initDistance);
        this.unitY = (direction.y-position.y)*(1/initDistance);
        this.image = image;
    }

    public void update(){
        double currX = position.x;
        double currY = position.y;

        currX += Math.sqrt(Speed)* unitX;
        currY += Math.sqrt(Speed)* unitY;


        if (isActive){
            move(currX,currY);
            draw();
        }
    }

    public void move(double x, double y) {
        position = new Point(x, y);
    }

    public void draw() {
        image.draw(position.x, position.y);
    }


}
