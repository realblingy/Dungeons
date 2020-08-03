package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.scene.image.Image;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");
        JSONObject goal = json.getJSONObject("goal-condition");

        Dungeon dungeon = new Dungeon(this, width, height, goal);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "exit":
            Exit exit = new Exit(dungeon,x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "switch":
            Switch switchPlate = new Switch(dungeon, x, y);
            onLoad(switchPlate);
            entity = switchPlate;
            break; 
        case "closedDoor":
            Door closedDoor = new Door(dungeon, x, y, json.getInt("id"));
            onLoad(closedDoor);
            entity = closedDoor;
            break;
        case "key":
            DungeonKey key = new DungeonKey(dungeon, x, y, json.getInt("id"));
            onLoad(key);
            entity = key;
            break;
        case "sword":
            Sword sword = new Sword(dungeon, x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "potion":
            Potion potion = new Potion(dungeon, x, y);
            onLoad(potion);
            entity = potion;
            break;
        case "portal":
            int id = json.getInt("id");
            Portal portal = new Portal(dungeon, x, y, id);
            onLoad(portal);
            entity = portal;
            break;
        case "treasure":
            Treasure treasure = new Treasure(dungeon, x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        case "pickaxe":
            Pickaxe pickaxe = new Pickaxe(dungeon, x, y);
            onLoad(pickaxe);
            entity = pickaxe;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            dungeon.setEnemy(enemy);
            onLoad(enemy);
            entity = enemy;
            break;

        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);

    public abstract void onLoad(Exit exit);

    public abstract void onLoad(Boulder boulder);

    public abstract void onLoad(Switch switchPlate);

    public abstract void onLoad(Door closedDoor);

    public abstract void onLoad(DungeonKey key);

    public abstract void onLoad(Sword sword);

    public abstract void onLoad(Potion potion);

    public abstract void onLoad(Portal portal);

    public abstract void removeEntity(Entity entity);

    public abstract void onLoad(Treasure treasure);

    public abstract void onLoad(Pickaxe pickaxe);

    public abstract void onLoad(Enemy enemy);

    public abstract void changeEntityImage(Entity entity);

}
