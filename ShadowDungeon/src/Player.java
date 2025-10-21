import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Player character that can move around and between rooms, defeat enemies, collect coins
 */
public class Player implements Damageable, Movable {
    /**
     * protected variables not allowed and it felt necessary for these to be inherited thus public
     */
    public Point prevPosition;
    public Point position;
    public Image currImage;
    public double health;
    public double speed;
    public double coins = 0;
    public boolean faceLeft = false;
    public Image rightImage;
    public Image leftImage;
    public Weapon gun;
    public int level = 0;
    public double keyCount = 0;
    public static String[] weaponType = {"Standard","Advance", "Elite"};

    /**
     * create player
     * @param rightImage facing right
     * @param leftImage facing left
     * @param tempplayer previous state
     */
    public Player(Image rightImage, Image leftImage, Player tempplayer) {
        this.position = tempplayer.position;
        this.currImage = rightImage;
        this.rightImage = rightImage;
        this.leftImage = leftImage;
        this.speed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("movingSpeed"));
        this.health = tempplayer.health;
        if (tempplayer.gun == null){
            this.level = 0;
            this.gun = new Weapon(weaponType[0]);

        }
        else{
            this.level = tempplayer.level;
            this.gun = new Weapon(weaponType[this.level]);
        }
        this.keyCount = tempplayer.keyCount;
        this.coins = tempplayer.coins;
    }

    /**
     * constructor for default character in preproom
     * @param position position of spawn
     */
    public Player(Point position) {
        this.position = position;
        this.rightImage = new Image("res/player_right.png");
        this.leftImage = new Image("res/player_left.png");
        this.currImage = this.rightImage;
        this.speed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("movingSpeed"));
        this.health = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("initialHealth"));
        this.gun = null;
    }

    /**
     * update state and consequences
     * @param input key event presses
     */
    public void update(Input input) {
        // check movement keys and mouse cursor
        double currX = position.x;
        double currY = position.y;

        if (input.isDown(Keys.A)) {
            currX -= speed;
        }
        if (input.isDown(Keys.D)) {
            currX += speed;
        }
        if (input.isDown(Keys.W)) {
            currY -= speed;
        }
        if (input.isDown(Keys.S)) {
            currY += speed;
        }

        faceLeft = input.getMouseX() < currX;

        // update the player position accordingly and ensure it can't move past the game window
        Rectangle rect = currImage.getBoundingBoxAt(new Point(currX, currY));
        Point topLeft = rect.topLeft();
        Point bottomRight = rect.bottomRight();
        if (topLeft.x >= 0 && bottomRight.x <= Window.getWidth() && topLeft.y >= 0 && bottomRight.y <= Window.getHeight()) {
            move(currX, currY);
        }
        if(this.gun != null) {
            gun.update(input, this);
        }
    }

    /**
     * move player
     * @param x x position
     * @param y y positoon
     */
    public void move(double x, double y) {
        prevPosition = position;
        position = new Point(x, y);
    }

    /**
     * draw object
     */
    public void draw() {
        currImage = faceLeft ? leftImage : rightImage; // NOTE: this is an example of using the ternary operator
        currImage.draw(position.x, position.y);
        UserInterface.drawStats(health, coins, keyCount, level);
    }

    /**
     * earn coins
     * @param coins amount earned
     */
    public void earnCoins(double coins) {
        this.coins += coins;
    }

    /**
     * take damage
     * @param damage amount of damage
     */
    public void receiveDamage(double damage) {
        health -= damage;
        if (health <= 0) {
            ShadowDungeon.changeToGameOverRoom();
        }
    }

    /**
     * get health
     * @return health
     */
    public double getHealth() {
        return health;
    }

    /**
     * get position
     * @return position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * get current image
     * @return current image
     */
    public Image getCurrImage() {
        return currImage;
    }

    /**
     * get previous position
     * @return previous position
     */
    public Point getPrevPosition() {
        return prevPosition;
    }
}
