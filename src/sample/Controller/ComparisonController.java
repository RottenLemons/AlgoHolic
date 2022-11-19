package sample.Controller;

import com.jfoenix.controls.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ComparisonController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane pane1;

    @FXML
    private Label time1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private Label time2;

    @FXML
    private Spinner<Integer> delaySpinner;

    @FXML
    private JFXComboBox<String> algoCB1;

    @FXML
    private JFXButton startBtn;

    @FXML
    private JFXTextField listTF;

    @FXML
    private JFXButton enterBtn;

    @FXML
    private JFXButton randomBtn;

    @FXML
    private JFXButton visualiseBtn;

    @FXML
    private JFXTextField listTF1;

    @FXML
    private JFXButton enterBtn1;

    @FXML
    private JFXComboBox<String> algoCB2;

    @FXML
    private JFXTextField listTF2;

    @FXML
    private JFXButton enterBtn2;

    @FXML
    private AnchorPane iconPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXButton hamburgerBtn;

    @FXML
    private JFXDrawer drawer;

    public static final SimpleIntegerProperty DELAY_PROPERTY = new SimpleIntegerProperty();
    public static final SimpleBooleanProperty disableUI = new SimpleBooleanProperty(false);
    private static AnimationController animationController1;
    private static AnimationController animationController2;
    private ExecutorService executor;
    private Timeline timeline;
    ObservableList<String> algorithms = FXCollections.observableArrayList("Bubble", "Merge", "Quick", "Insertion");
    SortAlgorthm[] sortAlgorthms = {BubbleSort.BUBBLE, MergeSort.MERGE, QuickSort.QUICK, InsertionSort.INSERTION};
    AnchorPane anchorPane1 = null;
    AnchorPane anchorPane2 = null;
    public static String s1;
    public static String s2;

    @FXML
    private void enterBtnOnClick(ActionEvent event) {
        if (listTF.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("TextField is Empty");
            alert.setHeaderText("TextField is Empty");
            alert.setContentText("Enter Integers in TextField");
            alert.show();
        } else {
            if (listTF.getText().matches("([\\d]+,)+[\\d]+") || listTF.getText().matches("([\\d]+, )+[\\d]+")) {
                int[] compares = new int[listTF.getText().split("[, ]+").length];
                for (int i = 0; i < listTF.getText().split("[, ]+").length; i++) {
                    compares[i] = Integer.valueOf(listTF.getText().split("[, ]+")[i]);
                }
                Rand.setRandSet(compares);
                animationController1.setPresetValues();
                animationController2.setPresetValues();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Input");
                alert.setHeaderText("Incorrect Input");
                alert.setContentText("Enter proper input");
                alert.show();
                listTF.clear();
            }
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

    @FXML
    private void startBtnOnClick(ActionEvent event) {
        s1 = algoCB1.getValue();
        s2 = algoCB2.getValue();
        disableUI.set(true);
        drawer.close();

        int sortIndex = getAlgorithmIndex1();
        int sortIndex2 = getAlgorithmIndex2();
        timeline.play();
        Compare[] rand = new Compare[Rand.getArray().length];
        for (int i = 0; i < Rand.getArray().length; i++) {
            rand[i] = new Compare(Rand.getArray()[i].getValue());
        }
        SortAlgorthm sorter = sortAlgorthms[sortIndex];
        sorter.sort(Rand.getArray(), 0, Rand.max_size - 1);
        ArrayList<ArrayList<Integer>> ints = (ArrayList<ArrayList<Integer>>) SortAlgorthm.getSwaps().clone();
        SortAlgorthm sorter2 = sortAlgorthms[sortIndex2];
        sorter2.sort(rand, 0, Rand.max_size - 1);
        ArrayList<ArrayList<Integer>> ints2 = (ArrayList<ArrayList<Integer>>) SortAlgorthm.getSwaps().clone();
        executor = Executors.newFixedThreadPool(3);
        AtomicBoolean a = new AtomicBoolean(true);
        AtomicBoolean b = new AtomicBoolean(true);

        executor.submit(() -> {
            long start =  System.currentTimeMillis();
            for (ArrayList<Integer> in : ints) {
                int[] integer = new int[in.size()];
                for (int i = 0; i < in.size(); i++) {
                    integer[i] = in.get(i);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ComparisonController.updateViews1(integer);
                    }
                });
                long startTime =  System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < DELAY_PROPERTY.get()) {

                }
            }
            long end = System.currentTimeMillis();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    time1.setText("Time Taken: " + (end - start) + " ms");
                }
            });
            b.set(false);
            a.set(false);
        });
        executor.submit(() -> {
            long start =  System.currentTimeMillis();
            for (ArrayList<Integer> in : ints2) {
                int[] integer = new int[in.size()];
                for (int i = 0; i < in.size(); i++) {
                    integer[i] = in.get(i);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ComparisonController.updateViews2(integer);
                    }
                });
                long startTime =  System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime < DELAY_PROPERTY.get()) {

                }
            }
            long end = System.currentTimeMillis();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    time2.setText("Time Taken: " + (end - start) + " ms");
                }
            });
            b.set(false);
        });

        executor.submit(() -> {
            while (a.get() || b.get()) {

            }
            stop();
        });
    }

    private void stop() {
        try {
            executor.shutdown();
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {

        } finally {
            if (!executor.isTerminated()) {
                executor.shutdownNow();
            }
            timeline.stop();
            disableUI.set(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("View/anim.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader();
        fxmlLoader2.setLocation(Main.class.getResource("View/anim.fxml"));
        try {
            anchorPane1 = fxmlLoader.load();
            anchorPane2 = fxmlLoader2.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        animationController1 = fxmlLoader.getController();
        animationController1.setY(400);
        animationController2 = fxmlLoader2.getController();
        animationController2.setY(400);
        AnchorPane.setTopAnchor(anchorPane1, 25.0);
        AnchorPane.setTopAnchor(anchorPane2, 25.0);
        pane2.getChildren().add(anchorPane2);
        pane1.getChildren().add(anchorPane1);
        algoCB1.setItems(algorithms);
        algoCB2.setItems(algorithms);
        animationController1.setPresetValues();
        animationController2.setPresetValues();
        algoCB1.getSelectionModel().select(0);
        algoCB2.getSelectionModel().select(1);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2000, 250, 10);
        delaySpinner.setValueFactory(valueFactory);
        DELAY_PROPERTY.bind(delaySpinner.valueProperty());
        timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(delaySpinner.getValue())));
        timeline.setCycleCount(Animation.INDEFINITE);
        startBtn.disableProperty().bind(disableUI);
        visualiseBtn.disableProperty().bind(disableUI);
        enterBtn.disableProperty().bind(disableUI);
        randomBtn.disableProperty().bind(disableUI);
        algoCB1.disableProperty().bind(disableUI);
        algoCB2.disableProperty().bind(disableUI);
        delaySpinner.disableProperty().bind(disableUI);
        listTF.disableProperty().bind(disableUI);
        hamburgerBtn.disableProperty().bind(disableUI);
        try {
            AnchorPane pane = FXMLLoader.load(Main.class.getResource("View/drawer.fxml"));
            drawer.setSidePane(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void hamburgerBtnOnClick(ActionEvent event) {
        if (drawer.isOpened()) {
            drawer.close();
        } else {
            drawer.open();
        }
    }

    private int getAlgorithmIndex1() {
        return algoCB1.getSelectionModel().getSelectedIndex();
    }

    private int getAlgorithmIndex2() {
        return algoCB2.getSelectionModel().getSelectedIndex();
    }

    @FXML
    private void randomBtnOnClick(ActionEvent event) {
        pane1.getChildren().remove(anchorPane1);
        pane2.getChildren().remove(anchorPane2);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("View/anim.fxml"));
        FXMLLoader fxmlLoader2 = new FXMLLoader();
        fxmlLoader2.setLocation(Main.class.getResource("View/anim.fxml"));
        AnchorPane anchorPane1 = null;
        AnchorPane anchorPane2 = null;
        try {
            anchorPane1 = fxmlLoader.load();
            anchorPane2 = fxmlLoader2.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        animationController1 = fxmlLoader.getController();
        animationController1.setY(400);
        AnchorPane.setTopAnchor(anchorPane1, 25.0);
        pane1.getChildren().add(anchorPane1);
        animationController1.setPresetValues();
        animationController2 = fxmlLoader2.getController();
        animationController2.setY(400);
        AnchorPane.setTopAnchor(anchorPane2, 25.0);
        pane2.getChildren().add(anchorPane2);
        animationController2.setPresetValues();
    }

    @FXML
    private void visualiseBtnOnClick(ActionEvent event) throws IOException {
        delaySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2000, 0, 10));
        Parent root = FXMLLoader.load(Main.class.getResource("View/sorting.fxml"));
        Stage stage = (Stage) visualiseBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public static void updateViews1(int[] ints) {
        Compare[] rand = new Compare[ints.length];
        for (int i = 0; i < ints.length; i++) {
            rand[i] = new Compare(ints[i]);
        }
        Sorter.getInstance().apply(rand, animationController1);
    }

    public static void updateViews2(int[] ints) {
        Compare[] rand = new Compare[ints.length];
        for (int i = 0; i < ints.length; i++) {
            rand[i] = new Compare(ints[i]);
        }
        Sorter.getInstance().apply(rand, animationController2);
    }
}