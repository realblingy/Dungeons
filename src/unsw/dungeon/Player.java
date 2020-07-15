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

    public void moveUp() {
        if (getY() > 0) {
            // if (!willCollide(getX(), getY()-1)) {
            //     y().set(getY() - 1);
            // }
            // else {
            //     dungeon.pushBoulder(getX(), getY()-1, "up");
            // }
            recentMovement = "up";
            if (!willCollide(getX(), getY()-1)) {
                y().set(getY() - 1);
            }
            notifyDungeon();
        }   
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1) {
            if (!willCollide(getX(), getY()+1)) {
                y().set(getY() + 1);
            } 
            else {
                dungeon.pushBoulder(getX(), getY()+1, "down");
            }
        }   
    }

    public void moveLeft() {
        if (getX() > 0) {
            if (!willCollide(getX() - 1, getY())) {
                x().set(getX() - 1);
            }
            else {
                dungeon.pushBoulder(getX() - 1, getY(), "left");
            }
        }                
    }
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1) {
            if (!willCollide(getX() + 1, getY())) {
                x().set(getX() + 1);
            }
            else {
                dungeon.pushBoulder(getX() + 1, getY(), "right");
            }
        }
    }

    public String getRecentMovement() {
        return recentMovement;
    }

    public boolean willCollide(int x, int y) {
        return dungeon.willCollide(x, y);
    }

}
