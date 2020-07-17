package unsw.dungeon;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;
    private String recentMovement;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y, false);
        recentMovement = "";
        this.dungeon = dungeon;
    }

    public void notifyDungeon() {
        dungeon.update(this);
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Boulder) {
            updatePosition();         
        }    
    }

    public void moveUp() {
        if (getY() > 0) {
            recentMovement = "up";
            notifyDungeon();
        }   
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
            recentMovement = "down";
            notifyDungeon();
        }   
    }

    public void moveLeft() {
        if (getX() > 0) {
            recentMovement = "left";
            notifyDungeon();
        }          
    }
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1) {
            recentMovement = "right";
            notifyDungeon();
        }
    }

    public String getRecentMovement() {
        return recentMovement;
    }

    public void updatePosition() {
        if (recentMovement == "up") {
            y().set(getY() - 1);
        }
        if (recentMovement == "down") {
            y().set(getY() + 1);
        }
        if (recentMovement == "left") {
            x().set(getX() - 1);
        }
        if (recentMovement == "right") {
            x().set(getX() + 1);
        }
    }
}

