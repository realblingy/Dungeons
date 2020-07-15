package unsw.dungeon;

import java.util.List;

public class Boulder extends Entity {
    
    /**
     * Create a Boulder positioned in square(x, y)
     * @param x
     * @param y
     */
    public Boulder(int x, int y) {
        super(x, y, false);
    }

    public void update(Entity entity, List<Entity> entities) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            pushBoulder(getX(), getY(), player.getRecentMovement());
        }
    }

    public void resetPosition(int x, int y) {
        x().set(x);
        y().set(y);
    }

    public void pushBoulder(int x, int y, String move) {
        // if (isBoulder(x, y) != -1) {
        //     int index = isBoulder(x, y);
            // if (index != -1) {
                // Boulder b = (Boulder) entities.get(index);
        if (move == "left" && !willCollide(x - 1, y)) {
            resetPosition(x - 1, y);
            // player.x().set(x);
        }
        if (move == "right" && !willCollide(x + 1, y)) {
            resetPosition(x + 1, y);
            // player.x().set(x);
        }
        if (move == "up" && !willCollide(x, y - 1)) {
            resetPosition(x, y - 1);
            // player.y().set(y);
        }
        if (move == "down" && !willCollide(x, y + 1)) {
            resetPosition(x, y + 1);
            // player.y().set(y);
        }
    }
        // }
    // }
}