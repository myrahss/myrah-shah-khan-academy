package byow.Core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class DrawInterface {
    public static final int HEIGHT = Engine.HEIGHT;
    public static final int WIDTH = Engine.WIDTH;

    public static void drawFrame(String s) {

        /* default  Frame, with input string, draw frame at center;
         * Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, s);
        StdDraw.show();
    }

    public static void drawHUD(String s) {
        /* default  Frame, with input string, draw frame at center;
         * Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont();
        StdDraw.text(5, HEIGHT - 1, s);
        StdDraw.show();
    }

    public static void drawLandInterface() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(WIDTH / 2, HEIGHT / 4 * 3, "ESCAPE THE WATER");

        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontSmall);
        StdDraw.text(WIDTH / 2, HEIGHT / 3 * 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 3 * 2 - 2, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 3 * 2 - 4, "Replay Game (R)");
        StdDraw.text(WIDTH / 2, HEIGHT / 3 * 2 - 6, "Quit (Q)");
        StdDraw.show();
    }

    public static void pause(int time) {
        StdDraw.pause(time);
    }
}
