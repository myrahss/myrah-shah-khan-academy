package byow.InputDemo;

/**
 * Created by hug.
 */
import byow.Core.DrawInterface;
import byow.Core.Engine;
import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.StdDraw;

public class KeyboardInputSource implements InputSource {

    MouseListener listener;
    private static final boolean PRINT_TYPED_KEYS = false;
    public KeyboardInputSource(MouseListener listener) {
        this.listener = listener;
        StdDraw.text(0.3, 0.3, "press m to moo, q to quit");
    }

    public char getNextKey() {
        while (true) {
            listener.onMouseMoved();
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (PRINT_TYPED_KEYS) {
                    System.out.print(c);
                }
                return c;
            }
            DrawInterface.pause(10);
        }
    }


    public boolean possibleNextInput() {
        return true;
    }
}
