package byow.Core;

import byow.InputDemo.*;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    private StringBuilder fullInput = new StringBuilder();
    private World world = null;
    private boolean spotlight = false;
    private long lastRenderTime = System.currentTimeMillis();



    public World interact(InputSource inputSource, boolean isLive, boolean isReplay) {

        while (inputSource.possibleNextInput()) {
            char c = inputSource.getNextKey();
            if (c == 'N') {
                interactGeneration(inputSource, isLive);
            }
            if (c == 'L') {
                isReplay = false;
                interactLoadOrReplay(inputSource, isLive, isReplay, 'L');
            }
            if (c == 'R') {
                isReplay = true;
                interactLoadOrReplay(inputSource, isLive, isReplay, 'R');
            }
            if (c == 'Q') {
                System.out.println("done.");
                DrawInterface.pause(1500);
                System.exit(0);
            }

            if (this.world != null) {
                world.drawWorld();
                Game game = new Game(world);
                if (isLive) {
                    if (System.currentTimeMillis() - lastRenderTime > 16) {
                        renderWorldWithHUD();
                        this.lastRenderTime = System.currentTimeMillis();
                    }
                }

                while (game.playing()) {
                    if (world.getWorldTiles()[world.getPlayer().getP().getX()]
                            [world.getPlayer().getP().getY()] == Tileset.LOCKED_DOOR) {
                        winGame();
                    }
                    if (inputSource.possibleNextInput()) {
                        char c2 = inputSource.getNextKey();
                        if (c2 == ':') {
                            if (inputSource.getNextKey() == 'Q') {
                                this.saveGame(fullInput);
                                return this.world;
                            }
                        } else if (c2 == 'C') {
                            this.spotlight = !this.spotlight;
                        }
                        if (isReplay) {
                            DrawInterface.pause(400);
                        }
                        game.playGame(world, c2);
                        fullInput.append(c2);
                        world.drawWorld();
                        if (isLive) {
                            if (System.currentTimeMillis() - lastRenderTime > 16) {
                                renderWorldWithHUD(); // rendering;
                                this.lastRenderTime = System.currentTimeMillis();
                            }
                        }
                    } else { // if no further inputsource; no further charactor from input string;
                        break;
                    }
                }
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

    public void interactLoadOrReplay(InputSource inputSource, boolean isLive,
                                     boolean isReplay, char c) {
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
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        boolean isLive = true;
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        this.ter.initialize(WIDTH, HEIGHT);
        DrawInterface.drawLandInterface();
        MouseListener listener = new MouseListener() {
            @Override
            public void onMouseMoved() {
                if (world != null && (System.currentTimeMillis() - lastRenderTime > 16)) {
                    renderWorldWithHUD();
                    lastRenderTime = System.currentTimeMillis();
                }
            }
        };

        InputSource inputSource = new KeyboardInputSource(listener);
        //fullInput for saving and loading
        interact(inputSource, isLive, false);
        System.out.println("Quit.");
        System.exit(0);
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     */

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
    private void renderFramewithModes() {
        if (spotlight) {
            ter.renderSpotlightFrame(this.world.getWorldTiles(), this.world.getPlayer().getP());
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
        this.renderFramewithModes();
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
        } catch (IOException excp) {
            throw new IllegalArgumentException(excp.getMessage());
        }
    }

    public void saveGame(StringBuilder fullinputstring) {
        try {
            FileWriter savedGame = new FileWriter("savedGame.txt");
            savedGame.write(fullinputstring.toString());
            savedGame.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
