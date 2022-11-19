package sample.Controller;

import com.jfoenix.controls.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SortingController implements Initializable {

    @FXML
    private ImageView play;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Spinner<Integer> delaySpinner;

    @FXML
    private JFXComboBox<String> algoCB;

    @FXML
    private JFXButton startBtn;

    @FXML
    private JFXButton enterBtn;

    @FXML
    private ToggleButton tb1;

    @FXML
    private ToggleButton tb2;

    @FXML
    private ToggleButton tb3;

    @FXML
    private AnchorPane iconPane;

    @FXML
    private Label time;

    @FXML
    private Slider sortSlider;

    @FXML
    private JFXButton compareBtn;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXButton hamburgerBtn;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXButton randomBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXTextField listTF;

    @FXML
    private JFXTextArea infoTF;

    @FXML
    private JFXButton playBtn;

    public static final SimpleIntegerProperty DELAY_PROPERTY = new SimpleIntegerProperty();
    final ToggleGroup group = new ToggleGroup();
    public static final SimpleBooleanProperty disableUI = new SimpleBooleanProperty(false);
    private static AnimationController animationController;
    private ExecutorService executor;
    private Timeline timeline;
    ObservableList<String> algorithms = FXCollections.observableArrayList("Bubble", "Merge", "Quick", "Insertion");
    SortAlgorthm[] sortAlgorthms = {BubbleSort.BUBBLE, MergeSort.MERGE, QuickSort.QUICK, InsertionSort.INSERTION};
    AnchorPane anchorPane1 = null;
    public static boolean sort = false;
    private boolean start = false;

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
                sortSlider.setVisible(false);
                playBtn.setVisible(false);
                play.setVisible(false);
                time.setText("");
                int[] compares = new int[listTF.getText().split("[, ]+").length];
                for (int i = 0; i < listTF.getText().split("[, ]+").length; i++) {
                    compares[i] = Integer.valueOf(listTF.getText().split("[, ]+")[i]);
                }
                Rand.setRandSet(compares);
                animationController.setPresetValues();
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
        sort = true;
        time.setText("");
        sortSlider.setVisible(false);
        playBtn.setStyle("-fx-background-color: transparent");
        playBtn.setVisible(false);
        play.setVisible(false);
        disableUI.set(true);
        drawer.close();
        int sortIndex = getAlgorithmIndex();

        timeline.play();

        executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            SortAlgorthm sorter = sortAlgorthms[sortIndex];

            sorter.sort(Rand.getArray(), 0, Rand.max_size - 1);
            stop();
        });
    }

    private void stop() {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    SortingController.updateViews();
                }
            });
            executor.shutdown();
            executor.awaitTermination(100, TimeUnit.MILLISECONDS);
            sort = false;
        } catch (InterruptedException e) {

        } finally {
            if (!executor.isTerminated()) {
                executor.shutdownNow();
            }
            timeline.stop();
            disableUI.set(false);
        }
        if (SortAlgorthm.getSwaps().size()-1 == 0) {
            return;
        }
        sortSlider.setVisible(true);
        playBtn.setVisible(true);
        playBtn.setDisable(false);
        sortSlider.setMax(SortAlgorthm.getSwaps().size()-1);
        sortSlider.setValue(sortSlider.getMax());
        sortSlider.valueProperty().addListener(l -> {
            if (sortSlider.getValue() == sortSlider.getMax() || start) {
                playBtn.setDisable(true);
                play.setVisible(false);
            } else {
                playBtn.setDisable(false);
                play.setVisible(true);
            }
            int[] ints = new int[SortAlgorthm.getSwaps().get(0).size()];
            for (int i = 0; i < SortAlgorthm.getSwaps().get(0).size(); i++) {
                ints[i] = SortAlgorthm.getSwaps().get((int) sortSlider.getValue()).get(i);
            }
            Rand.setRandSet(ints);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    SortingController.updateViews();
                }
            });
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                time.setText("Time Taken: " + (Info.time)+ " ms");
            }
        });
    }

    public static void updateViews() {
        Sorter.getInstance().apply(Rand.getArray(), animationController);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tb1.setToggleGroup(group);
        tb2.setToggleGroup(group);
        tb3.setToggleGroup(group);
        group.selectToggle(tb1);
        tb1.setOnAction(actionEvent -> {
            infoTF.setText(sortAlgorthms[getAlgorithmIndex()].getJava());
        });
        tb2.setOnAction(actionEvent -> {
            infoTF.setText(sortAlgorthms[getAlgorithmIndex()].getPython());
        });
        tb3.setOnAction(actionEvent -> {
            infoTF.setText(sortAlgorthms[getAlgorithmIndex()].getUses());
        });
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("View/animation.fxml"));
        try {
            anchorPane1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        animationController = fxmlLoader.getController();
        AnchorPane.setTopAnchor(anchorPane1, 50.0);
        anchorPane.getChildren().add(anchorPane1);
        algoCB.setItems(algorithms);
        animationController.setPresetValues();
        algoCB.getSelectionModel().select(0);
        infoTF.setText(sortAlgorthms[getAlgorithmIndex()].getJava());
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2000, 250, 10);
        delaySpinner.setValueFactory(valueFactory);
        DELAY_PROPERTY.bind(delaySpinner.valueProperty());

        final ChangeListener<Toggle> listener =
                (ObservableValue<? extends Toggle> observable,
                 Toggle old, Toggle now) -> {
                    if (now == null) {
                        group.selectToggle(old);
                    }
                };
        group.selectedToggleProperty().addListener(listener);
        timeline = new Timeline(new KeyFrame(javafx.util.Duration.millis(delaySpinner.getValue())));
        timeline.setCycleCount(Animation.INDEFINITE);
        startBtn.disableProperty().bind(disableUI);
        enterBtn.disableProperty().bind(disableUI);
        randomBtn.disableProperty().bind(disableUI);
        algoCB.disableProperty().bind(disableUI);
        delaySpinner.disableProperty().bind(disableUI);
        listTF.disableProperty().bind(disableUI);
        compareBtn.disableProperty().bind(disableUI);
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

    private int getAlgorithmIndex() {
        return algoCB.getSelectionModel().getSelectedIndex();
    }

    @FXML
    private void randomBtnOnClick(ActionEvent event) {
        sortSlider.setVisible(false);
        playBtn.setVisible(false);
        play.setVisible(false);
        time.setText("");
        anchorPane.getChildren().remove(anchorPane1);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("View/animation.fxml"));
        AnchorPane anchorPane1 = null;
        try {
            anchorPane1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        animationController = fxmlLoader.getController();
        AnchorPane.setTopAnchor(anchorPane1, 50.0);
        anchorPane.getChildren().add(anchorPane1);
        animationController.setPresetValues();
    }

    @FXML
    private void algoCBOnClick(ActionEvent event) {
        if (group.getSelectedToggle().equals(tb1)) {
            infoTF.setText(sortAlgorthms[getAlgorithmIndex()].getJava());
        } else if (group.getSelectedToggle().equals(tb2)) {
            infoTF.setText(sortAlgorthms[getAlgorithmIndex()].getPython());
        } else {
            infoTF.setText(sortAlgorthms[getAlgorithmIndex()].getUses());
        }
    }

    @FXML
    private void playBtnOnClick(ActionEvent event) {
        playBtn.setDisable(true);
        play.setVisible(false);
        start = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                disableUI.set(true);
                double i = sortSlider.getValue();
                while (i < sortSlider.getMax()) {
                    sortSlider.setValue(i + 1);
                    i += 0.1;
                    try {
                        Thread.sleep(DELAY_PROPERTY.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                start = false;
                disableUI.set(false);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void compareBtnOnClick(ActionEvent event) throws IOException {
        delaySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2000, 0, 10));
        Parent root = FXMLLoader.load(Main.class.getResource("View/comparison.fxml"));
        Stage stage = (Stage) compareBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}