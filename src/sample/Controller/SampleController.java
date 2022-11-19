package sample.Controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable {
    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane iconPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label welcome;

    @FXML
    private ImageView image;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawer.open();
        welcome.setStyle("-fx-alignment: center");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] fonts = {"Gigi", "Century Gothic", "Stencil", "Monotype Corsiva", "Elephant", "Arial Rounded MT Bold", "Mistral", "Eras Light ITC", "Britannic Bold", "Colonna MT", "Bauhaus 93", "Berlin Sans FB", "Book Antiqua", "Segoe UI Semibold"};
                int i = 0;
                while (true) {
                    String font = fonts[i%fonts.length];
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            welcome.setFont(new Font(font, 89));
                        }
                    });
                    i++;
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        thread.start();
        image.setImage(new Image("file:src/sample/View/logo.png"));
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
}
