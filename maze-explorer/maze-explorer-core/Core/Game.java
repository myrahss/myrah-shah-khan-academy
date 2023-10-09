package MazeExplorer.Core;

/**
 * Represents current game state
 */
public class Game {

    /**
     * Initializes game on given world
     * @param world World to create game from
     */
    public Game(World world) {
        playing = true;
    }

    /**
     * @return game is not over
     */
    boolean playing() {
        return playing;
    }

    /**
     * Clear the board and play one game, until receiving a quit or
     * new-game request.
     * @param world World to play game on
     * @param c current keyboard input to move character
     */
    public void playGame(World world, char c) {
        world.getPlayer().movePosition(world, c);
    }

    /** True if game not over */
    private final boolean playing;
}
