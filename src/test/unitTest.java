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
        d.setPlayer(player);
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
        Dungeon d = make.makeDungeon("wall", "maze.json", 4, 4);
        Player player = new Player(d, 1, 1);
        d.setPlayer(player);
        d.addEntity(player);
        d.addEntity(new Wall(0, 0));
        d.addEntity(new Wall(1, 0));
        d.addEntity(new Wall(2, 0));
        d.addEntity(new Wall(3, 0));
        d.addEntity(new Wall(0, 1));
        d.addEntity(new Wall(0, 2));
        d.addEntity(new Wall(0, 3));
        d.addEntity(new Wall(3, 1));
        d.addEntity(new Wall(3, 2));
        d.addEntity(new Wall(3, 3));
        d.addEntity(new Wall(1, 3));
        d.addEntity(new Wall(2, 3));
        player.moveLeft();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveUp();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);   
        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1); 
        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1);    
        player.moveDown();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 2);
        player.moveDown();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 2);
    }
    
    
    @Test
    public void testExit(){
        Dungeon d = make.makeDungeon("exit", "maze.json", 20, 20);
        Player player = new Player(d, 0, 0);
        d.setPlayer(player);
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
    public void testPotion() throws InterruptedException{
        Dungeon d = make.makeDungeon("exit", "advanced.json", 20, 20);
        Player player = new Player(d, 0, 0);
        d.setPlayer(player);
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
        // Player walks to enemy with invincibility
        player.moveRight();
        // Player should still move and enemy should be dead
        assertEquals(player.canMove(), true);
        assertEquals(d.hasEntity(enemy), false);
        TimeUnit.SECONDS.sleep(11);
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
        // Player should teleport to corresponding portal coordinates
        player.moveDown();
        assertEquals(player.getX(), 17);
        assertEquals(player.getY(), 17);

        d.addEntity(new Portal(d, 14, 17, 1));
        d.addEntity(new Portal(d, 0, 3, 1));

        for (int x = 0; x < 3; x++) {
            player.moveLeft();
        }
        // Player should teleport to corresponding portal coordinates
        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 3);

        d.addEntity(new Portal(d, 0, 2, 2));

        player.moveUp();
        // Player should not be able to enter portal with no corresponding portal
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
        // Dungeon should have treasures 
        assertEquals(d.hasEntity(t1), true);
        assertEquals(d.hasEntity(t2), true);

        player.moveDown();
        // Player should collect first treasure
        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 1);
        assertEquals(d.hasEntity(t1), false);
        for (int x = 0; x < 17; x++) {
            player.moveRight();
            player.moveDown();
        }
        player.moveUp();
        // Player should collect second treasure
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
        d.addEntity(player);
        d.addEntity(enemy);
        d.addEntity(sword);

        player.moveRight();
        // Sword should be on player
        assertEquals(d.hasEntity(sword), false);
        for (int x = 0; x < 5; x++) {
            player.moveRight();
        }
        // Player should kill enemy and still be able to move
        assertEquals(player.canMove(), true);
        assertEquals(d.hasEntity(enemy), false);
    }

    @Test
    public void testKeyDoor() {
        Dungeon d = make.makeDungeon("exit", "advanced.json", 5, 5);
        Player player = new Player(d, 1, 1);
        d.setPlayer(player);
        d.addEntity(player);
        d.addEntity(new Door(d, 1, 2, 0));
        d.addEntity(new Door(d, 2, 2, 1));
        DungeonKey key1 = new DungeonKey(d, 1, 3, 1);
        DungeonKey key0 = new DungeonKey(d, 2, 1, 0);
        d.addEntity(key1);
        d.addEntity(key0);
        player.moveDown();
        // player does not hold any key
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 1);
        player.moveRight();
        // player pick up key with id 0
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1);
        assertEquals(d.hasEntity(key0), false);
        player.moveDown();
        // player cannot open the door with id 1 by the key id 0
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1);
        player.moveLeft();
        // player can open the door wit same id as the key
        player.moveDown();
        assertEquals(player.getX(), 1);
        assertEquals(player.getY(), 2);      
        player.moveDown();
        assertEquals(d.hasEntity(key1), false);
        player.moveRight();
        player.moveUp();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 2);   
    }

    @Test
    public void testBoulderSwitch() {
        Dungeon d = make.makeDungeon("boulder", "boulders.json", 5, 4);
        Player player = new Player(d, 0, 2);
        Switch s1 = new Switch(d, 2, 1);
        Switch s2 = new Switch(d, 3, 2);
        Switch s3 = new Switch(d, 2, 2);
        Boulder b = new Boulder(d, 1, 1);
        d.addEntity(s1);
        d.setPlayer(player);
        d.addEntity(player);
        d.addEntity(s2);
        d.addEntity(s3);
        d.addEntity(new Wall(4, 0));
        d.addEntity(new Wall(4, 1));
        d.addEntity(new Wall(4, 2));
        d.addEntity(new Wall(4, 3));
        d.addEntity(b);
        d.addEntity(new Boulder(d, 1, 2));
        // add Boulder to the swtich at the beginning should also trigger the switch
        d.addEntity(new Boulder(d, 2, 2));
        assertEquals(s3.getTrigger(), 1);
        player.moveRight();
        // can only push one Boulder at a time
        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 2);
        player.moveUp();
        // trigger the switch once the player push the boulder onto it
        player.moveRight();
        assertEquals(b.getX(), 2);
        assertEquals(b.getY(), 1);
        assertEquals(s1.getTrigger(), 1);
        // shut off the switch once the player push off the boulder
        player.moveRight();
        assertEquals(s1.getTrigger(), 0);
        // cannot push the boulder if there is a wall next to the boulder
        player.moveRight();
        assertEquals(b.getX(), 3);
        assertEquals(b.getY(), 1);
        player.moveUp();
        player.moveRight();
        player.moveDown();
        assertEquals(s3.getTrigger(), 1);
        assertEquals(s3.allTrigger(), false);
    }

}