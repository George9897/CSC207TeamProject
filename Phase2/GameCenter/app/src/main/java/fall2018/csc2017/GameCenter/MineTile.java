package fall2018.csc2017.GameCenter;

/**
 * The Tiles for Mine.
 */
class MineTile {
    /**
     * The value of this mineTile.
     * Representing how many booms nearby.
     */
    short value;
    /**
     * The boolean of whether this mineTile is opened or not.
     */
    boolean isOpened;

    /**
     * The constructor for tiles.
     */
    MineTile() {
        this.value = 0;
        this.isOpened = false;
    }
}
