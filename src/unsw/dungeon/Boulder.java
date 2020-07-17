package unsw.dungeon;

public class Boulder extends Entity {
    private Dungeon dungeon;

    /**
     * Create a Boulder positioned in square(x, y)
     * @param x
     * @param y
     */
    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y, false);
        this.dungeon = dungeon;
    }

    public void notifyDungeon() {
        dungeon.update(this);
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            pushBoulder(getX(), getY(), player);            
        }
    }

    /**
     * method to set x and y coordinates of the Boulder
     * @param x
     * @param y
     */
    public void resetPosition(int x, int y) {
        x().set(x);
        y().set(y);
    }

    /**
     * method to push boulder if the boulder will not collide with entity in adjacent square
     * @param x     old x coordinate of the Boulder
     * @param y     old y coordinate of the Boulder
     * @param player
     */
    public void pushBoulder(int x, int y, Player player) {
        String move = player.getRecentMovement();
        if (move == "left" && !dungeon.willCollide(x - 1, y)) {
            resetPosition(x - 1, y);
            notifyDungeon();
        }
        if (move == "right" && !dungeon.willCollide(x + 1, y)) {
            resetPosition(x + 1, y);
            notifyDungeon();
        }
        if (move == "up" && !dungeon.willCollide(x, y - 1)) {
            resetPosition(x, y - 1);
            notifyDungeon();
        }
        if (move == "down" && !dungeon.willCollide(x, y + 1)) {
            resetPosition(x, y + 1);
            notifyDungeon();
        }
    }
}