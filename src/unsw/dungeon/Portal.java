package unsw.dungeon;

public class Portal extends Entity{

    private Dungeon dungeon;
    private int id;

    public Portal(Dungeon dungeon, int x, int y, int id) {
        super(x, y, true);
        this.dungeon = dungeon;
        this.id = id;
    }

    public Portal findMatchingPortal() {
        return dungeon.matchingPortal(this);
    }

    public int getID() {
        return id;
    }

    public boolean matchPortal(Portal portal) {
        if (portal.getID() == id) {
            return (getX() != portal.getX() || getY() != portal.getY());
        }
        return false;
    }

    public void teleport(Player player) {
        Portal matchPortal = findMatchingPortal();
        if (matchPortal == null) return;
        dungeon.movePlayer(matchPortal.getX(), matchPortal.getY());
    }
    
    public void update(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            teleport(player);
        }
        return;
    }

}