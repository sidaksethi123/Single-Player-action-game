import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

/**
 * Area in battleroom where the player can buy health or weapons
 */
public class Store {
    private final Point position;
    private final Image image;

    /**
     * create store
     */
    public Store() {
        this.position = new Point(ShadowDungeon.screenWidth/2,ShadowDungeon.screenHeight/2);
        this.image = new Image("res/store.png");
    }

    /**
     * update logic on whether something has been purchased
     * @param input key event press
     * @param player current player
     * @param Curr current battlroom
     */
    public void update(Input input, Player player, BattleRoom Curr) {
        if (input.wasPressed(Keys.P)) {
            ShadowDungeon.resetGameState(ShadowDungeon.getGameProps());
        }
        if (input.wasPressed(Keys.E)) {
            if (player.coins >= 50) {
                player.coins -= 50;
                player.health += 50;
            }
        }
        if (input.wasPressed(Keys.L) && player.coins >= 50) {
            if (player.level == 0) {
                player.gun = new Weapon("Advance");
                player.level = 1;
                player.coins -= 50;
            } else if (player.level == 1) {
                player.gun = new Weapon("Elite");
                player.level = 2;
                player.coins -= 50;
            }
        }

        if (input.wasPressed(Keys.SPACE)) {
            Curr.storeflag = 0;
        }
    }

    /**
     * draw the store
     */
    public void draw() {
        image.draw(position.x, position.y);
    }

    }

