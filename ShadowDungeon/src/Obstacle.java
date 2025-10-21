import bagel.Image;
import bagel.util.Point;


public class Obstacle implements Collidable {

    public final Point position;
    public Image image;
    public boolean Active = true;
    private static final Image fireImage = new Image("res/fireball.png");
    private static final Image bulletImage = new Image("res/bullet.png");


    public Obstacle(Point position, Image image) {
        this.position = position;
        this.image = image;

    }


    public void update(Player player) {
        if (hasCollidedWith(player)) {
            // set the player to its position prior to attempting to move through this wall
            player.move(player.getPrevPosition().x, player.getPrevPosition().y);
        }

    }

    public boolean hasCollidedWith(Player player) {
        return image.getBoundingBoxAt(position).intersects(player.getCurrImage().getBoundingBoxAt(player.getPosition()));
    }

    public void draw() {
        image.draw(position.x, position.y);
    }


    public boolean projectilecollision(Projectile projectile){
        Image projectileImg;
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
