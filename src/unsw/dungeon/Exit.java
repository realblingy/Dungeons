package unsw.dungeon;

public class Exit extends Entity {
    
    private Dungeon dungeon;

    public Exit(Dungeon dungeon, int x, int y) {
        super(x, y, true);
        onExit = false;
        this.dungeon = dungeon;
    }

    public void notifyDungeon() {
        dungeon.update(this);
    }

    public void update(Entity entity) {
        if (entity instanceof Player) {
            notifyDungeon();
        }
    }

}