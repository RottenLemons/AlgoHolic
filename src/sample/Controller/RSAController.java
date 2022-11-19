package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;
import sample.Model.RSA;

import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class RSAController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField alphaTF;

    @FXML
    private JFXTextField betaTF;

    @FXML
    private JFXTextField intTF;

    @FXML
    private JFXButton runBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private Label n1;

    @FXML
    private Label n2;

    @FXML
    private Label ph1;

    @FXML
    private Label e2;

    @FXML
    private Label ph2;

    @FXML
    private Label e1;

    @FXML
    private AnchorPane iconPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXButton hamburgerBtn;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label d;

    @FXML
    private Label c;

    private static RSA rsa = null;
    private static boolean done = false;

    @FXML
    private void backBtnOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("View/hashing.fxml"));
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
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
    private void runBtnOnClick(ActionEvent event) throws IOException {
        if ((alphaTF.getText().matches("\\d{1,3}") && Integer.parseInt(alphaTF.getText()) <= 53) && (betaTF.getText().matches("\\d{1,3}") && Integer.parseInt(betaTF.getText()) <= 53) && intTF.getText().matches("\\d+")) {
            try {
                rsa = new RSA(Integer.parseInt(alphaTF.getText()), Integer.parseInt(betaTF.getText()));
            } catch (InputMismatchException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Input In Alpha Beta Is Incorrect");
                alert.setHeaderText("Incorrect AlphaBeta");
                alert.setTitle("Incorrect AlphaBeta");
                alert.show();
                alphaTF.clear();
                betaTF.clear();
                return;
            }
            if (Integer.parseInt(intTF.getText()) % rsa.getAlpha() == 0 || Integer.parseInt(intTF.getText()) % rsa.getBeta() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Input In IntegerTF Not Relatively Prime");
                alert.setHeaderText("Incorrect Message");
                alert.setTitle("Incorrect Message");
                alert.show();
                intTF.clear();
                return;
            }
            String s = rsa.startEncrypt(intTF.getText());
            if (done) {
                done = false;
                Parent root = FXMLLoader.load(Main.class.getResource("View/rsa.fxml"));
                Stage stage = (Stage) runBtn.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
            runBtn.setDisable(true);
            alphaTF.setDisable(true);
            betaTF.setDisable(true);
            hamburgerBtn.setDisable(true);
            intTF.setDisable(true);
            backBtn.setDisable(true);
            n1.setVisible(true);
            ph1.setVisible(true);
            drawer.close();
            e1.setVisible(true);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            n2.setVisible(true);
                            n2.setText(n2.getText() + rsa.getN());
                        }
                    });
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {

                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ph2.setVisible(true);
                            ph2.setText(ph2.getText() + rsa.getZ());
                        }
                    });
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {

                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            e2.setVisible(true);
                            e2.setText(e2.getText() + rsa.getE());
                        }
                    });

                    try {
                        Thread.sleep(3800);
                    } catch (InterruptedException e) {

                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            d.setVisible(true);
                            d.setText("d=(1+" + rsa.getI() + " * φ(n))/" + rsa.getE() + " if integer, else 0");
                        }
                    });

                    try {
                        Thread.sleep(3500);
                    } catch (InterruptedException e) {

                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            d.setText("d=" + rsa.getD());
                        }
                    });
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {

                    }
                    FadeTransition f1 = new FadeTransition(Duration.millis(1000), n1);
                    f1.setFromValue(1);
                    f1.setToValue(0);
                    FadeTransition f2 = new FadeTransition(Duration.millis(1000), ph1);
                    f2.setFromValue(1);
                    f2.setToValue(0);
                    FadeTransition f3 = new FadeTransition(Duration.millis(1000), ph2);
                    f3.setFromValue(1);
                    f3.setToValue(0);
                    FadeTransition f4 = new FadeTransition(Duration.millis(1000), e1);
                    f4.setFromValue(1);
                    f4.setToValue(0);
                    FadeTransition f5 = new FadeTransition(Duration.millis(1000), d);
                    f5.setFromValue(1);
                    f5.setToValue(0);
                    ParallelTransition p = new ParallelTransition();
                    p.getChildren().addAll(f1, f2, f3, f4, f5);
                    p.play();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Label l1 = new Label(n2.getText());
                    Label l2 = new Label(e2.getText());
                    Platform.runLater(() -> {
                        pane.getChildren().addAll(l1, l2);
                    });
                    l1.setFont(new Font("Segoe UI Semilight", 24));
                    l2.setFont(new Font("Segoe UI Semilight", 24));
                    l1.setStyle("-fx-text-fill: #eee;-fx-alignment: center");
                    l2.setStyle("-fx-text-fill: #eee;-fx-alignment: center");
                    TranslateTransition t1 = new TranslateTransition(Duration.millis(1000), n2);
                    t1.setToY(-53);
                    t1.setToX(835);
                    TranslateTransition t2 = new TranslateTransition(Duration.millis(1000), e2);
                    t2.setToY(-159);
                    t2.setToX(835);
                    TranslateTransition t3 = new TranslateTransition(Duration.millis(1000), l1);
                    t3.setFromX(179);
                    t3.setFromY(276);
                    t3.setToX(430 - l1.getPrefWidth());
                    t3.setToY(75);
                    TranslateTransition t4 = new TranslateTransition(Duration.millis(1000), l2);
                    t4.setFromX(14);
                    t4.setFromY(435);
                    t4.setToX(533);
                    t4.setToY(75);
                    ParallelTransition p2 = new ParallelTransition();
                    p2.getChildren().addAll(t1, t2, t3, t4);
                    p2.play();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            c.setVisible(true);
                            c.setText(c.getText() + rsa.getInput() + "^" + rsa.getE() + " mod " + rsa.getN());
                        }
                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Label l3 = new Label();
                    l3.setFont(new Font("Segoe UI Semilight", 24));
                    l3.setStyle("-fx-text-fill: #eee;-fx-alignment: center");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            c.setStyle("-fx-alignment: center");
                            c.setText("c=" + s);
                        }
                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            pane.getChildren().add(l3);
                            l3.setText(c.getText());
                        }
                    });
                    TranslateTransition t5 = new TranslateTransition(Duration.millis(1000), l3);
                    t5.setFromX(782);
                    t5.setFromY(393);
                    t5.setToX(533);
                    t5.setToY(136);
                    TranslateTransition t6 = new TranslateTransition(Duration.millis(1000), c);
                    t6.setToX(-750);
                    t6.setToY(-106);
                    ParallelTransition p3 = new ParallelTransition();
                    p3.getChildren().addAll(t5, t6);
                    p3.play();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Label l4 = new Label();
                    l4.setFont(new Font("Segoe UI Semilight", 24));
                    l4.setStyle("-fx-text-fill: #eee;-fx-alignment: center");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            pane.getChildren().add(l4);
                            l4.setText("d=" + rsa.getD());
                            l4.setLayoutX(c.getLayoutX()-680);
                            l4.setLayoutY(260);
                        }
                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Label l5 = new Label();
                    l5.setFont(new Font("Segoe UI Semilight", 24));
                    l5.setStyle("-fx-text-fill: #eee;-fx-alignment: center");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            pane.getChildren().add(l5);
                            l5.setText("Message=" + s + "^" + rsa.getD() + " mod "  + rsa.getN());
                            l5.setLayoutX(c.getLayoutX()-680);
                            l5.setLayoutY(329);
                        }
                    });
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            l5.setText("Message=" + rsa.startDecrypt());
                        }
                    });
                    runBtn.setDisable(false);
                    alphaTF.setDisable(false);
                    hamburgerBtn.setDisable(false);
                    betaTF.setDisable(false);
                    intTF.setDisable(false);
                    backBtn.setDisable(false);
                }
            });
            thread.start();
            done = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Input In TextFields Were Wrong");
            alert.setHeaderText("Incorrect Input");
            alert.setTitle("Incorrect Input");
            alert.show();
            if (!alphaTF.getText().matches("\\d+") || Integer.parseInt(alphaTF.getText()) > 53) {
                alphaTF.clear();
            }
            if (!betaTF.getText().matches("\\d+") || Integer.parseInt(betaTF.getText()) > 53) {
                betaTF.clear();
            }
            if (!intTF.getText().matches("\\d+")) {
                intTF.clear();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (rsa != null) {
            alphaTF.setText(rsa.getAlpha() + "");
            betaTF.setText(rsa.getBeta() + "");
            intTF.setText(rsa.getInput() + "");

            try {
                runBtnOnClick(new ActionEvent());
            } catch (IOException e) {
            }
        }
        try {
            AnchorPane pane = FXMLLoader.load(Main.class.getResource("View/drawer.fxml"));
            drawer.setSidePane(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
