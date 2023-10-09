package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import org.junit.Test;

import static org.junit.Assert.*;

public class Tests {
    @Test
    public void testOutOfBounds() {
        World testworld = new World(123);
        assertTrue(testworld.outofbounds(90, 10));
        assertFalse(testworld.outofbounds(30, 50));
    }


    @Test
    public void testtwoInputstrint() {
        Engine engine = new Engine();
        TERenderer ter = engine.ter;
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);
        TETile[][] res1 = engine.interactWithInputString("N999SDDDWWWDDD");


        engine.interactWithInputString("N999SDDD:Q");
        TETile[][] res2 = engine.interactWithInputString("LWWWDDD");


        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                assertTrue(res1[i][j] == res2[i][j]);
            }
        }
    }

    @Test
    public void testthreeInputstrint() {
        Engine engine = new Engine();
        TERenderer ter = engine.ter;
        ter.initialize(Engine.WIDTH, Engine.HEIGHT);
        TETile[][] res1 = engine.interactWithInputString("N999SDDDWWWDDD");


        engine.interactWithInputString("N999SDDD:Q");
        engine.interactWithInputString("LWWW:Q");
        TETile[][] res2 = engine.interactWithInputString("LDDD:Q");


        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                assertTrue(res1[i][j] == res2[i][j]);
            }
        }
    }

    @Test
    public void testfourInputstrint() {
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
                assertTrue(res1[i][j] == res2[i][j]);
            }
        }
    }

}
