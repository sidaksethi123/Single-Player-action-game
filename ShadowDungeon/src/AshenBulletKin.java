import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class AshenBulletKin extends Enemy{

    private static final Image AshenBKimage= new Image("res/ashen_bullet_kin.png");
    private int elapsedCounter = 0;


    public AshenBulletKin(Point startPos) {
        super(startPos, AshenBKimage,
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("ashenBulletKinHealth")),
                Double.parseDouble(ShadowDungeon.getGameProps().getProperty("ashenBulletKinCoin"))
        );
        FireBallFreq = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("ashenBulletKinShootFrequency"));
        fireBalls = new ArrayList<>();
    }

    public void update(Player player) {
        if (hasCollidedWith(player)) {
            dead = true;
            active = false;
        }

        if (active){
            for(Fireball ball: fireBalls){
                ball.update(player);
            }
            if (elapsedCounter%FireBallFreq == 0){
                fireBalls.add(new Fireball(position, ShadowDungeon.playerPos()));
            }


        }
        elapsedCounter += 1;

    }

}
