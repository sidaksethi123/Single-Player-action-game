import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class Enemy implements Collidable, Damageable{
    /**
     * these attributes are public because specifications dont allow protected, and
     * it was felt necessary for childrent to inherit these attributes
     */

    public Point position;
    public final Image image;
    public boolean active = false; // only true when the Battle Room has been activated
    public boolean dead = false;
    public double health;
    public double coinDrop;
    public double FireBallFreq;
    public ArrayList<Fireball> fireBalls;

    private static final Image bulletImage = new Image("res/bullet.png");

    /**
     * create enemy object
     * @param startPos start position
     * @param image sprite image
     * @param health starting health
     * @param coinDrop coins dropped when killed
     */
    public Enemy(Point startPos, Image image, double health, double coinDrop) {
        this.position = startPos;
        this.image = image;
        this.health = health;
        this.coinDrop = coinDrop;
    }

    /**
     * update state of the enemy whether alive or dead and the consequences
     * @param player current player status
     */
    public void update(Player player) {
        if (hasCollidedWith(player)) {
            player.receiveDamage(0.2);
        }
    }

    /**
     * draw the object
     */
    public void draw() {
        image.draw(position.x, position.y);
    }

    /**
     * check if collided with player
     * @param player current player
     * @return whether collision or not
     */
    public boolean hasCollidedWith(Player player) {
        return image.getBoundingBoxAt(position).intersects(player.getCurrImage().getBoundingBoxAt(player.getPosition()));
    }

    /**
     * check if player has shot enemy
     * @param projectile is the bullet in question
     * @return whether bullet collided or not
     */
    public boolean bulletcollision(Projectile projectile) {
        return image.getBoundingBoxAt(position).intersects
                (bulletImage.getBoundingBoxAt(projectile.position));
    }

    /**
     * when enemy is hit
     * @param damage amount of damage to enemy
     */
    public void receiveDamage(double damage){
        this.health -= damage;
    }

    /**
     * health status
     * @return health status
     */
    public double getHealth() {
        return health;
    }

    /**
     * check if dead
     * @return whether dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * check if active
     * @return whether active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Update whether active
     * @param active current status
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
