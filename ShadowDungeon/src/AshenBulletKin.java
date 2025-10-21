import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Subclass of Enemy that gets removed when player shoots it and can shoot enemy
 */
public class AshenBulletKin extends Enemy implements Shootable{

    private static final Image AshenBKimage= new Image("res/ashen_bullet_kin.png");
    private int elapsedCounter = 0;

    /**
     * Constructor for Enemy type AshenBulletKin
     * @param startPos the starting point
     */
    public AshenBulletKin(Point startPos) {
        super(startPos, AshenBKimage,
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("ashenBulletKinHealth")),
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("ashenBulletKinCoin"))
        );
        FireBallFreq = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("ashenBulletKinShootFrequency"));
        fireBalls = new ArrayList<>();
    }

    /**
     * Update logic for AshenBulletKin to track and alter its own state and abilities
     * @param player the current player object that is alive
     */
    public void update(Player player) {
        if (hasCollidedWith(player)) {
            player.receiveDamage(0.2);
        }

        if (active){
            shootProjectile(player);
        }
        elapsedCounter += 1;
    }

    /**
     * Handles the shooting logic of fireballs and create fireball object
     * @param player current player state
     */
    public void shootProjectile(Player player){
        for(Fireball ball: fireBalls){
            ball.update(player);
        }
        //if enough frames have elapsed
        if (elapsedCounter%FireBallFreq == 0){
            fireBalls.add(new Fireball(position, player.getPosition()));
        }

    }

}
