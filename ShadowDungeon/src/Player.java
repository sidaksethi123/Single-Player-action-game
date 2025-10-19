import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Player character that can move around and between rooms, defeat enemies, collect coins
 */
public class Player {
    public Point prevPosition;
    public Point position;
    public Image currImage;
    public double health;
    public double speed;
    public double coins = 0;
    public boolean faceLeft = false;
    public Image rightImage;
    public Image leftImage;
    private Weapon gun;


    public Player(Point position, Image rightImage, Image leftImage) {
        this.position = position;
        this.currImage = rightImage;
        this.rightImage = rightImage;
        this.leftImage = leftImage;
        this.speed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("movingSpeed"));
        this.health = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("initialHealth"));
        this.gun = new Weapon("Standard");
    }

    public Player(Point position) {
        this.position = position;
        this.rightImage = new Image("res/player_right.png");
        this.leftImage = new Image("res/player_left.png");
        this.currImage = this.rightImage;
        this.speed = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("movingSpeed"));
        this.health = Double.parseDouble(ShadowDungeon.getGameProps().getProperty("initialHealth"));
        this.gun = null;
    }


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
    
    public void move(double x, double y) {
        prevPosition = position;
        position = new Point(x, y);
    }

    public void draw() {
        currImage = faceLeft ? leftImage : rightImage; // NOTE: this is an example of using the ternary operator
        currImage.draw(position.x, position.y);
        UserInterface.drawStats(health, coins);
    }

    public void earnCoins(double coins) {
        this.coins += coins;
    }

    public void receiveDamage(double damage) {
        health -= damage;
        if (health <= 0) {
            ShadowDungeon.changeToGameOverRoom();
        }
    }

    public Point getPosition() {
        return position;
    }

    public Image getCurrImage() {
        return currImage;
    }

    public Point getPrevPosition() {
        return prevPosition;
    }
}
