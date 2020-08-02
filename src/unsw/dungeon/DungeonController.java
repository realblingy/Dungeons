package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController extends Controller {

    @FXML
    private GridPane squares;

    @FXML 
    private StackPane stackpane;

    @FXML
    private GridPane inventory;

    @FXML
    private VBox pauseMenu;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private PauseMenu menu;

    private ImageLoader dungeonImageLoader;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, DungeonApplication application) {
        super(application);
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        dungeon.setController(this);
        menu = new PauseMenu();
        dungeonImageLoader = new ImageLoader();
    }

    @Override
    public void notifyApplication() throws IOException {
        super.getDungeonApplication().update(this);
    }

    public void resetDungeonMap(String string) {
        DungeonApplication dungeonApp = getDungeonApplication();
        dungeonApp.resetDungeonMap(string);
    }

    public void returnToMenu() {
        menu.setReturnToMenu(true);
        try {
            notifyApplication();
        }
        catch (IOException e) {
            System.out.println("Could not return to main menu.");
        }
        
    }

    public PauseMenu getPauseMenu() {
        return menu;
    }

    public void displayMenu() {
        //creating a text field 
        if (!inventory.isVisible()) {
            player.setMove(!player.canMove());
        }
        pauseMenu.setVisible(!pauseMenu.isVisible());
        if (pauseMenu.isVisible()) {
            squares.setEffect(new GaussianBlur());
            inventory.setEffect(new GaussianBlur());
        }
        else {
            squares.setEffect(null);
            inventory.setEffect(null);
        }
        
    }

    public ImageView loadImage() {
        return new ImageView();
    }

    public void displayInventory() {
        List<Item> playerInventory = player.getInventory();
        inventory.getChildren().clear();
        int horizontal = 0;
        int vertical = 0;

        for (Item i : playerInventory) {
            dungeonImageLoader.setItem(i);
            inventory.add(dungeonImageLoader.generateImageView(), horizontal % 4, vertical);
            horizontal++;
            if (horizontal % 4 == 0 && horizontal > 0) { vertical = vertical % 4;  }
            if (vertical == 16) { break; }
        }

        player.setMove(!player.canMove());
        inventory.setVisible(!inventory.isVisible());
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());
        inventory.setVisible(false);
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

        pauseMenu.setVisible(false);
    }

    @FXML
    public void handleReturnToMenu(ActionEvent event) {
        returnToMenu();
    }
    
    @FXML 
    public void handleResume(ActionEvent event) {
        displayMenu();
        getDungeonApplication().focusRoot();
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case ESCAPE:
            displayMenu();
            break;
        case E:
            if (pauseMenu.isVisible()) {
                break;
            }
            displayInventory();
            break;
        default:
            break;
        }
    }

}

