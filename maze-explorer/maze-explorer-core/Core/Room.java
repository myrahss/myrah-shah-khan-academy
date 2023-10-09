package MazeExplorer.Core;

import MazeExplorer.TileEngine.TETile;
import MazeExplorer.TileEngine.Tileset;

/**
 * Represents a room in the world.
 */
public class Room {

    /**
     * Creates a room at a position with given dimensions
     * @param position The position to create the room
     * @param width room width
     * @param height room height
     */
    public Room(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.center = new Position(position.x() + width / 2, position.y() + height / 2);

    }

    /** Return the room width */
    public int getWidth() {
        return width;
    }

    /** Return the room height */
    public int getHeight() {
        return height;
    }

    /** Return the room position */
    public Position getPosition() {
        return position;
    }

    /** Return the room center */
    public Position getCenter() {
        return center;
    }

    /**
     * Draw the room in a given world
     * @param tiles the World matrix to draw the room on
     */
    public void drawRoom(TETile[][] tiles) {
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                tiles[position.x() + i][position.y() + j] = Tileset.WATER;
            }
        }
    }

    /**
     * Draws a hallway connecting rooms
     * @param tiles the World to draw the hallway on
     * @param p1 the hallway midpoint
     * @param p2 relative right top of p1
     */
    public static void drawHallway(TETile[][] tiles, Position p1, Position p2) {
        //p1, p2 is the Hallway midPoint; p2 is at the relative right top direction of p1
        drawL(tiles, p1, p2, Tileset.WATER);
    }

    /**
     * Utilities for creating hallways and rooms
     */
    private static void drawL(TETile[][] tiles, Position p1, Position p2, TETile tile) {
        int xOff = Math.abs(p1.x() - p2.x()) + 1;
        int yOff = Math.abs(p1.y() - p2.y()) + 1;
        Position intersectionPoint = new Position(p2.x(), p1.y()); // draw horizontal first;

        if (p1.x() <= p2.x()) {
            drawHorzRow(tiles, p1, xOff, tile);
        } else {
            drawHorzRow(tiles, intersectionPoint, xOff, tile);
        }

        if (p1.y() <= p2.y()) {
            drawVertRow(tiles, intersectionPoint, yOff, tile);
        } else {
            drawVertRow(tiles, p2, yOff, tile);
        }
    }

    private static void drawHorzRow(TETile[][] tiles, Position p, int length, TETile tile) {
        for (int i = 0; i < length; i++) {
            tiles[p.x() + i][p.y()] = tile;
        }
    }

    private static void drawVertRow(TETile[][] tiles, Position p, int length, TETile tile) {
        for (int i = 0; i < length; i++) {
            tiles[p.x()][p.y() + i] = tile;
        }
    }

    /** Instance variables for room */
    private final int width;
    private final int height;
    private final Position position;
    private final Position center;
}
