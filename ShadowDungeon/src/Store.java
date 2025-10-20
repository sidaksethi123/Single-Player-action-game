import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

/**
 * Area in Prep or End Room where the player can trigger a game reset
 */
public class Store {
    private final Point position;
    private final Image image;

    public Store() {
        this.position = new Point(ShadowDungeon.screenWidth/2,ShadowDungeon.screenHeight/2);
        this.image = new Image("res/store.png");
    }

    public void update(Input input, Player player, BattleRoom Curr) {
        if (input.wasPressed(Keys.P)) {
            ShadowDungeon.resetGameState(ShadowDungeon.getGameProps());
        }
        if (input.wasPressed(Keys.E)) {
            ShadowDungeon.player.coins -= 50;
            ShadowDungeon.player.health += 50;
        }
        if (input.wasPressed(Keys.L)) {
            if (player.level == 0) {
                player.gun = new Weapon("Advance");
                player.level = 1;
            } else if (player.level == 1) {
                player.gun = new Weapon("Elite");
                player.level = 2;
            }
        }

        if (input.wasPressed(Keys.SPACE)) {
            Curr.storeflag = 0;
        }
    }


    public void draw() {
        image.draw(position.x, position.y);
    }

    }

