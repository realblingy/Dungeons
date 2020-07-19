package unsw.dungeon;

public class Enemy extends Entity {
    private Dungeon dungeon;
    private String move;

    public Enemy (Dungeon dungeon, int x, int y) {
        super(x, y, false);
        this.dungeon = dungeon;
        this.move = "";
    }

    public void notifyDungeon() {
        dungeon.update(this);
    }

    // this update will be called only when enemy is on the square that the  player wants to move
    @Override
    public void update(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.isInvincible()) {
                // enemy dies (player invincible)
                return;
            }
            else {
                if (player.reduceSwordhits()) {
                    // player is holding sword (enemy dies)
                    return;
                }
            }
        }
    }

    // this function will be called only after player has made a move
    public void updatePosition(Player player) {

        if (player.isInvincible()) {
            MoveAwayFromPlayer(player);
        }
        else {
            MoveTowardPlayer(player);
            resetPosition();
        }
    }

    public void MoveTowardPlayer(Player player) {
        // player on top
        if (player.getY() < getY()) {
            // move enemy up
            if (!dungeon.willCollide(getX(), getY() - 1)) {
                move = "up";
            }
            else {
                checkLeftRight(player);
            }
        }
        if (player.getY() > getY()) {
            // move enemy down
            if (!dungeon.willCollide(getX(), getY() + 1)) {
                move = "down";
            }
            else {
                checkLeftRight(player);
            }
        }
        if (player.getX() < getX()) {
            // move enemy left
            if (!dungeon.willCollide(getX() - 1, getY())) {
                move = "left";
            }
            else {
                checkUpDown(player);
            }
        }
        if (player.getX() > getX()) {
            // move enemy right
            if (!dungeon.willCollide(getX() + 1, getY())) {
                move = "right";
            }
            else {
                checkUpDown(player);
            }
        }
    

    }

    public void MoveAwayFromPlayer(Player player) {
        // player on left 
        if (player.getX() < getX()) {
            if (!dungeon.willCollide(getX() + 1, getY())) {
                // check right
                move = "right";
            }
            else {
                checkUpDown(player);
            }
        }
        // player on right
        else if (player.getX() > getX()) {
            if (!dungeon.willCollide(getX() - 1, getY())) {
                // check left
                move = "left";
            }
            else {
                checkUpDown(player);
            }
        }
        // same x coordinate 
        else {
            if (player.getY() < getY()) {
                if (!dungeon.willCollide(getX(), getY() + 1)) {
                    move = "Down";
                }
                else {
                    checkLeftRight(player);
                }
            }
            if (player.getY() > getY()) {
                if (!dungeon.willCollide(getX(), getY() - 1)) {
                    move = "up";
                }
                else {
                    checkLeftRight(player);
                }
            }
        }
    }

    public void checkLeftRight(Player player) {
        if (!dungeon.willCollide(getX() + 1, getY())) {
            move = "right";
        }
        else {
            move = "left";
        }
    }

    public void checkUpDown(Player player) {
        if (player.getY() < getY()) {
            if (!dungeon.willCollide(getX(), getY() + 1)) {
                // Move Down
                move = "down";
            }
            else {
                // Move Up
                move = "up";
            }
        }
        else {
            if (!dungeon.willCollide(getX(), getY() - 1)) {
                // Move up
                move = "up";
            }
            else {
                // Move Down
                move = "down";
            }
        }
    }

    public boolean collideWithPlayer(Player player, int x, int y) {
        return player.getX() == x && player.getY() == y;
    }

    public void resetPosition() {
        if (move == "up") {
            y().set(getY() - 1);
        }
        if (move == "down") {
            y().set(getY() + 1);
        }
        if (move == "left") {
            x().set(getX() - 1);
        }
        if (move == "right") {
            x().set(getX() + 1);
        }
    }
}