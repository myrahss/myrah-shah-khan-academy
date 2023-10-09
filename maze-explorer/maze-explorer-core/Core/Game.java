package MazeExplorer.Core;

public class Game {

    private final boolean playing;

    public Game(World world) {
        /*
         * constructor initialize game;
         */
        playing = true;
    }

    boolean playing() {
        return playing;
    }

    /**
     * Clear the board and play one game, until receiving a quit or
     * new-game request.
     */
    public void playGame(World world, char c) {
        world.getPlayer().movePosition(world, c);
    }
}
