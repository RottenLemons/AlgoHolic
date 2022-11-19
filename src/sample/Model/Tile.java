package sample.Model;

import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class Tile extends Observable {

    public enum Types {
        START,
        END,
        WALL,
        EMPTY,
        PATH,
        HIGHLIGHT,
        VISITED,
    }

    private final Map<Types, Color> typeMap;


    private final StackPane pane;

    private final int x;
    private final int y;

    private final Rectangle rectangle;

    private Types type;
    private final double tileGap = 0;
    private final int size;

    public Tile(int x, int y, int size) {
        pane = new StackPane();

        typeMap = new HashMap<>();
        typeMap.put(Types.START, Color.BLUE.desaturate());
        typeMap.put(Types.END, Color.LIGHTCORAL);
        typeMap.put(Types.EMPTY, Color.web("#252525"));
        typeMap.put(Types.WALL, Color.WHITE.desaturate());
        typeMap.put(Types.PATH, Color.LIGHTBLUE.desaturate());
        typeMap.put(Types.HIGHLIGHT, Color.RED.desaturate());
        typeMap.put(Types.VISITED, Color.LIGHTGREEN.desaturate());

        this.x = x;
        this.y = y;

        this.type = Types.EMPTY;
        this.size = size;

        this.rectangle = new Rectangle(size - tileGap, size - tileGap);
        this.rectangle.setFill(Color.web("#252525"));
        this.rectangle.setStroke(Color.web("#404040"));

        pane.getChildren().add(rectangle);
        pane.setTranslateX(x * size);
        pane.setTranslateY(y * size);

        setEvents();
    }

    public StackPane getStackPane() {
        return this.pane;
    }

    public void setAttributes(Types type) {
        Color color;
        color = this.typeMap.get(type);

        this.rectangle.setFill(color);
        this.type = type;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void clearTile() {
        this.setAttributes(Types.EMPTY);
    }

    public Types getType() {
        return this.type;
    }

    public boolean isWall() {
        return (getType() == Types.WALL);
    }

    private void setEvents() {
        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            setChanged();
            notifyObservers();
        });
    }

    @Override
    public String toString() {
        return String.format("%s - (%d,%d)", this.type, this.x, this.y);
    }
}
