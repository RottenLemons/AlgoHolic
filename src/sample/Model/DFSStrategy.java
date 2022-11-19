package sample.Model;

import sample.Controller.InfoController;
import sample.Controller.PathfindingController;

import java.util.*;

public class DFSStrategy extends PathfindingStrategy {
    @Override
    protected void runPathfinder(Grid model, ArrayList<Tile> path) {
        Tile start = model.getStart();
        Tile end = model.getEnd();

        HashMap<Tile, Tile> parents = new HashMap();
        HashMap<Tile, Integer> weights = new HashMap();
        path.clear();

        executeDFS(model, parents, weights);

        if (weights.get(end) != Integer.MAX_VALUE) {
            do{
                path.add(0, end);
                end = parents.get(end);
            } while (end != start);
        }
    }

    private void executeDFS(Grid model, HashMap<Tile, Tile> parents, HashMap<Tile, Integer> weights) {
        Tile start = model.getStart();

        Set<Tile> unvisited = new HashSet<>();
        Stack<Tile> toVisit = new Stack<>();

        model.getTiles().stream().filter((tile) -> !(tile.isWall())).map((tile) -> {
            weights.put(tile, Integer.MAX_VALUE);
            return tile;
        }).forEachOrdered((tile) -> {
            parents.put(tile, null);
        });
        weights.put(start, 0);
        toVisit.add(model.getStart());
        while (!toVisit.isEmpty()) {
            Tile minWeightTile = toVisit.peek();
            toVisit.remove(minWeightTile);
            if (weights.get(minWeightTile) == Integer.MAX_VALUE) {
                break;
            }
            painter.drawTile(minWeightTile, model.getEnd(), model.getStart(), Tile.Types.HIGHLIGHT, Math.max(PathfindingController.DELAY_PROPERTY.get(), Math.max(InfoController.DELAY_PROPERTY.get(), Math.max(InfoController.DELAY_PROPERTY2.get(), InfoController.DELAY_PROPERTY3.get()))));
            unvisited.add(minWeightTile);
            if (minWeightTile == model.getEnd()) break;
            ArrayList<Tile> neighbours = model.getTileNeighbors(minWeightTile);
            for (Tile tile: neighbours) {
                if (!unvisited.contains(tile)) {
                    int newWeight = 1 + weights.get(minWeightTile);
                    if (weights.get(tile) > newWeight) {
                        weights.put(tile, newWeight);
                        parents.put(tile, minWeightTile);
                        toVisit.add(tile);
                    }
                }
            }
            painter.drawTile(minWeightTile, model.getEnd(), model.getStart(), Tile.Types.VISITED, Math.max(PathfindingController.DELAY_PROPERTY.get(), Math.max(InfoController.DELAY_PROPERTY.get(), Math.max(InfoController.DELAY_PROPERTY2.get(), InfoController.DELAY_PROPERTY3.get()))));
        }
    }
}
