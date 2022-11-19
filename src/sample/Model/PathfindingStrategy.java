package sample.Model;

import java.util.ArrayList;

public abstract class PathfindingStrategy {

    protected final Painter painter;

    public enum Algorithms{
        Dijkstra,
        AStar,
        DFS
    }

    public final void algorithm(Grid grid, ArrayList<Tile> path) {
        this.runPathfinder(grid, path);
        this.painter.drawPath(path, grid);
    }

    public PathfindingStrategy() {
        this.painter = Painter.getInstance();
    }

    protected abstract void runPathfinder(Grid model, ArrayList<Tile> path);
}
