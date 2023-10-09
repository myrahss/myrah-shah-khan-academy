package MazeExplorer.Core;

/**
 * Represents the door to exit the maze. Implements a constructor and position getter.
 */
public class Door {

    /**
     * Creates a door in the center of the last generated room.
     * @param world the current game world
     */
    public Door(World world) {
        this.position = world.getRoomList().get(world.getRoomList().size() - 1).getCenter();
    }

    /**
     * @return the position of the door.
     */
    public Position getPosition() {
        return this.position;
    }

    //Private variable tracking the position of the door.
    private final Position position;
}

