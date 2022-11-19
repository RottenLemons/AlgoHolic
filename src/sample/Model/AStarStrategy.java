package sample.Model;

import sample.Controller.InfoController;
import sample.Controller.PathfindingController;

import java.util.*;

public class AStarStrategy extends PathfindingStrategy {
    private final HeuristicStrategy heuristic;

    public enum Heuristic {
        Diagonal,
        Manhattan,
        Euclidean
    }


    public AStarStrategy(HeuristicStrategy heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    protected void runPathfinder(Grid model, ArrayList<Tile> path) {
        ArrayList<NewTile> tiles = new ArrayList<>();
        NewTile start = null;
        NewTile end = null;

        for (Tile tile : model.getTiles()) {
            NewTile newTile = new NewTile(tile);

            if (model.getStart() == tile) {
                start = newTile;
            } else if (model.getEnd() == tile) {
                end = newTile;
            }

            tiles.add(newTile);
        }

        tiles.forEach((newTile -> {
            newTile.setNeighbors(tiles, model);
        }));

        executeAStar(start, end, this.heuristic);
        addPath(tiles, path, model.getEnd());
    }

    private void executeAStar(NewTile start, NewTile end, HeuristicStrategy heuristic) {
        NewTile current = start;
        current.setLocalGoal(0.0);
        current.setGlobalGoal(heuristic.cmptHeuristic(start.getTile(), end.getTile()));

        Queue<NewTile> unvisited = new PriorityQueue<>();
        unvisited.add(current);

        while (!unvisited.isEmpty()) {
            if (current == end) {
                break;
            }

            while (!unvisited.isEmpty() && unvisited.peek().isVisited) {
                unvisited.poll();
            }

            if (unvisited.isEmpty()) {
                break;
            }

            current = unvisited.poll();
            painter.drawTile(current.getTile(), end.getTile(), start.getTile(), Tile.Types.HIGHLIGHT, Math.max(PathfindingController.DELAY_PROPERTY.get(), Math.max(InfoController.DELAY_PROPERTY.get(), Math.max(InfoController.DELAY_PROPERTY2.get(), InfoController.DELAY_PROPERTY3.get()))));
            current.setVisited(true);

            for (NewTile neighbour:current.getNeighbors()) {
                if (!neighbour.isVisited && !neighbour.isWall()) {
                    unvisited.add(neighbour);
                }
                double goal = current.getLocalGoal() + 1;
                if (goal< neighbour.getLocalGoal()) {
                    neighbour.setParent(current);
                    neighbour.setLocalGoal(goal);
                    neighbour.setGlobalGoal(neighbour.getLocalGoal() + heuristic.cmptHeuristic(neighbour.getTile(), end.getTile()));
                }
            }
            painter.drawTile(current.getTile(), end.getTile(), start.getTile(), Tile.Types.VISITED, Math.max(PathfindingController.DELAY_PROPERTY.get(), Math.max(InfoController.DELAY_PROPERTY.get(), Math.max(InfoController.DELAY_PROPERTY2.get(), InfoController.DELAY_PROPERTY3.get()))));
        }
    }

    private List<Tile> addPath(List<NewTile> tiles, List<Tile> path, Tile end)
    {
        NewTile parent = null;

        for (NewTile newTile: tiles) {
            if (newTile.getTile() == end) {
                parent = newTile;
                break;
            }
        }

        while (parent != null) {
            path.add(parent.getTile());
            parent = parent.getParent();
        }

        path.remove(path.size() - 1);
        Collections.reverse(path);
        return path;
    }

    private static class NewTile implements java.lang.Comparable<NewTile> {
        private final Tile tile;
        private final ArrayList<NewTile> neighbors;
        private NewTile parent;
        private boolean isVisited;
        private double globalGoal;
        private double localGoal;

        public NewTile(Tile tile) {
            this.tile = tile;
            this.parent = null;
            this.neighbors = new ArrayList<>();
            this.isVisited = false;
            this.globalGoal = Double.MAX_VALUE;
            this.localGoal = Double.MAX_VALUE;
        }

        public double getGlobalGoal() {
            return globalGoal;
        }

        public void setGlobalGoal(double globalGoal) {
            this.globalGoal = globalGoal;
        }

        public double getLocalGoal() {
            return localGoal;
        }

        public void setLocalGoal(double localGoal) {
            this.localGoal = localGoal;
        }

        public void setVisited(boolean visited) {
            isVisited = visited;
        }

        public ArrayList<NewTile> getNeighbors() {
            return neighbors;
        }

        public void setNeighbors(ArrayList<NewTile> nodes, Grid model) {
            ArrayList<Tile> tileNeighbors = model.getTileNeighbors(this.getTile());
            for (NewTile node : nodes) {
                if(tileNeighbors.contains(node.getTile())) {
                    this.neighbors.add(node);
                }
            }
        }

        public Tile getTile() {
            return tile;
        }

        public NewTile getParent() {
            return parent;
        }

        public void setParent(NewTile parent) {
            this.parent = parent;
        }

        public boolean isWall() {
            return this.tile.isWall();
        }

        @Override
        public int compareTo(NewTile o) {
            if (this.getGlobalGoal() < o.getGlobalGoal()) {
                return -1;
            } else if (this.getGlobalGoal() > o.getGlobalGoal()) {
                return 1;
            }

            return 0;
        }
    }
}