package test;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
// import org.junit.jupiter.params.ParameterizedTest;
// import org.junit.jupiter.params.provider.ValueSource;

import unsw.dungeon.*;

class make {

    static Dungeon dungeon;
    static DungeonLoader dungeonLoader;
    static Goal goal;
    JSONObject json;

    static void initialiseDungeonLoader(String dungeonName) {
        try {
            dungeonLoader = new DungeonControllerLoader("dungeons/" + dungeonName);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    static Dungeon makeDungeon(String goal, String dungeonName, int width, int height) {
        initialiseDungeonLoader(dungeonName);
        JSONObject jsonGoal = new JSONObject().put("goal", goal);
        dungeon = new Dungeon(dungeonLoader, width, height, jsonGoal);
        return dungeon;
    }
}

public class unitTest {

    @Test
    public void testPlayerMovement() {
        Dungeon d = make.makeDungeon("exit", "maze.json", 20, 20);
        Player player = new Player(d, 0, 0);
        d.assignPlayer(player);
        // Moving down
        for (int x = 0; x < 5; x++) {
            player.moveDown();
            assertEquals(player.getY(), x + 1);
        }
        // Moving right
        for (int x = 0; x < 5; x++) {
            player.moveRight();
            assertEquals(player.getX(), x + 1);
        }
        // Moving left
        for (int x = 5; x < 0; x--) {
            player.moveLeft();
            assertEquals(player.getX(), x - 1);
        }
        // Moving up
        for (int x = 5; x < 0; x--) {
            player.moveUp();
            assertEquals(player.getY(), x - 1);
        }
    }

    @Test
    public void testWall() {
        Dungeon d = make.makeDungeon("exit", "maze.json", 20, 20);
    }
    
    @Test
    public void testExit(){
        Dungeon d = make.makeDungeon("exit", "maze.json", 20, 20);
        Player player = new Player(d, 0, 0);
        d.assignPlayer(player);
        d.addEntity(player);
        d.addEntity(new Exit(d, 4, 4));

        // Moving player to exit
        for (int x = 0; x < 5; x++) {
            player.moveDown();
            player.moveRight();
        }
        // Player should not move
        assertEquals(player.canMove(), false);
    }

    @Test
    public void testPotion() {
        Dungeon d = make.makeDungeon("exit", "advanced.json", 20, 20);
        Player player = new Player(d, 0, 0);
        d.assignPlayer(player);
        d.addEntity(player);
        Potion p = new Potion(d, 1, 0);
        d.addEntity(p);
        // Check if entity in dungon
        assertEquals(d.hasEntity(p), true);
        player.moveRight();
        // Potion should be gone and player is invincible
        assertEquals(d.hasEntity(p), false);
        assertEquals(player.isInvincible(), true);

        Enemy enemy = new Enemy(d, 2, 0);
        d.addEntity(enemy);
        player.moveRight();
        // Player should still move and enemy should be dead
        assertEquals(player.canMove(), true);
        assertEquals(d.hasEntity(enemy), false);
        TimeUnit.SECONDS.sleep(10);
        assertEquals(player.isInvincible(), false);
        
    }

    @Test
    public void testPortal() {
        Dungeon d = make.makeDungeon("exit", "advanced.json", 20, 20);
        Player player = new Player(d, 0, 0);
        d.setPlayer(player);
        d.addEntity(player);
        d.addEntity(new Portal(d, 0, 1, 0));
        d.addEntity(new Portal(d, 17, 17, 0));

        player.moveDown();
        assertEquals(player.getX(), 17);
        assertEquals(player.getY(), 17);

        d.addEntity(new Portal(d, 14, 17, 1));
        d.addEntity(new Portal(d, 0, 3, 1));

        for (int x = 0; x < 3; x++) {
            player.moveLeft();
        }

        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 3);
    }

    @Test
    public void testTreasure() {
        Dungeon d = make.makeDungeon("exit", "advanced.json", 20, 20);
        Player player = new Player(d, 0, 0);
        d.setPlayer(player);
        d.addEntity(player);
        Treasure t1 = new Treasure(d, 0, 1);
        Treasure t2 = new Treasure(d, 17, 17);
        d.addEntity(t1);
        d.addEntity(t2);

        assertEquals(d.hasEntity(t1), true);
        assertEquals(d.hasEntity(t2), true);

        player.moveDown();
        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 1);
        assertEquals(d.hasEntity(t1), false);
        

        for (int x = 0; x < 17; x++) {
            player.moveRight();
            player.moveDown();
        }

        player.moveUp();
        assertEquals(player.getY(), 17);
        assertEquals(player.getX(), 17);
        assertEquals(d.hasEntity(t2), false);
    }

    @Test 
    public void testSword() {
        Dungeon d = make.makeDungeon("exit", "advanced.json", 20, 20);
        Player player = new Player(d, 0, 0);
        Enemy enemy = new Enemy(d, 5, 0);
        Sword sword =new Sword(d, 1, 0);
        d.setPlayer(player);
        d.addEntity(enemy);
        d.addEntity(sword);

        player.moveRight();
        assertEquals(d.hasEntity(sword), false);

        for (int x = 0; x < 5; x++) {
            player.moveRight();
        }

        assertEquals(player.canMove(), true);
        assertEquals(d.hasEntity(enemy), false);
    }

}