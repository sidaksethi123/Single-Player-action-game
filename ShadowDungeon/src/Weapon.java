import bagel.Image;
import bagel.util.Point;
import bagel.*;
import java.util.ArrayList;


public class Weapon {

    private final String level;
    private final double damage;
    private static final Image image = new Image("res/bullet.png");
    private boolean bulletisactive = false;

    ArrayList<Bullet> bullets = new ArrayList<>();


    public Weapon(String Level){
        this.level = Level;
        this.damage = 30;
    }

    public void update(Input input, Player player){
        if (input.wasPressed(MouseButtons.LEFT)){
            bullets.add(new Bullet(damage, player.getPosition(), input.getMousePosition()));
        }


    }



}
