import bagel.Image;
import bagel.util.Point;
import bagel.*;
import java.util.ArrayList;

/**
 * weapon held by player that can shoot
 */
public class Weapon implements Shootable {

    private final String level;
    private final double damage;
    private static final Image image = new Image("res/bullet.png");
    private double elapsedCounter = 0;
    private final double BulletFreq = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletFreq"));
    private Point currmousePos;
    ArrayList<Bullet> bullets = new ArrayList<>();

    /**
     * create object
     * @param Level level of weapon
     */
    public Weapon(String Level){
        this.level = Level;
        this.damage = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("weapon"+Level+"Damage"));
    }

    /**
     * update whether bullet has been shot or not
     * @param input key event press
     * @param player current player
     */
    public void update(Input input, Player player){
        if (input.wasPressed(MouseButtons.LEFT) && (elapsedCounter>BulletFreq)){
            currmousePos = input.getMousePosition();
            shootProjectile(player);
        }

        for(Bullet bullet: bullets){
            bullet.update();
        }

        elapsedCounter += 1;
    }

    /**
     * damage weapon can do
     * @return damage amount
     */
    public double getDamage(){
        return damage;
    }

    /**
     * shoot the bullet
     * @param player current player
     */
    public void shootProjectile(Player player){
        bullets.add(new Bullet(damage, player.getPosition(), currmousePos));
        elapsedCounter = 0;
    }

}
