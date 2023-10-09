package MazeExplorer.Core;

import MazeExplorer.TileEngine.TETile;
import MazeExplorer.TileEngine.Tileset;

import java.util.*;

import static MazeExplorer.Core.RandomUtils.uniform;

/**
 * Represents the game world, the underlying backing of the world structure
 */
public class World {
    /**
     * Construct a world with pass-in random seed
     * @param seed random long to generate world
     */
    public World(long seed) {
        this.RANDOM = new Random(seed);
        this.worldTiles = new TETile[WIDTH][HEIGHT];
    }

    /**
     * Generate rooms, hallways, door and avatar for world
     */
    public void genWorld() {
        fillBoardWithNothing(worldTiles);
        // fills in rooms....
        if (roomList.isEmpty()) {
            fillRooms();
        }
        //sort as position x or y, make connect to closer room
        roomList.sort(new Comparator<Room>() {
            @Override
            public int compare(Room r1, Room r2) {
                return Integer.compare(r2.getCenter().y(), r1.getCenter().y());
            }
        });
        player = new Avatar(this);
        target = new Door(this);

    }

    /**
     * Draw features of world
     */
    public void drawWorld() {
        fillBoardWithNothing(worldTiles);
        drawRooms();
        drawHallwayConnectRooms();
        drawWall();
        drawPlayer();
        drawTarget();
    }

    /**
     * Methods to draw world features
     */
    private void drawPlayer() {
        worldTiles[player.getPosition().x()][player.getPosition().y()] = Tileset.AVATAR;
    }

    private void drawTarget() {
        worldTiles[target.getPosition().x()][target.getPosition().y()] = Tileset.LOCKED_DOOR;
    }

    private void drawHallwayConnectRooms() {
        for (int i = 0; i < roomList.size() - 1; i++) {
            Room r1 = roomList.get(i);
            Room r2 = roomList.get(i + 1);
            Room.drawHallway(worldTiles, r1.getCenter(), r2.getCenter());
        }
    }

    private void drawWall() {
        for (int x = 1; x < WIDTH - 1; x += 1) {
            for (int y = 1; y < HEIGHT - 1; y += 1) {
                if (worldTiles[x][y] == Tileset.GRASS && (worldTiles[x - 1][y] == Tileset.WATER || worldTiles[x + 1][y] == Tileset.WATER || worldTiles[x][y + 1] == Tileset.WATER || worldTiles[x][y - 1] == Tileset.WATER || worldTiles[x - 1][y - 1] == Tileset.WATER || worldTiles[x + 1][y + 1] == Tileset.WATER || worldTiles[x - 1][y + 1] == Tileset.WATER || worldTiles[x + 1][y - 1] == Tileset.WATER)) {
                    worldTiles[x][y] = Tileset.WALL;
                }
            }
        }
    }

    /**
     * Generate randomized room > 4 dimension
     * @return new Room
     */
    private Room genRandRoom() {
        int width = uniform(this.RANDOM, 4, (WIDTH / 5));
        int height = uniform(this.RANDOM, 4, (HEIGHT / 5));
        int x = uniform(this.RANDOM, 4, WIDTH);
        int y = uniform(this.RANDOM, 4, HEIGHT);
        while (outofbounds(x + width, y + height)) {
            x = uniform(this.RANDOM, 4, WIDTH);
            y = uniform(this.RANDOM, 4, HEIGHT);
        }
        Room newRoom = new Room(new Position(x, y), width, height);
        return newRoom;
    }

    /**
     * Utilities for creating and drawing rooms
     */
    private void fillRooms() {
        for (int i = 0; i < ROOMATTEMPTS; i++) {
            Room newroom = genRandRoom();
            if (!(this.overlap(newroom))) {
                roomList.add(newroom);
                newroom.drawRoom(worldTiles);
            }
        }
    }

    private void drawRooms() {
        for (Room room : roomList) {
            room.drawRoom(worldTiles);
        }
    }

    /** Utilities for edge case and error checking */
    public boolean isWall(Position p) {
        return this.worldTiles[p.x()][p.y()].equals(Tileset.WALL);
    }


    public boolean outofbounds(int x, int y) {
        return (x > WIDTH - 4 || y > HEIGHT - 4);
    }

    public boolean overlap(Room room) {
        for (int i = 0; i < room.getWidth(); i++) {
            for (int j = 0; j < room.getHeight(); j++) {
                if (worldTiles[room.getPosition().x() + i][room.getPosition().y() + j] != Tileset.GRASS) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void fillBoardWithNothing(TETile[][] world) {
        int height = world[0].length;
        int width = world.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.GRASS;
            }
        }
    }

    /**
     * Getters and Setters
     */
    public TETile[][] getWorldTiles() {
        return worldTiles;
    }

    public void setWorldTiles(TETile[][] worldTiles) {
        this.worldTiles = worldTiles;
    }

    public List<Room> getRoomList() {
        return this.roomList;
    }

    public Avatar getPlayer() {
        return player;
    }

    public Door getTarget() {
        return target;
    }

    /** Instance variables */
    public static final int WIDTH = Engine.WIDTH;
    public static final int HEIGHT = Engine.HEIGHT;
    private TETile[][] worldTiles;
    private final Random RANDOM;
    private final int ROOMATTEMPTS = 60;
    private final List<Room> roomList = new ArrayList<>();

    private Avatar player;
    private Door target;
}

