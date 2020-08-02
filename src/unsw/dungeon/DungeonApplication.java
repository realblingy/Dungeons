package unsw.dungeon;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    private String dungeonMap;
    private Stage stage;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        stage.setTitle("Dungeon");
        stage.setResizable(false);
        dungeonMap = "maze";
        changeScene("MainMenu");
    }

    public void resetDungeonMap(String string) {
        dungeonMap = string;
    }

    public Controller getController(String sceneName) {
        if (sceneName == "MainMenu") {
            return new MainMenuController(this);
        }
        else if (sceneName == "MapsMenu") {
            return new MapsMenuController(this);
        }
        else if (sceneName == "Dungeon") {
            try {
                DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(dungeonMap + ".json");
                return new DungeonController(dungeonLoader.load(), dungeonLoader.getEntities(), this);
            }
            catch (IOException e) {
                System.out.println("Cannot load Dungeon.");
            }
        }
        return null;
    }

    public void changeScene(String sceneName) throws IOException {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource(sceneName + "View.fxml"));
        loader.setController(getController(sceneName));
        root = loader.load();
        Scene scene = new Scene(root);
        focusRoot();
        stage.setScene(scene);
        stage.show();
    }

    public void focusRoot() {
        root.requestFocus();
    }

    public void update(Controller controller) throws IOException {
        if (controller instanceof MainMenuController) {
            MainMenuController c = (MainMenuController) controller;
            switch(c.getAction()) {
                case "play":
                    changeScene("Dungeon");
                    break;
                case "maps":
                    changeScene("MapsMenu");
                    break;
            }
        }
        else if (controller instanceof MapsMenuController) {
            MapsMenuController c = (MapsMenuController) controller;
            changeScene("MainMenu");
            dungeonMap = c.getDungeon();
            System.out.println(dungeonMap);
        }
        else if (controller instanceof DungeonController) {
            DungeonController c = (DungeonController) controller;
            if (c.getPauseMenu().isReturnToMenu()) {
                changeScene("MainMenu");
                return;
            }
            changeScene("Dungeon");
        }
    }

    // public void exitGame() {

    // }
    // Hi

    public static void main(String[] args) {
        launch(args);
    }
}
