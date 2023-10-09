package MazeExplorer.Core;

import MazeExplorer.TileEngine.TERenderer;
import MazeExplorer.TileEngine.TETile;
import org.junit.Test;

import static org.junit.Assert.*;

public class Tests {
    @Test
    public void testOutOfBounds() {
        World testWorld = new World(123);
        assertTrue(testWorld.outofbounds(90, 10));
        assertFalse(testWorld.outofbounds(30, 40));
    }

    @Test
    public void testLoadFeature() {
        Engine engine = new Engine();
        TERenderer ter = engine.ter;
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);
        TETile[][] res1 = engine.interactWithInputString("N999SDDDWWWDDD");

        engine.interactWithInputString("N999SDDD:Q");
        TETile[][] res2 = engine.interactWithInputString("LWWWDDD");

        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                assertSame(res1[i][j], res2[i][j]);
            }
        }
    }

    @Test
    public void testStandard() {
        Engine engine = new Engine();
        TERenderer ter = engine.ter;
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);
        TETile[][] res1 = engine.interactWithInputString("N999SDDDWWWDDD");

        engine.interactWithInputString("N999SDDD:Q");
        engine.interactWithInputString("LWWW:Q");
        TETile[][] res2 = engine.interactWithInputString("LDDD:Q");

        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                assertSame(res1[i][j], res2[i][j]);
            }
        }
    }

    @Test
    public void testLoad() {
        Engine engine = new Engine();
        TERenderer ter = engine.ter;
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);
        TETile[][] res1 = engine.interactWithInputString("N999SdddWWWDDD");

        engine.interactWithInputString("N999SDDD:Q");
        engine.interactWithInputString("L:Q");
        engine.interactWithInputString("L:Q");
        TETile[][] res2 = engine.interactWithInputString("LWWWDDD");

        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                assertSame(res1[i][j], res2[i][j]);
            }
        }
    }

}
