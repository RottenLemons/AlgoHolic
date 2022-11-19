package sample.Model;

import java.util.*;

public class Grid extends Observable implements Observer {

    private int x_size;
    private int y_size;

    private Tile[][] grid;
    public Tile start, end;

    private Tile.Types clickType;

    private final Painter painter;

    public Grid() {
        this.start = null;
        this.end = null;
        this.clickType = Tile.Types.START;
        painter = Painter.getInstance();
    }

    public void executePathfinding(PathfindingStrategy pathfindingStrategy) throws InterruptedException {
        this.painter.clearPath(this);

        ArrayList<Tile> path = new ArrayList<>();

        pathfindingStrategy.algorithm(this, path);
    }

    public void gridInit(int x_tiles, int y_tiles, int tile_size) {
        this.x_size = x_tiles;
        this.y_size = y_tiles;
        this.grid = new Tile[x_tiles][y_tiles];

        for(int y = 0; y < y_tiles; y++) {
            for(int x = 0; x < x_tiles; x++) {
                Tile tile = new Tile(x, y, tile_size);
                tile.addObserver(this);
                grid[x][y] = tile;
            }
        }
    }

    public Tile[][] getGrid() {
        return this.grid;
    }

    public boolean isReady() {
        return !(start == null || end == null);
    }


    public ArrayList<Tile> getTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();

        for(int y = 0; y < this.y_size; y++) {
            for(int x = 0; x < this.x_size; x++) {
                tiles.add(grid[x][y]);
            }
        }

        return tiles;
    }

    public void clearGrid() {
        for(int y = 0; y < this.y_size; y++) {
            for(int x = 0; x < this.x_size; x++) {
                grid[x][y].setAttributes(Tile.Types.EMPTY);
            }
        }

        this.start = null;
        this.end = null;
    }

    public void clearGrid2() {
        for(int y = 0; y < this.y_size; y++) {
            for(int x = 0; x < this.x_size; x++) {
                if (!grid[x][y].getType().equals(Tile.Types.WALL)) {
                    grid[x][y].setAttributes(Tile.Types.EMPTY);
                }
            }
        }

        this.start = null;
        this.end = null;
    }

    public ArrayList<Tile> getTileNeighbors(Tile tile) {
        ArrayList<Tile> neighbors = new ArrayList<>();

        neighbors.add((tile.getY() - 1 >= 0) ? grid[tile.getX()][tile.getY() - 1] : null);
        neighbors.add((tile.getY() + 1 <= y_size - 1) ? grid[tile.getX()][tile.getY() + 1] : null);
        neighbors.add((tile.getX() + 1  <= x_size - 1) ? grid[tile.getX() + 1][tile.getY()] : null);
        neighbors.add((tile.getX() - 1 >= 0) ? grid[tile.getX() - 1][tile.getY()] : null);
        neighbors.removeIf(Objects::isNull);
        neighbors.removeIf(e -> e.isWall());
        return neighbors;
    }

    public int getYSize() {
        return this.y_size;
    }

    public int getXSize() {
        return this.x_size;
    }

    public void changeClickType(Tile.Types type) {
        this.clickType = type;
    }

    public Tile getStart() {
        return this.start;
    }

    public Tile getEnd() {
        return this.end;
    }

    public void addRandomWalls() {
        Random random = new Random();
        Tile tile;

        for(int y = 0; y < this.y_size; y++) {
            for(int x = 0; x < this.x_size; x++) {
                tile = grid[x][y];
                if (tile.getType() == Tile.Types.WALL)
                    tile.setAttributes(Tile.Types.EMPTY);

                if ((random.nextInt(2)) == 1)
                    tile.setAttributes(Tile.Types.WALL);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Tile) {
            Tile tile = (Tile) o;

            if(tile.isWall()) {
                if(this.clickType == Tile.Types.EMPTY)
                    tile.setAttributes(clickType);
                return;
            }

            if (tile.getType().equals(Tile.Types.END) || tile.getType().equals(Tile.Types.START)) {
                return;
            }

            switch(this.clickType) {
                case START: case END:

                    if(clickType == Tile.Types.START) {
                        if(this.start != null)
                            start.clearTile();
                        this.start = tile;
                    }
                    else if(clickType == Tile.Types.END) {
                        if(this.end != null)
                            end.clearTile();
                        this.end = tile;
                    }

                    tile.setAttributes(clickType);
                    break;

                default:
                    tile.setAttributes(clickType);

                    break;
            }
        }
    }
}
