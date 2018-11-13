package fall2018.csc2017.GameCenter;

/**
 * MinePoint to represent a Tile's position.
 */
public class MinePoint {
    /**
     * The x coordinate.
     */
    int x;
    /**
     * The y coordinate.
     */
    int y;

    /**
     * The constructor for point.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    MinePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return 2 * x + y;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();

    }
}
