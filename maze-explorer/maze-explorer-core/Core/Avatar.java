package MazeExplorer.Core;

import java.util.Map;

/**
 * Represents the avatar, or playable token, on the screen. It has methods to move the avatar based on
 * keyboard input and to return the position of the avatar.
 */
public class Avatar {

    /** Creates an avatar on a given world.
     * @param world the World that this avatar will belong to.
     */
    public Avatar(World world) {
        this.position = world.getRoomList().get(0).getCenter();
    }

    /**
     * Moves the avatar's position based on keyboard input of 'WASD' keys, using a class mapping of character to shift.
     * @param world The world the avatar belongs to/the current game is on.
     * @param c The 'WASD' character from keyboard input.
     */
    public void movePosition(World world, char c) {
        if (xShift.containsKey(c)) {
            Position newPosition = this.getPosition().shift(xShift.get(c), yShift.get(c));
            if (!world.outofbounds(newPosition.getX(), newPosition.getY()) && !world.isWall(newPosition)) {
                this.position = newPosition;
            }
        }
    }

    /**
     * Returns the position of the avatar
     * @return Position
     */
    public Position getPosition() {
        return this.position;
    }

    //Private variables of the position of the avatar and the character to position shift mappings.
    private Position position;
    private final Map<Character, Integer> xShift = Map.ofEntries(
            Map.entry('W', 0),
            Map.entry('A', -1),
            Map.entry('S', 0),
            Map.entry('D', 1)
    );
    private final Map<Character, Integer> yShift = Map.ofEntries(
            Map.entry('W', 1),
            Map.entry('A', 0),
            Map.entry('S', -1),
            Map.entry('D', 0)
    );
}
