import bagel.Image;
import bagel.util.Point;
import bagel.*;
import java.util.ArrayList;


public class Weapon {

    private final String level;
    private final double damage;
    private static final Image image = new Image("res/bullet.png");
    private double elapsedCounter = 0;
    private final double BulletFreq = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("bulletFreq"));

    ArrayList<Bullet> bullets = new ArrayList<>();


    public Weapon(String Level){
        this.level = Level;
        this.damage = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("weapon"+Level+"Damage"));
    }

    public void update(Input input, Player player){
        if (input.wasPressed(MouseButtons.LEFT) && (elapsedCounter>BulletFreq)){
            bullets.add(new Bullet(damage, player.getPosition(), input.getMousePosition()));
            elapsedCounter = 0;
        }

        for(Bullet bullet: bullets){
            bullet.update();
        }

        elapsedCounter += 1;
    }

    public double getDamage(){
        return damage;
    }



}
