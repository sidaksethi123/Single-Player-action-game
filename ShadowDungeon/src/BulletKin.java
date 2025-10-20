import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Enemy that gets removed when the player overlaps with it
 */
public class BulletKin extends Enemy{

    private static final Image BKimage= new Image("res/bullet_kin.png");

    public ArrayList<Fireball> fireBalls;
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
            dead = true;
            active = false;
        }

        if (active){
            for(Fireball ball: fireBalls){
                ball.update();
            }
            if (elapsedCounter%FireBallFreq == 0){
                fireBalls.add(new Fireball(position, ShadowDungeon.playerPos()));
            }


        }
        elapsedCounter += 1;

    }

}
