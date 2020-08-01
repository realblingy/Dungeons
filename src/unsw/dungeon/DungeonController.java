package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    // @FXML
    // private Pane pane;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    public Parent root;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, DungeonApplication application) {
        super(application);
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
    }

    @Override
    public void notifyApplication() throws IOException {
        // TODO Auto-generated method stub
        return;
    }

    public void displayMenu() {
        //creating a text field 
        stackpane.getChildren().add(new Text("PAUSED!"));
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
    }

    @FXML
    public void playGame(ActionEvent event) throws IOException {
        Parent gameViewParent = FXMLLoader.load(getClass().getResource("DungeonView.fxml"));
        Scene gameViewScene = new Scene(gameViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(gameViewScene);
        window.show();
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
            if (player.canMove()) {
                player.setMove(false);
                displayMenu();
            }
            else {
                player.setMove(true);
            }
        default:
            break;
        }
    }

}

