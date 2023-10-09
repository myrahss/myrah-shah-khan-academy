package MazeExplorer.Core;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position shift(int xOff, int yOff) {
        return new Position(this.x + xOff, this.y + yOff);
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

}
