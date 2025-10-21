import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class BulletKin extends Enemy implements Shootable{

    private static final Image BKimage= new Image("res/bullet_kin.png");

    private int elapsedCounter = 0;

    public BulletKin(Point startPos) {
        super(startPos, BKimage,
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletKinHealth")),
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletKinCoin"))
        );
        FireBallFreq = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletKinShootFrequency"));
        fireBalls = new ArrayList<>();


    }

    public void update(Player player) {
        if (hasCollidedWith(player)) {
            player.receiveDamage(0.2);
        }

        if (active) {
            shootProjectile(player);
            elapsedCounter += 1;
        }
    }

    public void shootProjectile(Player player){
        for(Fireball ball: fireBalls){
            ball.update(player);
        }
        if (elapsedCounter%FireBallFreq == 0){
            fireBalls.add(new Fireball(position, ShadowDungeon.playerPos()));
        }

    }
}
