package MazeExplorer.Core;

import MazeExplorer.TileEngine.TETile;
import MazeExplorer.TileEngine.Tileset;

public class Room {
    private final int width;
    private final int height;
    private Position position;
    private Position center;

    public Room(Position p, int width, int height) {
        this.position = p;
        this.width = width;
        this.height = height;
        this.center = new Position(p.getX() + width / 2, p.getY() + height / 2);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getPosition() {
        return position;
    }

    public Position getCenter() {
        return center;
    }

    // draw this  room in the world;
    public void drawRoom(TETile[][] tiles) {
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                tiles[position.getX() + i][position.getY() + j] = Tileset.WATER;
            }
        }
    }

    /**
     * p1, p2 is the Hallway midPoint; p2 is at the relative right top direction if p1;
     */
    public static void drawHallway(TETile[][] tiles, Position p1, Position p2) {
        drawL(tiles, p1, p2, Tileset.WATER);
    }

    private static void drawL(TETile[][] tiles, Position p1, Position p2, TETile tile) {
        int xOff = Math.abs(p1.getX() - p2.getX()) + 1;
        int yOff = Math.abs(p1.getY() - p2.getY()) + 1;
        Position intersectionPoint = new Position(p2.getX(), p1.getY()); // draw horizontal first;

        if (p1.getX() <= p2.getX()) {
            drawHorzRow(tiles, p1, xOff, tile);
        } else {
            drawHorzRow(tiles, intersectionPoint, xOff, tile);
        }

        if (p1.getY() <= p2.getY()) {
            drawVertRow(tiles, intersectionPoint, yOff, tile);
        } else {
            drawVertRow(tiles, p2, yOff, tile);
        }
    }

    public static void drawHorzRow(TETile[][] tiles, Position p, int length, TETile tile) {
        for (int i = 0; i < length; i++) {
            tiles[p.getX() + i][p.getY()] = tile;
        }
    }

    public static void drawVertRow(TETile[][] tiles, Position p, int length, TETile tile) {
        for (int i = 0; i < length; i++) {
            tiles[p.getX()][p.getY() + i] = tile;
        }
    }
}
