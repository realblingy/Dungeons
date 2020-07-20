package unsw.dungeon;

public class Item extends Entity {
    
    private Dungeon dungeon;

    public Item (Dungeon dungeon, int x, int y) {
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
            player.updatePosition();
            
            if (player.canPickUp(this)) {
                // for potion
                if (getClass() == Potion.class) {
                    Potion potion = ( Potion ) this;
                   potion.updatePlayer(player);
                }
                // for key, sword and treasure
                addToInventory(player);
                dungeon.removeEntity(entity);
            }
            
        }
    }

    public void addToInventory(Player player) {
        Dungeon dungeon = getDungeon();
        player.addItem(this);
        dungeon.removeEntity(this);
    }

}