package sample.Model;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import sample.Controller.ComparisonController;
import sample.Controller.SortingController;

public class Compare implements Comparable {
    private int value;
    private Color color;
    public Compare(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int compare(Comparable number) {
        int i;
        Compare compare = (Compare) number;
        if (SortingController.sort) {
            compare.setColor(Color.web("#a07617"));
            this.setColor(Color.web("#a07617"));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    SortingController.updateViews();
                }
            });
            try {
                Thread.sleep(Math.max(SortingController.DELAY_PROPERTY.get(), ComparisonController.DELAY_PROPERTY.get())/2);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        if (value < compare.value) {
            i = -1;
        } else if (value > compare.value) {
            i = 1;
        } else {
            i = 0;
        }
        if (SortingController.sort) {
            try {
                Thread.sleep(Math.max(SortingController.DELAY_PROPERTY.get(), ComparisonController.DELAY_PROPERTY.get())/2);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            compare.setColor(Color.web("#3073b4"));
            this.setColor(Color.web("#3073b4"));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    SortingController.updateViews();
                }
            });
        }
        return i;
    }
}
