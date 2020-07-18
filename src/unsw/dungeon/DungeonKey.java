package unsw.dungeon;

public class DungeonKey extends Item {
    
    private Integer id;

    public DungeonKey (Dungeon dungeon, int x, int y, int id) {
        super(dungeon, x, y);
        this.id = id;
    }

    /**
     * method to get id of key
     * @return
     */
    public Integer getID() {
        return id;
    }
}