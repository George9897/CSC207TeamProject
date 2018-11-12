package fall2018.csc2017.GameCenter;

/**
 * The Tiles.
 */
public class MineTile {
    /**
     * The value of this mineTile.
     * Representing how many booms nearby.
     */
    short value;
    /**
     * The boolean of whether this mineTile is opened or not.
     */
    boolean open;

    /**
     * The constructor for tiles.
     */
    public MineTile() {
        this.value = 0;
        this.open = false;
    }
}
