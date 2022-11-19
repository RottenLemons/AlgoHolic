package sample.Model;

public class StrategyFactory {

    public static PathfindingStrategy getPathfindingStrategy(PathfindingStrategy.Algorithms algorithmStrategy, HeuristicStrategy heuristicStrategy) {
        switch (algorithmStrategy) {
            case Dijkstra:
                return new DijkstraStrategy();
            case AStar:
                return new AStarStrategy(heuristicStrategy);
            case DFS:
                return new DFSStrategy();
            default:
                throw new IllegalArgumentException("Pathfinding algorithm not found!");
        }
    }


    public static HeuristicStrategy getHeuristicStrategy(AStarStrategy.Heuristic strategy) {
        switch (strategy) {
            case Diagonal:
                return new DiagonalStrategy();
            case Manhattan:
                return new ManhattanStrategy();
            case Euclidean:
                return new EuclideanStrategy();
            default:
                throw new IllegalArgumentException("Heuristic strategy not found!");
        }
    }
}