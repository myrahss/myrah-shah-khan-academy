package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Room {
    //needs to include hallways
    private int width;
    private int height;
    private Position p;
    private Position c;

    public Room(Position p, int width, int height) {
        this.p = p;
        this.width = width;
        this.height = height;
        this.c = new Position(p.getX() + width / 2, p.getY() + height / 2);

    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Position getP() {
        return p;
    }
    public Position getC() {
        return c;
    }
    // draw this  room in the world;
    public void drawRoom(TETile[][] tiles) {
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                tiles[p.getX() + i][p.getY() + j] = Tileset.WATER;
            }
        }
//        drawHorzRow(tiles, p, width, Tileset.WALL);
//        drawHorzRow(tiles, p.shift( 0, height -1), width, Tileset.WALL);
//        drawVertRow(tiles, p, height, Tileset.WALL);
//        drawVertRow(tiles, p.shift( width-1, 0), height, Tileset.WALL);
    }

    /**  p1, p2 is the Hallway midPoint; p2 is at the relative right top direction if p1;
     *
     * */
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
