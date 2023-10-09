package MazeExplorer.Core;

/**
 * Represents the position of an object
 */
public record Position(int x, int y) {

    /**
     * Shifts the position by specific x amount and y amount.
     * @param xOff the horizontal shift amount
     * @param yOff the vertical shift amount
     * @return new Position with shifted location
     */
    public Position shift(int xOff, int yOff) {
        return new Position(this.x + xOff, this.y + yOff);
    }
}
