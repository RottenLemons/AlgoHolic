package sample.Controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;
import sample.Model.DijkstraStrategy;
import sample.Model.Grid;
import sample.Model.Tile;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {
    @FXML
    private AnchorPane grid;

    @FXML
    private Label label;

    private final SequentialTransition progress = new SequentialTransition();

    private static ObservableList<String> texts = FXCollections.observableArrayList("Creating random lists...",
            "Creating the comparing device...", "Loading the Grid", "Loading DFS...", "Preparing Heuristics...",
            "Preparing AStar...", "Ciphering Text...","Hail Caesar...", "Preparing Some Ciphers...",
            "Looking at Zodiacs...", "My Zodiac is Leo...", "Sorting a List...",
            "The Sun is Hot...", "Coding RSA.java...", "Crying after finishing RSA...", "Trying to understand AStar", "Experimenting with Affine Cipher",
            "Creating Animations", "Bug Testing Code", "Generating Poor Code",
            "Catching all bugs...", "Gotta Catch Them All...",
            "Translating into Python...",
            "Creating TextFields...", "Drinking Some AlgoHol...", "Looking At Notes...",
            "Creating Logo...", "Playing Minecraft...", "Loading JFoenix...", "Creating GUI..." , "Completed.");






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Grid grid = new Grid();
        GridPane gridPane = new GridPane();
        grid.gridInit(28, 2, 20);
        for(Tile[] row : grid.getGrid()) {
            for(Tile tile: row) {
                gridPane.getChildren().add(tile.getStackPane());
            }
        }
        grid.getGrid()[0][0].setAttributes(Tile.Types.START);
        grid.start = grid.getGrid()[0][0];
        grid.getGrid()[27][1].setAttributes(Tile.Types.END);
        grid.end = grid.getGrid()[27][1];
        PathfindingController.DELAY_PROPERTY.set(65);
        try {
            grid.executePathfinding(new DijkstraStrategy());
        } catch (InterruptedException e) {

        }
        this.grid.getChildren().add(gridPane);
        gridPane.setDisable(true);
        progress.getChildren().add(new Transition() {{
                setCycleDuration(Duration.millis(8000));
            }

            @Override
            protected void interpolate(double frac) {
                label.setText(texts.get((int) (frac * (texts.size() - 1))));
                if(frac==1){
                    ((Stage)label.getScene().getWindow()).close();
                    try {
                        PathfindingController.DELAY_PROPERTY.set(1);
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("View/sample.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        stage.setTitle("AlgoHolic");
                        stage.setScene(scene);
                        stage.show();
                        stage.getIcons().add(new Image("file:src/sample/View/icon.png"));
                        stage.setResizable(false);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        progress.play();
    }
}