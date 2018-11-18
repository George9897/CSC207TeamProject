package fall2018.csc2017.GameCenter;

/**
 * The Tiles for Mine.
 */
class MineTile {
    /**
     * The value of this mineTile.
     * Representing how many booms nearby.
     */
    private int value;
    /**
     * The boolean of whether this mineTile is opened or not.
     */
    private boolean isOpened;

    /**
     * The constructor for tiles.
     */
    MineTile() {
        this.value = 0;
        this.setOpened(false);
    }

    /**
     * Get the value of this mineTile.
     *
     * @return the value of this mineTile.
     */
    public int getValue() {
        return value;
    }

    /**
     * Set the value of this mineTile.
     *
     * @param value the value of this mineTile.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Get the boolean of whether this mineTile is opened or not.
     *
     * @return the boolean of whether this mineTile is opened or not.
     */
    boolean isOpened() {
        return isOpened;
    }

    /**
     * Set the boolean of whether this mineTile is opened or not.
     *
     * @param opened the boolean of whether this mineTile is opened or not.
     */
    void setOpened(boolean opened) {
        this.isOpened = opened;
    }
}
