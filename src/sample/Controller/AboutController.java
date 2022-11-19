package sample.Controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label label2;

    @FXML
    private Label label;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXComboBox<String> languageCB;

    private static final Random RANDOM = new Random();
    String language = "en";
    String country = "US";
    Locale currentLocale;
    ResourceBundle message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languageCB.setItems(FXCollections.observableArrayList("English", "German", "French"));
        languageCB.getSelectionModel().selectFirst();
        mainPane.setFocusTraversable(true);
        mainPane.setOnKeyPressed((KeyEvent ke) -> {
            createLetter(ke.getText());
            ke.consume();
        });
    }

    private void createLetter(String c) {
        final Font font = new Font(Font.getDefault().getFamily(), 150);
        final Text letter = new Text(c);
        letter.setFill(Color.valueOf("#eee"));
        letter.setFont(font);
        letter.setTextOrigin(VPos.TOP);
        letter.setTranslateX((pane.getWidth() -
                letter.getBoundsInLocal().getWidth()) / 2);
        letter.setTranslateY((pane.getHeight() -
                letter.getBoundsInLocal().getHeight()) / 2);
        pane.getChildren().add(letter);
        final Interpolator interp = Interpolator.SPLINE(0.295, 0.800,
                0.305, 1.000);
        final Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(3), (ActionEvent event) -> {
                    pane.getChildren().remove(letter);
                },
                        new KeyValue(letter.translateXProperty(),
                                getRandom(0.0f, pane.getWidth() -
                                        letter.getBoundsInLocal().getWidth()),
                                interp),
                        new KeyValue(letter.translateYProperty(),
                                getRandom(0.0f, pane.getHeight() -
                                        letter.getBoundsInLocal().getHeight()),
                                interp),
                        new KeyValue(letter.opacityProperty(), 0f)));
        timeline.play();
    }

    private static float getRandom(double min, double max) {
        return (float) (RANDOM.nextFloat() * (max - min) + min);
    }

    @FXML
    private void languageCBOnClick(ActionEvent event) {
        if (languageCB.getSelectionModel().getSelectedIndex() == 0) {
            language = "en";
            country = "US";
        } else if (languageCB.getSelectionModel().getSelectedIndex() == 1) {
            language = "de";
            country = "DE";
        } else {
            language = "fr";
            country = "FR";
        }

        currentLocale = new Locale(language, country);
        message = ResourceBundle.getBundle("sample.Controller.MessagesBundle", currentLocale);
        label.setText(message.getString("Version") + ":");
        label2.setText(message.getString("CreatedBy") + ":");
    }
}
