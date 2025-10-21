import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Enemy that gets removed when shot at, and can shoot player
 */
public class BulletKin extends Enemy implements Shootable{

    private static final Image BKimage= new Image("res/bullet_kin.png");

    private int elapsedCounter = 0;

    /**
     * create Bulletkin object
     * @param startPos starting position
     */
    public BulletKin(Point startPos) {
        super(startPos, BKimage,
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletKinHealth")),
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletKinCoin"))
        );
        FireBallFreq = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletKinShootFrequency"));
        fireBalls = new ArrayList<>();


    }

    /**
     * update the state of the enemy and its fireballs
     * @param player current player state
     */
    public void update(Player player) {
        if (hasCollidedWith(player)) {
            player.receiveDamage(0.2);
        }

        if (active) {
            shootProjectile(player);
            elapsedCounter += 1;
        }
    }

    /**
     * create fireball object when shot
     * @param player current player status
     */
    public void shootProjectile(Player player){
        for(Fireball ball: fireBalls){
            ball.update(player);
        }
        //check if enough frames have passed
        if (elapsedCounter%FireBallFreq == 0){
            fireBalls.add(new Fireball(position, player.getPosition()));
        }

    }
}
