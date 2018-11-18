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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    boolean isOpened() {
        return isOpened;
    }

    void setOpened(boolean opened) {
        isOpened = opened;
    }
}
