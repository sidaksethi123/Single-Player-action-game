import bagel.Image;
import bagel.util.Point;

/**
 * Obstacles in the battlerooms
 */
public abstract class Obstacle implements Collidable {
    /**
     * Protected variables not allowed and it felt necessary for these attributes to be inherited
     */
    public final Point position;
    public Image image;
    public boolean Active = true;
    private static final Image fireImage = new Image("res/fireball.png");
    private static final Image bulletImage = new Image("res/bullet.png");

    /**
     * create obstacle
     * @param position position
     * @param image display
     */
    public Obstacle(Point position, Image image) {
        this.position = position;
        this.image = image;

    }

    /**
     * update status and interactions with object
     * @param player current player
     */
    public void update(Player player) {
        if (hasCollidedWith(player)) {
            // set the player to its position prior to attempting to move through this wall
            player.move(player.getPrevPosition().x, player.getPrevPosition().y);
        }

    }

    /**
     * whether player has collided
     * @param player current player
     * @return whether collision occured
     */
    public boolean hasCollidedWith(Player player) {
        return image.getBoundingBoxAt(position).intersects(player.getCurrImage().getBoundingBoxAt(player.getPosition()));
    }

    /**
     * draw object on screen
     */
    public void draw() {
        image.draw(position.x, position.y);
    }

    /**
     * whether projectile has collieded
     * @param projectile fireball or bullet
     * @return whether collision occured
     */
    public boolean projectilecollision(Projectile projectile){
        Image projectileImg;
        // checking which image to use based on projectile type
        if (projectile instanceof Bullet){
            projectileImg = bulletImage;
        }
        else{
            projectileImg = fireImage;
        }
        return image.getBoundingBoxAt(position).intersects
                (projectileImg.getBoundingBoxAt(projectile.position));
    }
}
