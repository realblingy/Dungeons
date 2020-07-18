package unsw.dungeon;

public class Door extends Entity {
    
    private Dungeon dungeon;
    private int id;

    public Door (Dungeon dungeon, int x, int y, int id) {
        super(x, y, false);
        this.dungeon = dungeon;
        this.id = id;
    }

    public void notifyDungeon() {
        dungeon.update(this);
    }

    @Override
    public void update(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            openDoor(player);
        }
    }

    /**
     * method to open the door if the player hold the key with same id 
     * if the door is already open then just tell the player to move
     * @param player
     */
    public void openDoor(Player player) {
        if (isCollidable() == true) {
            notifyDungeon();
        }
        Item item = player.findKey(id);
        if (item != null) {
            player.removeItem(item);
            resetCollidable(true);
            notifyDungeon();
        }
    }

    /**
     * method to get the id of door
     * @return
     */
    public Integer getID() {
        return id;
    }

}