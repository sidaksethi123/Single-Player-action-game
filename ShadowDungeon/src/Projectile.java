import bagel.Image;
import bagel.util.Point;
import bagel.*;
import bagel.util.Rectangle;

public class Projectile {

    public final double damage;
    public final double speed;
    public final double unitX;
    public final double unitY;
    public Point position;
    public boolean isActive = true;
    public Image image;


    public Projectile(double damage, Point position, Point direction, Image image, double speed){
        this.damage = damage;
        this.position = position;
        double initDistance = position.distanceTo(direction);
        this.unitX = (direction.x-position.x)*(1/initDistance);
        this.unitY = (direction.y-position.y)*(1/initDistance);
        this.image = image;
        this.speed = speed;
    }


    public void update(){
        projection();
    }

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

    public void move(double x, double y) {
        position = new Point(x, y);
    }

    public void draw() {
        image.draw(position.x, position.y);
    }


}
