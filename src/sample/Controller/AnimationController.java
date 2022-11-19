package sample.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import sample.Model.Compare;
import sample.Model.Rand;
import sample.Model.Sort;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class AnimationController extends AnchorPane implements Sort, Initializable {
    @FXML
    private GridPane grid;
    private double y = 491.3332926432292;

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rand.setRandSet(null);
    }

    public void setPresetValues() {
        barMaker();
        IntStream.range(0, Rand.max_size).forEachOrdered(index -> {
            Label label = new Label();
            label.setFont(new Font("Segoe UI Semilight", 15));
            grid.add(label, index, 1);
            label.setText(String.valueOf(Rand.getArray()[index].getValue()));
        });
    }

    private void barMaker() {
        grid.getChildren().clear();
        IntStream.range(0, Rand.max_size).forEachOrdered((int index) -> {
            Compare compareValue = Rand.getArray()[index];
            double height = calculateHeight(compareValue.getValue(), y);

            Bar rect = new Bar();
            rect.getStyleClass().add("rect");
            rect.setPrefHeight(height);
            rect.setMaxHeight(height);
            rect.setPrefWidth((grid.getPrefWidth() - 6 * Rand.max_size)/Rand.max_size);
            rect.setMaxWidth((grid.getPrefWidth() - 6 * Rand.max_size)/Rand.max_size);
            grid.add(rect, index, 0);

        });
    }

    private double calculateHeight(int value, double y) {
        double x1 = Rand.getMaxValue(), y2 = y;

        double height = y2/x1 * value;

        return Math.round(height);
    }

    private void animate(Bar rect, double height, Color color) {
        final Timeline tl = new Timeline();
        tl.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(Math.max(Math.max(SortingController.DELAY_PROPERTY.get(), ComparisonController.DELAY_PROPERTY.get()), 20)),
                        new KeyValue(rect.colorProperty, color),
                        new KeyValue(rect.prefHeightProperty(), height),
                        new KeyValue(rect.maxHeightProperty(), height)));
        tl.play();
    }

    @Override
    public Object apply(Object object) {
        Compare[] objectArray = (Compare[]) object;

        for (int indexPos = 0; indexPos < objectArray.length; indexPos++) {

            Compare compareValue = objectArray[indexPos];
            Color color = compareValue.getColor();
            int value = compareValue.getValue();

            Bar rect = (Bar) grid.getChildren().get(indexPos);

            Label label = (Label) grid.getChildren().get(indexPos + Rand.max_size);
            final Timeline tl = new Timeline();
            tl.getKeyFrames().addAll(new KeyFrame(Duration.millis(Math.max(Math.max(SortingController.DELAY_PROPERTY.get(), ComparisonController.DELAY_PROPERTY.get()), 20)), new KeyValue(rect.colorProperty, color)));
            tl.play();
            double height = calculateHeight(value, y);
            if(rect.getHeight() != height) {
                animate(rect, calculateHeight(value, y), color);
            }
            label.setText(String.valueOf(value));

        }

        return null;
    }

    private final class Bar extends Region {
        public final SimpleObjectProperty<Color> colorProperty = new SimpleObjectProperty<>(Color.web("#3073b4"));

        public Bar() {
            colorProperty.addListener(this::colorPropertyAction);
        }

        public void colorPropertyAction(Observable observable) {
            if (colorProperty.get() == null) {
                return;
            }
            String color = "#".concat(Integer.toHexString(colorProperty.get().hashCode()));
            setStyle("-fx-background-color: " + color + ";");
        }
    }
}
