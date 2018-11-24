package fall2018.csc2017.GameCenter;

import android.graphics.drawable.Drawable;

/**
 * The Tiles for Mine.
 */
class MineTile{
    /**
     * The value of this mineTile.
     * Representing how many booms nearby.
     */
    private int value;
    /**
     * The boolean of whether this mineTile is opened or not.
     */
    private boolean isOpened;
    private int x;
    private int y;
    /**
     * A drawable background for picture sliding tiles.
     */
    private Drawable drawableBackground;
    /**
     * The background id to find the tile image.
     */
    private int background;


    /**
     * The constructor for tiles.
     */
    MineTile(int value, boolean isOpened) {
        this.value = value;
        this.isOpened = isOpened;
        background = value;
        if (!isOpened) {
            background = R.drawable.tile_10;
        }else {switch (value) {
            case -1:
                background = R.drawable.tile_13;
                break;
            case 0:
                background = R.drawable.tile_0;
                break;
            case 1:
                background = R.drawable.tile_1;
                break;
            case 2:
                background = R.drawable.tile_2;
                break;
            case 3:
                background = R.drawable.tile_3;
                break;
            case 4:
                background = R.drawable.tile_4;
                break;
            case 5:
                background = R.drawable.tile_5;
                break;
            case 6:
                background = R.drawable.tile_6;
                break;
            case 7:
                background = R.drawable.tile_7;
                break;
            case 8:
                background = R.drawable.tile_8;
                break;
        }
        }
    }

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
