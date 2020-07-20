package unsw.dungeon;

public class Sword extends Item {

    private int hits;

    public Sword (Dungeon dungeon, int x, int y) {
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