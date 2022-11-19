package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;
import sample.Model.CaesarCipher;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CaesarController implements Initializable {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField alphaTF;

    @FXML
    private JFXTextField stringTF;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton runBtn;

    @FXML
    private AnchorPane pane2;

    @FXML
    private Label fnLabel;

    @FXML
    private JFXButton copyBtn;

    @FXML
    private AnchorPane iconPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXButton hamburgerBtn;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXButton backBtn;

    private CaesarCipher caesarCipher;
    private ArrayList<Node> list;

    @FXML
    private void copyBtnOnClick(ActionEvent event) {
        String s = "";
        for (Node n : list) {
            s += ((Label) n).getText();
        }
        ClipboardContent content = new ClipboardContent();
        content.putString(s);
        Clipboard.getSystemClipboard().setContent(content);
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
        if (!stringTF.getText().matches("[a-zA-Z]+") || !alphaTF.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Input In TF Was Wrong");
            alert.setHeaderText("Incorrect Input");
            alert.setTitle("Incorrect Input");
            alert.show();
            if (!alphaTF.getText().matches("\\d+")) {
                alphaTF.clear();
            }
            if (!stringTF.getText().matches("[a-zA-Z]+")) {
                stringTF.clear();
            }
        } else {
            caesarCipher = new CaesarCipher(Integer.parseInt(alphaTF.getText()));
            pane.getChildren().clear();
            runBtn.setDisable(true);
            backBtn.setDisable(true);
            copyBtn.setDisable(true);
            hamburgerBtn.setDisable(true);
            alphaTF.setDisable(true);
            stringTF.setDisable(true);
            drawer.close();
            int length = stringTF.getText().length();
            if (length >= 10) {
                int count = 0;
                for (String s : stringTF.getText().split("")) {
                    Label label = new Label(s);
                    label.setFont(new Font("Segoe UI Semilight", 1000/length));
                    label.setStyle("-fx-border-color: #eee; -fx-text-fill: #eee;-fx-alignment: center");
                    label.setPrefSize(1000/length, 1000/length);
                    label.setLayoutX(1000/length * count++);
                    pane.getChildren().add(label);
                }
            } else {
                int count = 0;
                for (String s : stringTF.getText().split("")) {
                    Label label = new Label(s);
                    label.setFont(new Font("Segoe UI Semilight", 100));
                    label.setStyle("-fx-border-color: #eee; -fx-text-fill: #eee;-fx-alignment: center");
                    label.setPrefSize(100, 100);
                    label.setLayoutX((1000 - length * 100)/2 + 100 * count++);
                    pane.getChildren().add(label);
                }
            }
            Text text = new Text(alphaTF.getText());
            text.setFont(new Font("System", 100));
            text.setFill(Paint.valueOf("#eee"));
            mainPane.getChildren().add(text);
            fnLabel.setStyle("-fx-text-fill: #eee;-fx-alignment: center");
            fnLabel.setText("ƒ(\uD835\uDC65) = α + \uD835\uDC65");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    TranslateTransition t = new TranslateTransition(Duration.millis(1000), text);
                    t.setFromX(93);
                    t.setFromY(520);
                    t.setToX(530);
                    t.setToY(140);
                    t.play();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            fnLabel.setText("ƒ(\uD835\uDC65) = " + text.getText() + " + \uD835\uDC65");
                            text.setVisible(false);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    list = new ArrayList<>();
                    ArrayList<Node> list2 = new ArrayList<>();
                    for (Node node : pane.getChildren()) {
                        list.add(node);
                    }
                    for (Node l : list) {
                        Label label = (Label) l;
                        String s = caesarCipher.startEncrypt(label.getText().toLowerCase());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                label.setStyle("-fx-border-color: #eee; -fx-text-fill: #eee;-fx-alignment: center;-fx-background-color: #980d60");
                                Label label1 = new Label(String.valueOf(CaesarCipher.letters.indexOf(label.getText().toLowerCase())));
                                label1.setFont(new Font("Segoe UI Semilight", 18));
                                label1.setStyle("-fx-text-fill: #eee; -fx-alignment: center");
                                label1.setPrefWidth(label.getWidth());
                                label1.setLayoutX(label.getLayoutX());
                                label1.setLayoutY(label.getLayoutY() + 150);
                                pane.getChildren().add(label1);
                                list2.add(label1);
                            }
                        });
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int j = CaesarCipher.letters.indexOf(label.getText().toLowerCase());
                        for (int i = j +  1; i < j + Integer.parseInt(alphaTF.getText()); i++) {
                            int finalI = i;
                            Platform.runLater(() -> {
                                label.setText(CaesarCipher.letters.get(finalI % 26));
                            });
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                label.setStyle("-fx-border-color: #eee; -fx-text-fill: #eee;-fx-alignment: center;-fx-background-color: #0b0b91");
                                label.setText(s);
                                pane.getChildren().removeIf(node -> list2.contains(node));
                            }
                        });
                    }
                    runBtn.setDisable(false);
                    backBtn.setDisable(false);
                    copyBtn.setDisable(false);
                    hamburgerBtn.setDisable(false);
                    alphaTF.setDisable(false);
                    stringTF.setDisable(false);
                }
            });
            thread.start();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AnchorPane pane = FXMLLoader.load(Main.class.getResource("View/drawer.fxml"));
            drawer.setSidePane(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/hashing.fxml"));
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
