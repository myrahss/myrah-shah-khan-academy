package byow.Core;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position shift(int xoff, int yoff) {
        return new Position(this.x + xoff, this.y + yoff);
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

}
