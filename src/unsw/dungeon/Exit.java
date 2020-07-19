package unsw.dungeon;

public class Exit extends Entity {
    
    private boolean onExit;
    private Dungeon dungeon;

    public Exit(Dungeon dungeon, int x, int y) {
        super(x, y, true);
        onExit = false;
        this.dungeon = dungeon;
    }

    public void notifyDungeon() {
        dungeon.update(this);
    }

    public boolean playerExits(int x, int y) {
        if (getX() == x && getY() == y) { onExit = true; }
        return onExit;
    }

    public void update(Entity entity) {
        if (entity instanceof Player) {
            notifyDungeon();
        }
    }

}