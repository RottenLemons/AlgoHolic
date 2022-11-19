package sample.Model;

import sample.Controller.InfoController;
import sample.Controller.PathfindingController;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Painter {
    private static final Painter INSTANCE = new Painter();
    private final Executor executor;

    private Painter() {
        executor = Executors.newSingleThreadExecutor();
    }

    public static Painter getInstance() {
        return INSTANCE;
    }

    public void drawPath(ArrayList<Tile> path, Grid model) {
        this.executor.execute(() -> {
            path.stream().filter((tile) -> !(tile == model.getEnd() || tile == model.getStart())).map((tile) -> {
                tile.setAttributes(Tile.Types.PATH);
                return tile;
            }).forEachOrdered((item) -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                }
            });
            PathfindingController.disableUI.set(false);
            InfoController.disableUI.set(false);
        });
    }

    public void drawTile(Tile tile, Tile end, Tile start, Tile.Types type, long sleep) {
        this.executor.execute(()-> {
            if (tile != end && tile != start)
                tile.setAttributes(type);

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
            }
        });
    }

    public void clearPath(Grid model) {
        this.executor.execute(()-> {
            Tile tile;
            for(int y = 0; y < model.getYSize(); y++) {
                for(int x = 0; x < model.getXSize(); x++) {
                    tile = model.getGrid()[x][y];
                    if(tile.getType() == Tile.Types.PATH || tile.getType() == Tile.Types.VISITED) {
                        tile.setAttributes(Tile.Types.EMPTY);
                    }
                }
            }
        });
    }
}
