import bagel.Image;
import bagel.util.Point;
import bagel.*;
import bagel.util.Rectangle;

/**
 * projectiles that can be shot by enemies and player
 */
public class Projectile implements Movable{
    /**
     * Not allowed to have protected fields and felt it necessary for these attributes to be inherited
     */
    public final double damage;
    public final double speed;
    public final double unitX;
    public final double unitY;
    public Point position;
    public boolean isActive = true;
    public Image image;

    /**
     * create projectile
     * @param damage damage to do
     * @param position current positon
     * @param direction position to go
     * @param image image of projectile
     * @param speed speed of projectile
     */
    public Projectile(double damage, Point position, Point direction, Image image, double speed){
        this.damage = damage;
        this.position = position;
        double initDistance = position.distanceTo(direction);
        this.unitX = (direction.x-position.x)*(1/initDistance);
        this.unitY = (direction.y-position.y)*(1/initDistance);
        this.image = image;
        this.speed = speed;
    }

    /**
     * update the travel and collision logic
     */
    public void update(){
        projection();
    }

    /**
     * travel logic of projectile
     */
    public void projection(){
        double currX = position.x;
        double currY = position.y;

        currX += Math.sqrt(speed)* unitX;
        currY += Math.sqrt(speed)* unitY;



        if (isActive){
            move(currX,currY);
            draw();
        }
    }

    /**
     * change position
     * @param x x position
     * @param y y position
     */
    public void move(double x, double y) {
        position = new Point(x, y);
    }

    /**
     * draw the object as its new position
     */
    public void draw() {
        image.draw(position.x, position.y);
    }


}
