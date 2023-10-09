package MazeExplorer.Core;

import MazeExplorer.TileEngine.TETile;
import MazeExplorer.TileEngine.Tileset;

import java.util.*;

import static MazeExplorer.Core.RandomUtils.uniform;

/**
 * generates/draw a world consisting random rooms and hallways;
 */
public class World {
    public static final int WIDTH = Engine.WIDTH;
    public static final int HEIGHT = Engine.HEIGHT;
    private TETile[][] worldTiles;
    private Random RANDOM;
    private final int ROOMATTEMPTS = 60;
    private List<Room> roomList = new ArrayList<>();

    private Avatar player;
    private Door target;


    /**
     * Construct a world with pass-in seed and random;
     */
    public World(long seed) {
        // initialize tiles
        this.RANDOM = new Random(seed);
        this.worldTiles = new TETile[WIDTH][HEIGHT];
    }


    /**
     * Generate world;
     */

    public void genWorld() {
        fillBoardWithNothing(worldTiles);
        // fills in rooms....
        if (roomList.isEmpty()) {
            fillwithrooms();
        }
        /** sort as position x or y, make connect to closer room;
         * @source https://stackoverflow.com/questions/16252269/how-to-sort-a-list-arraylist
         */
        Collections.sort(roomList, new Comparator<Room>() {
            @Override
            public int compare(Room r1, Room r2) {
                return Integer.compare(r2.getCenter().getY(), r1.getCenter().getY());
            }
        });
        player = new Avatar(this);
        target = new Door(this);

    }

    public TETile[][] drawWorld() {
        fillBoardWithNothing(worldTiles);
        drawRooms();
        drawHallwayConnectRooms();
        drawWall();
        drawPlayer();
        drawTarget();
        return worldTiles;
    }

    private void drawPlayer() {
        worldTiles[player.getPosition().getX()][player.getPosition().getY()] = Tileset.AVATAR;
    }
    private void drawTarget() {
        worldTiles[target.getPosition().getX()][target.getPosition().getY()] = Tileset.LOCKED_DOOR;
    }


    private void drawHallwayConnectRooms() {
        for (int i = 0; i < roomList.size() - 1; i++) {
            Room r1 = roomList.get(i);
            Room r2 = roomList.get(i + 1);
            Room.drawHallway(worldTiles, r1.getCenter(), r2.getCenter());
        }
    }

    // draw all walls at last;
    private void drawWall() {
        for (int x = 1; x < WIDTH - 1; x += 1) {
            for (int y = 1; y < HEIGHT - 1; y += 1) {
                if (worldTiles[x][y] == Tileset.GRASS
                        && (worldTiles[x - 1][y] == Tileset.WATER
                        || worldTiles[x + 1][y] == Tileset.WATER
                        || worldTiles[x][y + 1] == Tileset.WATER
                        || worldTiles[x][y - 1] == Tileset.WATER
                        || worldTiles[x - 1][y - 1] == Tileset.WATER
                        || worldTiles[x + 1][y + 1] == Tileset.WATER
                        || worldTiles[x - 1][y + 1] == Tileset.WATER
                        || worldTiles[x + 1][y - 1] == Tileset.WATER
                        )) {
                    worldTiles[x][y] = Tileset.WALL;
                }
            }
        }
    }


    /**
     * generate random RoomParameters, and construct room;
     * // make room width and Length bigger than 6(includes wall), distinguish from a Hallway;
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

    private void fillwithrooms() {
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

    public boolean isWall(Position p) {
        return this.worldTiles[p.getX()][p.getY()].equals(Tileset.WALL);
    }


    public boolean outofbounds(int x, int y) {
        return (x > WIDTH - 4 || y > HEIGHT - 4);
    }

    public boolean overlap(Room room) {
        for (int i = 0; i < room.getWidth(); i++) {
            for (int j = 0; j < room.getHeight(); j++) {
                if (worldTiles[room.getPosition().getX() + i][room.getPosition().getY() + j] != Tileset.GRASS) {
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

}

