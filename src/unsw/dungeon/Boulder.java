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

    public void resetPosition(int x, int y) {
        x().set(x);
        y().set(y);
    }

    public void pushBoulder(int x, int y, Player player) {
        String move = player.getRecentMovement();
        if (move == "left" && !player.willCollide(x - 1, y)) {
            resetPosition(x - 1, y);
            notifyDungeon();
        }
        if (move == "right" && !player.willCollide(x + 1, y)) {
            resetPosition(x + 1, y);
            notifyDungeon();
        }
        if (move == "up" && !player.willCollide(x, y - 1)) {
            resetPosition(x, y - 1);
            notifyDungeon();
        }
        if (move == "down" && !player.willCollide(x, y + 1)) {
            resetPosition(x, y + 1);
            notifyDungeon();
        }
    }
}