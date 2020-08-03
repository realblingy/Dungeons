package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images
    private Image playerImage;
    private Image wallImage;
    private Image exitImage;
    private Image boulderImage;
    private Image switchImage;
    private Image closedDoorImage;
    private Image openDoorImage;
    private Image keyImage;
    private Image swordImage;
    private Image potionImage;
    private Image portalImage;
    private Image treasureImage;
    private Image enemyImage;
    private Image invincibleImage;
    private Image pickaxeImage;

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        switchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        closedDoorImage = new Image((new File("images/closed_door.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        openDoorImage = new Image((new File("images/open_door.png")).toURI().toString());
        swordImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        potionImage = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());
        enemyImage = new Image((new File("images/deep_elf_master_archer.png")).toURI().toString());
        invincibleImage = new Image((new File("images/human_invincible.png")).toURI().toString());
        pickaxeImage = new Image((new File("images/pickaxe.png")).toURI().toString());
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(Door closedDoor) {
        ImageView view = new ImageView(closedDoorImage);
        addEntity(closedDoor, view);
    }

    @Override
    public void onLoad(DungeonKey key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }

    @Override
    public void onLoad(Switch switchPlate) {
        ImageView view = new ImageView(switchImage);
        addEntity(switchPlate, view);
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
    }    

    @Override
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        addEntity(potion, view);
    }    

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }    

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
    }

    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }

    @Override
    public void onLoad(Pickaxe pickaxe) {
        ImageView view = new ImageView(pickaxeImage);
        addEntity(pickaxe, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        view.setX(entity.getX());
        view.setY(entity.getY());
        trackPosition(entity, view);
        entities.add(view);
    }

    public List<ImageView> getEntities() {
        return entities;
    }

    public Image getImage(Entity entity) {
        if (entity instanceof Potion) {
            return potionImage;
        }
        else if (entity instanceof Wall) {
            return wallImage;
        }
        else if (entity instanceof Pickaxe) {
            return pickaxeImage;
        }
        else if (entity instanceof Treasure) {
            return treasureImage;
        }
        else if (entity instanceof Sword) {
            return swordImage;
        }
        else if (entity instanceof DungeonKey) {
            return keyImage;
        }
        else if (entity instanceof Door) {
            return closedDoorImage;
        }
        else if (entity instanceof Enemy) {
            return enemyImage;
        }
        if (entity instanceof Player) {
            Player p = (Player) entity;
            return (p.isInvincible() ?  playerImage : invincibleImage);
        }
        return null;
    }

    public void removeEntity(Entity entity) {
        Image entityImageView = getImage(entity);
        if (entityImageView == null) { return; }
        for (ImageView image : entities) {
            if (image.getX() == entity.getX() && image.getY() == entity.getY()) {
                try {
                    if (image.getImage().equals(entityImageView)) {
                        image.setImage(null);

                        if (entity instanceof Door) {
                            image.setImage(openDoorImage);
                        }
                    }
                }  catch (NullPointerException e) {}
            }
        }
    }

    public void changeEntityImage(Entity entity) {
        Image entityImageView = getImage(entity);
        
        for (ImageView e : entities) {
            try {
                if (e.getImage().equals(entityImageView)) {
                    if (entity instanceof Player) {
                        Player p = (Player) entity;
                        boolean playerIsInvis = p.isInvincible();
                        if (playerIsInvis) {
                            e.setImage(invincibleImage);
                        }
                        else {
                            e.setImage(playerImage);
                        } 
                        return;
                    }
                    
                }
            } catch (NullPointerException exception) {}

        }
    }
    

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }


}
