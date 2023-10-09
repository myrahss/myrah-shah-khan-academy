package MazeExplorer.Core;

import MazeExplorer.InputDemo.*;
import MazeExplorer.TileEngine.TERenderer;
import MazeExplorer.TileEngine.TETile;
import MazeExplorer.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Handles game-play logic and driver.
 */
public class Engine {
    /** Game renderer */
    TERenderer ter = new TERenderer();
    /** WIDTH and HEIGHT of the screen */
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    /** String of all inputs */
    private StringBuilder fullInput = new StringBuilder();
    /** World for gameplay */
    private World world = null;
    /** True if limited line of sight enabled, using C character during gameplay */
    private boolean spotlight = false;
    /** last time of render */
    private long lastRenderTime = System.currentTimeMillis();

    /**
     * Drives gameplay.
     * Opening screen options:
     * N: New game, enter seed and press s to begin
     * L: Load previous game state
     * R: Replay previous game
     * Q: Quit game
     *
     * @param inputSource Keyboard input tracker
     * @param isLive
     * @param isReplay
     * @return
     */
    public World interact(InputSource inputSource, boolean isLive, boolean isReplay) {
        //while game is not over
        while (inputSource.possibleNextInput()) {
            char c = inputSource.getNextKey();
            //opening screen options
            switch (c) {
                case 'N' -> interactGeneration(inputSource, isLive);
                case 'L' -> interactLoadOrReplay(isLive, false, 'L');
                case 'R' -> interactLoadOrReplay(isLive, true, 'R');
                case 'Q' -> {
                    System.out.println("done.");
                    DrawInterface.pause(1500);
                    System.exit(0);
                }
            }
            if (this.world != null) {
                //setup world
                world.drawWorld();
                Game game = new Game(world);
                if (isLive && System.currentTimeMillis() - lastRenderTime > 16) {
                    renderWorldWithHUD();
                    this.lastRenderTime = System.currentTimeMillis();
                }

                while (game.playing()) {
                    //win game if we reach the door
                    if (world.getWorldTiles()[world.getPlayer().getPosition().getX()][world.getPlayer().getPosition().getY()]
                            == Tileset.LOCKED_DOOR) {
                        winGame();
                    }
                    if (inputSource.possibleNextInput()) {
                        char ch = inputSource.getNextKey();
                        //':Q' to save and exit game
                        if (ch == ':' && inputSource.getNextKey() == 'Q') {
                            this.saveGame(fullInput);
                            return this.world;
                        }
                        //'C' turns on limited line of sight
                        else if (ch == 'C') {
                            this.spotlight = !this.spotlight;
                        }
                        //Play replays at a normal speed
                        if (isReplay) {
                            DrawInterface.pause(400);
                        }
                        //use keyboard input to play game
                        game.playGame(world, ch);
                        fullInput.append(ch);
                        world.drawWorld();
                        if (isLive) {
                            if (System.currentTimeMillis() - lastRenderTime > 16) {
                                renderWorldWithHUD(); // rendering;
                                this.lastRenderTime = System.currentTimeMillis();
                            }
                        }
                    } else { // if no further inputSource; no further character from input string;
                        break;
                    }
                }
                //If ':Q': save game
                this.saveGame(fullInput);
            }
        }
        System.out.println("Processed " + fullInput.length() + " characters.");
        return this.world;
    }

    private void winGame() {
        DrawInterface.drawFrame("You won and made it to land!");
        DrawInterface.pause(1500);
        System.exit(0);
    }

    public void interactGeneration(InputSource inputSource, boolean isLive) {
        if (isLive) {
            DrawInterface.drawFrame("enter an random seed");
        }
        long seed = getSeedfromInput(inputSource, isLive);
        this.world = new World(seed);  // initial gen world, game playing
        fullInput.append('N');
        fullInput.append(seed);
        fullInput.append('S');
        world.genWorld();
    }

    public void interactLoadOrReplay(boolean isLive, boolean isReplay, char c) {
        File savedGametxt = new File("savedGame.txt");
        if (!savedGametxt.exists()) {
            if (isLive) {
                DrawInterface.drawFrame("No saved game found");
                DrawInterface.pause(1500);
            }
            System.exit(0);
        } else {
            String savedInputs = readContentsAsString(savedGametxt);
            this.world = interactWithInputStringWorld(savedInputs, isLive, isReplay);
            if (isLive) {
                this.ter.renderFrame(world.getWorldTiles());
            }
            fullInput.append(c);
        }
    }


    /**
     * Method used for exploring a fresh world. This method handles all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        boolean isLive = true;
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        this.ter.initialize(WIDTH, HEIGHT);
        DrawInterface.drawLandInterface();
        MouseListener listener = () -> {
            if (world != null && (System.currentTimeMillis() - lastRenderTime > 16)) {
                renderWorldWithHUD();
                lastRenderTime = System.currentTimeMillis();
            }
        };

        InputSource inputSource = new KeyboardInputSource(listener);
        //fullInput for saving and loading
        interact(inputSource, isLive, false);
        System.out.println("Quit.");
        System.exit(0);
    }

    public TETile[][] interactWithInputString(String input) {
        boolean isLive = false;
        fullInput = new StringBuilder();
        this.world = null;
        return interactWithInputString(input, isLive);
    }

    public TETile[][] interactWithInputString(String input, boolean isLive) {
        World worldObj = interactWithInputStringWorld(input, isLive, false);
        return worldObj.getWorldTiles();
    }

    public World interactWithInputStringWorld(String input, boolean isLive, boolean isReplay) {
        InputSource inputSource = new StringInputDevice(input);
        return interact(inputSource, isLive, isReplay);
    }

    // choose use which render mode: full render/ spotLight render;
    private void renderFrameWithModes() {
        if (spotlight) {
            ter.renderSpotlightFrame(this.world.getWorldTiles(), this.world.getPlayer().getPosition());
        } else {
            ter.renderFrame(this.world.getWorldTiles());
        }
    }

    private void renderWorldWithHUD() {

        double mouseX = StdDraw.mouseX();
        double mouseY = StdDraw.mouseY();
        if (world.outofbounds((int) mouseX, (int) mouseY) || mouseX < 0 || mouseY < 0) {
            return;
        }
        TETile tile = this.world.getWorldTiles()[(int) mouseX][(int) mouseY];
        this.renderFrameWithModes();
        DrawInterface.drawHUD(tile.description());
    }

    private Long getSeedfromInput(InputSource inputSource, boolean isLive) {

        long seed = 12345678910L;

        StringBuilder currSeed = new StringBuilder();
        while (inputSource.possibleNextInput()) {
            char c = inputSource.getNextKey();
            if (c != 'S' && Character.isDigit(c) && currSeed.length() <= 19) {
                currSeed.append(c);
                if (isLive) {
                    DrawInterface.drawFrame(currSeed.toString());
                }
            }
            if (c == 'S') {
                String inputSeed = currSeed.toString();
                seed = Long.parseLong(inputSeed);
                break;
            }
        }
        return seed;
    }

    /**
     * Return the entire contents of FILE as a String.
     */
    private static String readContentsAsString(File file) {
        return new String(readContents(file), StandardCharsets.UTF_8);
    }

    private static byte[] readContents(File file) {
        if (!file.isFile()) {
            throw new IllegalArgumentException("must be a normal file");
        }
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void saveGame(StringBuilder fullInputString) {
        try {
            FileWriter savedGame = new FileWriter("savedGame.txt");
            savedGame.write(fullInputString.toString());
            savedGame.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
