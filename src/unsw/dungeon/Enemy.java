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
                System.out.println("Player is invincible");
                notifyDungeon();
            }
            else {
                if (player.reduceSwordhits()) {
                    // player is holding sword (enemy dies)
                    System.out.println("Player holds sword");
                    notifyDungeon();
                }
                else {
                    System.out.println("        Player death");
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
        }
    }

    public void MoveTowardPlayer(Player player) {
        if (collideWithPlayer(player)) {
            move = "";
            //notifyDungeon();
            update(player);
            return;
        }
        // player on top
        if (player.getY() < getY()) {
            // move enemy up
            if (!dungeon.willCollide(getX(), getY() - 1)) {
                move = "up";
            }
            else {
                checkLeftRight(player);
            }
            resetMove();
            return;
        }
        if (player.getY() > getY()) {
            // move enemy down
            if (!dungeon.willCollide(getX(), getY() + 1)) {
                move = "down";
            }
            else {
                checkLeftRight(player);
            }
            resetMove();
            return;
        }
        if (player.getX() < getX()) {
            // move enemy left
            if (!dungeon.willCollide(getX() - 1, getY())) {
                move = "left";
            }
            else {
                checkUpDown(player);
            }
            resetMove();
            return;
        }
        if (player.getX() > getX()) {
            // move enemy right
            if (!dungeon.willCollide(getX() + 1, getY())) {
                move = "right";
            }
            else {
                checkUpDown(player);
            }
            resetMove();
            return;
        }
    }

    public void resetMove() {
        if (move != "") {
            resetPosition();
            move = "";
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
                    decideLeftRight(player);
                }
            }
            if (player.getY() > getY()) {
                if (!dungeon.willCollide(getX(), getY() - 1)) {
                    move = "up";
                }
                else {
                    decideLeftRight(player);
                }
            }
        }
        resetMove();
    }

    public void decideLeftRight(Player player) {
        if (!dungeon.willCollide(getX() + 1, getY())) {
            move = "right";
        }
        else {
            move = "left";
        }
    }

    public void checkLeftRight(Player player) {
        if (player.getX() < getX()) {
            if (!dungeon.willCollide(getX() - 1, getY())) {
                move = "left";
            }
            else {
                move = "right";
            }
        }
        else {
            if (!dungeon.willCollide(getX() + 1, getY())) {
                move = "right";
            }
            else {
                move = "left";
            }
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

    public boolean collideWithPlayer(Player player) {
        if (getX() == player.getX()) {
            if (getY() - 1 == player.getY()) {
                return true;
            }
            if (getY() + 1 == player.getY()) {
                return true;
            }
        }
        if (getY() == player.getY()) {
            if (getX() - 1 == player.getX()) {
                return true;
            }
            if (getX() + 1 == player.getX()) {
                return true;
            }
        }
        return false;
    }
}