package unsw.dungeon;

public class Boulder extends Entity {
    
    /**
     * Create a Boulder positioned in square(x, y)
     * @param x
     * @param y
     */
    public Boulder(int x, int y) {
        super(x, y, false);
    }

    public void resetPosition(int x, int y) {
        x().set(x);
        y().set(y);
    }
}