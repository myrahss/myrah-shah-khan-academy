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
        this.world = world;
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
     * @param c current keyboard input to move character
     */
    public void playGame(char c) {
        this.world.getPlayer().movePosition(world, c);
    }

    /** True if game not over */
    private final boolean playing;
    public World world;
}
