package sample.Controller;

import com.jfoenix.controls.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class InfoController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton runBtn;

    @FXML
    private Spinner<Integer> delaySpinner;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXComboBox<Tile.Types> tileCB;

    @FXML
    private JFXButton runBtn2;

    @FXML
    private Spinner<Integer> delaySpinner2;

    @FXML
    private AnchorPane pane2;

    @FXML
    private JFXButton backBtn2;

    @FXML
    private JFXComboBox<Tile.Types> tileCB2;

    @FXML
    private JFXComboBox<AStarStrategy.Heuristic> heuristicCB;

    @FXML
    private JFXButton runBtn3;

    @FXML
    private Spinner<Integer> delaySpinner3;

    @FXML
    private AnchorPane pane3;

    @FXML
    private JFXButton backBtn3;

    @FXML
    private JFXComboBox<Tile.Types> tileCB3;

    @FXML
    private AnchorPane iconPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXButton hamburgerBtn;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXTextArea infoTF;

    @FXML
    private JFXTextArea infoTF2;

    @FXML
    private JFXTextArea infoTF3;

    private Grid grid = new Grid();
    private Grid grid2 = new Grid();
    private Grid grid3 = new Grid();
    public static final SimpleIntegerProperty DELAY_PROPERTY = new SimpleIntegerProperty();
    public static final SimpleIntegerProperty DELAY_PROPERTY2 = new SimpleIntegerProperty();
    public static final SimpleIntegerProperty DELAY_PROPERTY3 = new SimpleIntegerProperty();
    public static final SimpleBooleanProperty disableUI = new SimpleBooleanProperty(false);

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
        drawer.close();
        disableUI.set(true);

        try {
            HeuristicStrategy heuristicStrategy = StrategyFactory.getHeuristicStrategy(AStarStrategy.Heuristic.Euclidean);
            PathfindingStrategy pathfindingStrategy = StrategyFactory.getPathfindingStrategy(PathfindingStrategy.Algorithms.Dijkstra, heuristicStrategy);
            grid.executePathfinding(pathfindingStrategy);
        }
        catch (InterruptedException ex) {
        }
    }

    @FXML
    private void spinnerScrollAction(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            delaySpinner.increment();
        } else if (event.getDeltaY() < 0) {
            delaySpinner.decrement();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Scanner sc = new Scanner(new File("src/sample/Model/Dijkstra.txt"));
            Scanner sc2 = new Scanner(new File("src/sample/Model/Astar.txt"));
            Scanner sc3 = new Scanner(new File("src/sample/Model/DFS.txt"));
            String s = "";
            String s2 = "";
            String s3 = "";
            while (sc.hasNextLine()) {
                s += sc.nextLine() + "\n";
            }
            sc.close();
            while (sc2.hasNextLine()) {
                s2 += sc2.nextLine() + "\n";
            }
            sc2.close();
            while (sc3.hasNextLine()) {
                s3 += sc3.nextLine() + "\n";
            }
            sc3.close();
            infoTF.setText(s);
            infoTF2.setText(s2);
            infoTF3.setText(s3);
        } catch (FileNotFoundException e) {

        }
        try {
            AnchorPane pane = FXMLLoader.load(Main.class.getResource("View/drawer.fxml"));
            drawer.setSidePane(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1, 1);
        delaySpinner.setValueFactory(valueFactory);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1, 1);
        delaySpinner2.setValueFactory(valueFactory2);
        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1, 1);
        delaySpinner3.setValueFactory(valueFactory3);
        DELAY_PROPERTY2.bind(delaySpinner2.valueProperty());
        DELAY_PROPERTY.bind(delaySpinner.valueProperty());
        DELAY_PROPERTY3.bind(delaySpinner3.valueProperty());
        tileCB.setItems(FXCollections.observableArrayList(Tile.Types.values()));
        tileCB.getItems().remove(Tile.Types.PATH);
        tileCB.getItems().remove(Tile.Types.HIGHLIGHT);
        tileCB.getItems().remove(Tile.Types.VISITED);
        tileCB.getSelectionModel().selectFirst();
        tileCB.setOnAction((event) -> {
            FXCollections.observableArrayList(Tile.Types.values()).stream().filter((item) -> (tileCB.getValue().toString().equals(item.toString()))).forEachOrdered((item) -> {
                grid.changeClickType(item);
            });
        });
        tileCB2.setItems(FXCollections.observableArrayList(Tile.Types.values()));
        tileCB2.getItems().remove(Tile.Types.PATH);
        tileCB2.getItems().remove(Tile.Types.HIGHLIGHT);
        tileCB2.getItems().remove(Tile.Types.VISITED);
        tileCB2.getSelectionModel().selectFirst();
        tileCB2.setOnAction((event) -> {
            FXCollections.observableArrayList(Tile.Types.values()).stream().filter((item) -> (tileCB2.getValue().toString().equals(item.toString()))).forEachOrdered((item) -> {
                grid2.changeClickType(item);
            });
        });
        tileCB3.setItems(FXCollections.observableArrayList(Tile.Types.values()));
        tileCB3.getItems().remove(Tile.Types.PATH);
        tileCB3.getItems().remove(Tile.Types.HIGHLIGHT);
        tileCB3.getItems().remove(Tile.Types.VISITED);
        tileCB3.getSelectionModel().selectFirst();
        tileCB3.setOnAction((event) -> {
            FXCollections.observableArrayList(Tile.Types.values()).stream().filter((item) -> (tileCB3.getValue().toString().equals(item.toString()))).forEachOrdered((item) -> {
                grid3.changeClickType(item);
            });
        });
        heuristicCB.setItems(FXCollections.observableArrayList(AStarStrategy.Heuristic.values()));
        heuristicCB.getSelectionModel().selectFirst();

        GridPane gridPane = new GridPane();
        GridPane gridPane2 = new GridPane();
        GridPane gridPane3 = new GridPane();
        grid.gridInit(10, 22, 20);
        grid2.gridInit(10, 20, 20);
        grid3.gridInit(10, 22, 20);
        for(Tile[] row : grid.getGrid()) {
            for(Tile tile: row) {
                gridPane.getChildren().add(tile.getStackPane());
            }
        }
        for (Tile[] row : grid2.getGrid()) {
            for(Tile tile: row) {
                gridPane2.getChildren().add(tile.getStackPane());
            }
        }
        for (Tile[] row : grid3.getGrid()) {
            for(Tile tile: row) {
                gridPane3.getChildren().add(tile.getStackPane());
            }
        }
        gridPane.setOnMousePressed((event) -> {
            if (grid.getTiles().stream().anyMatch(tile -> tile.getType().equals(Tile.Types.VISITED))) {
                grid.clearGrid2();
            }
        });
        gridPane2.setOnMousePressed((event) -> {
            if (grid2.getTiles().stream().anyMatch(tile -> tile.getType().equals(Tile.Types.VISITED))) {
                grid2.clearGrid2();
            }
        });
        gridPane3.setOnMousePressed((event) -> {
            if (grid3.getTiles().stream().anyMatch(tile -> tile.getType().equals(Tile.Types.VISITED))) {
                grid3.clearGrid2();
            }
        });
        pane.getChildren().add(gridPane);
        pane2.getChildren().add(gridPane2);
        pane3.getChildren().add(gridPane3);
        gridPane.disableProperty().bind(disableUI);
        gridPane2.disableProperty().bind(disableUI);
        gridPane3.disableProperty().bind(disableUI);
        delaySpinner.disableProperty().bind(disableUI);
        tileCB.disableProperty().bind(disableUI);
        runBtn.disableProperty().bind(disableUI);
        runBtn2.disableProperty().bind(disableUI);
        tileCB2.disableProperty().bind(disableUI);
        heuristicCB.disableProperty().bind(disableUI);
        delaySpinner2.disableProperty().bind(disableUI);
        runBtn3.disableProperty().bind(disableUI);
        delaySpinner3.disableProperty().bind(disableUI);
        tileCB2.disableProperty().bind(disableUI);
        backBtn.disableProperty().bind(disableUI);
        backBtn3.disableProperty().bind(disableUI);
        backBtn2.disableProperty().bind(disableUI);
        hamburgerBtn.disableProperty().bind(disableUI);
    }

    @FXML
    private void backBtnOnClick(ActionEvent event) throws IOException {
        delaySpinner2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1, 1));
        delaySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1, 1));
        delaySpinner3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1, 1));
        Parent root = FXMLLoader.load(Main.class.getResource("View/pathfinding.fxml"));
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void runBtn2OnClick(ActionEvent event) {
        if (!grid2.isReady()) {
            return;
        }
        drawer.close();
        disableUI.set(true);
        AStarStrategy.Heuristic heuristic = null;

        for (AStarStrategy.Heuristic heur : FXCollections.observableArrayList(AStarStrategy.Heuristic.values())) {
            if(heur == heuristicCB.getValue()) {
                heuristic = heur;
            }
        }

        try {
            HeuristicStrategy heuristicStrategy = StrategyFactory.getHeuristicStrategy(heuristic);
            PathfindingStrategy pathfindingStrategy = StrategyFactory.getPathfindingStrategy(PathfindingStrategy.Algorithms.AStar, heuristicStrategy);
            grid2.executePathfinding(pathfindingStrategy);
        }
        catch (InterruptedException ex) {
        }

    }

    @FXML
    private void spinnerScrollAction2(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            delaySpinner2.increment();
        } else if (event.getDeltaY() < 0) {
            delaySpinner2.decrement();
        }
    }

    @FXML
    private void runBtn3OnClick(ActionEvent event) {
        if (!grid3.isReady()) {
            return;
        }
        drawer.close();
        disableUI.set(true);

        try {
            HeuristicStrategy heuristicStrategy = StrategyFactory.getHeuristicStrategy(AStarStrategy.Heuristic.Euclidean);
            PathfindingStrategy pathfindingStrategy = StrategyFactory.getPathfindingStrategy(PathfindingStrategy.Algorithms.DFS, heuristicStrategy);
            grid3.executePathfinding(pathfindingStrategy);
        }
        catch (InterruptedException ex) {
        }
    }

    @FXML
    private void spinnerScrollAction3(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            delaySpinner3.increment();
        } else if (event.getDeltaY() < 0) {
            delaySpinner3.decrement();
        }
    }
}
