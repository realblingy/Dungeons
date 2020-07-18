package unsw.dungeon;

public class Potion extends Item {

    public void notifyDungeon() {
        super.getDungeon().update(this);
    }

    public Potion(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
    }   

    public void updatePlayer(Player player) {
        player.activateInvincibility();
    }
    
}