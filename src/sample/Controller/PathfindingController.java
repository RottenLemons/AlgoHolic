package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PathfindingController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXComboBox<PathfindingStrategy.Algorithms> algoCB;

    @FXML
    private JFXComboBox<AStarStrategy.Heuristic> heuristicCB;


    @FXML
    private JFXComboBox<Tile.Types> tileCB;

    @FXML
    private JFXButton wallsBtn;

    @FXML
    private JFXButton runBtn;

    @FXML
    private JFXButton clearBtn;

    @FXML
    private AnchorPane iconPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXButton hamburgerBtn;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXButton infoBtn;

    @FXML
    private Spinner<Integer> delaySpinner;

    private Grid grid = new Grid();
    public static final SimpleIntegerProperty DELAY_PROPERTY = new SimpleIntegerProperty();
    public static final SimpleBooleanProperty disableUI = new SimpleBooleanProperty(false);

    @FXML
    private void clearBtnOnClick(ActionEvent event) {
        grid.clearGrid();
    }

    @FXML
    private void hamburgerBtnOnClick(ActionEvent event) {
        if (drawer.isOpened()) {
            drawer.close();
        } else {
            drawer.open();
        }
    }

    @FXML
    private void runBtnOnClick(ActionEvent event) {
        if (!grid.isReady()) {
            return;
        }

        disableUI.set(true);
        drawer.close();
        PathfindingStrategy.Algorithms algorithm = null;
        AStarStrategy.Heuristic heuristic = null;

        for (PathfindingStrategy.Algorithms algo : FXCollections.observableArrayList(PathfindingStrategy.Algorithms.values())) {
            if(algo == algoCB.getValue()) {
                algorithm = algo;
            }
        }

        for (AStarStrategy.Heuristic heur : FXCollections.observableArrayList(AStarStrategy.Heuristic.values())) {
            if(heur == heuristicCB.getValue()) {
                heuristic = heur;
            }
        }


        try {
            HeuristicStrategy heuristicStrategy = StrategyFactory.getHeuristicStrategy(heuristic);
            PathfindingStrategy pathfindingStrategy = StrategyFactory.getPathfindingStrategy(algorithm, heuristicStrategy);
            grid.executePathfinding(pathfindingStrategy);
        }
        catch (InterruptedException ex) {
        }
    }

    @FXML
    private void wallsBtnOnClick(ActionEvent event) {
        grid.clearGrid();
        grid.addRandomWalls();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AnchorPane pane = FXMLLoader.load(Main.class.getResource("View/drawer.fxml"));
            drawer.setSidePane(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1, 1);
        delaySpinner.setValueFactory(valueFactory);
        DELAY_PROPERTY.bind(delaySpinner.valueProperty());
        heuristicCB.setItems(FXCollections.observableArrayList(AStarStrategy.Heuristic.values()));
        algoCB.setItems(FXCollections.observableArrayList(PathfindingStrategy.Algorithms.values()));
        tileCB.setItems(FXCollections.observableArrayList(Tile.Types.values()));
        tileCB.getItems().remove(Tile.Types.PATH);
        tileCB.getItems().remove(Tile.Types.HIGHLIGHT);
        tileCB.getItems().remove(Tile.Types.VISITED);
        tileCB.getSelectionModel().selectFirst();
        heuristicCB.getSelectionModel().selectFirst();
        algoCB.getSelectionModel().selectFirst();
        algoCB.setOnAction((event) -> {
            heuristicCB.setDisable(!(algoCB.getSelectionModel().getSelectedItem().name().equals("AStar")));
        });
        tileCB.setOnAction((event) -> {
            FXCollections.observableArrayList(Tile.Types.values()).stream().filter((item) -> (tileCB.getValue().toString().equals(item.toString()))).forEachOrdered((item) -> {
                grid.changeClickType(item);
            });
        });
        GridPane gridPane = new GridPane();
        grid.gridInit(50, 26, 20);
        for(Tile[] row : grid.getGrid()) {
            for(Tile tile: row) {
                gridPane.getChildren().add(tile.getStackPane());
            }
        }
        gridPane.setOnMousePressed((event) -> {
            if (grid.getTiles().stream().anyMatch(tile -> tile.getType().equals(Tile.Types.VISITED))) {
                grid.clearGrid2();
            }
        });
        anchorPane.getChildren().add(gridPane);
        gridPane.disableProperty().bind(disableUI);
        delaySpinner.disableProperty().bind(disableUI);
        algoCB.disableProperty().bind(disableUI);
        tileCB.disableProperty().bind(disableUI);
        runBtn.disableProperty().bind(disableUI);
        clearBtn.disableProperty().bind(disableUI);
        wallsBtn.disableProperty().bind(disableUI);
        hamburgerBtn.disableProperty().bind(disableUI);
    }

    @FXML
    private void spinnerScrollAction(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            delaySpinner.increment();
        } else if (event.getDeltaY() < 0) {
            delaySpinner.decrement();
        }
    }

    @FXML
    private void infoBtnOnClick(ActionEvent event) throws IOException {
        delaySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1, 1));
        Parent root = FXMLLoader.load(Main.class.getResource("View/info.fxml"));
        Stage stage = (Stage) infoBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
