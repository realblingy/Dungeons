package unsw.dungeon;

public class Item extends Entity {
    
    private Dungeon dungeon;

    public Item (Dungeon dungeon, int x, int y) {
        super(x, y, true);
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void notifyDungeon() {
        dungeon.update(this);
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.updatePosition();
            
            if (player.canPickUp(this)) {
                // for potion
                //if (getClass() == Potion.class) {
                //    notifyDungeon();
                //}

                // for key, sword and treasure
                addToInventory(player);
            }
        }
    }

    public void addToInventory(Player player) {
        Dungeon dungeon = getDungeon();
        player.addItem(this);
        dungeon.removeEntity(this);
    }

}