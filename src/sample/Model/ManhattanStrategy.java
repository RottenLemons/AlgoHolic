package sample.Model;

public class ManhattanStrategy extends HeuristicStrategy {
    @Override
    public double cmptHeuristic(Tile root, Tile target) {
        double dx = Math.abs(root.getX() - target.getX());
        double dy = Math.abs(root.getY() - target.getY());

        return 1.0 * (dx + dy);
    }
}
