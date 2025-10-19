import bagel.Image;
import bagel.util.Point;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class Enemy{


    public Point position;
    public final Image image;
    public boolean active = false; // only true when the Battle Room has been activated
    public boolean dead = false;
    public double health;
    public double coinDrop;
    public double FireBallFreq;

    public Enemy(Point startPos, Image image, double health, double coinDrop) {
        this.position = startPos;
        this.image = image;
        this.health = health;
        this.coinDrop = coinDrop;
    }


    public void update(Player player) {
        if (hasCollidedWith(player)) {
            dead = true;
            active = false;
        }
    }


    public void draw() {
        image.draw(position.x, position.y);
    }


    public boolean hasCollidedWith(Player player) {
        return image.getBoundingBoxAt(position).intersects(player.getCurrImage().getBoundingBoxAt(player.getPosition()));
    }


    public boolean isDead() {
        return dead;
    }


    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }
}
