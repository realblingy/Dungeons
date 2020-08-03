package unsw.dungeon;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    private String dungeonMap;
    private String difficulty;
    private Stage stage;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        stage.setTitle("Dungeon");
        stage.setResizable(true);
        dungeonMap = "maze";
        difficulty = "easy";
        changeScene("MainMenu");
    }

    public String getDungeonMap() {
        return dungeonMap;
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
                DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(dungeonMap + "_" + difficulty + ".json");
                return new DungeonController(dungeonLoader.load(), dungeonLoader.getEntities(), this);
            }
            catch (IOException e) {
                System.out.println("Cannot load Dungeon.");
            }
        }
        else if (sceneName == "DifficultyMenu") {
            return new DifficultyMenuController(this);
        }
        return null;
    }

    public void changeScene(String sceneName) throws IOException {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource(sceneName + "View.fxml"));
        loader.setController(getController(sceneName));
        root = loader.load();
        Scene scene = new Scene(root);
        // scene.getStylesheets().add("src/unsw/dungeon/dungeons.css");
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
                case "difficulty":
                    changeScene("DifficultyMenu");
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
        else if (controller instanceof DifficultyMenuController) {
            DifficultyMenuController c = (DifficultyMenuController) controller;
            changeScene("MainMenu");
            difficulty = c.getDifficulty();
            System.out.println(difficulty);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
