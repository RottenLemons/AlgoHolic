package sample.Model;

public class DiagonalStrategy extends HeuristicStrategy{
    @Override
    public double cmptHeuristic(Tile root, Tile target) {
        double D1 = 1.0;
        double D2 = 1.0;

        double dx = Math.abs(root.getX() - target.getX());
        double dy = Math.abs(root.getY() - target.getY());

        return D1 * (dx + dy) + (D2 - 2 * D1) * Math.min(dx, dy);
    }
}
