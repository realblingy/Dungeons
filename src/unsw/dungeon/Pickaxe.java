package unsw.dungeon;

public class Pickaxe extends Item {
    
    private int hits;

    public Pickaxe (Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.hits = 5;
    }

    public void reduceHits() {
        this.hits = hits - 1;
    }

    public Integer getHits() {
        return hits;
    }
}