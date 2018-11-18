package fall2018.csc2017.GameCenter;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.io.Serializable;


/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * A drawable background for picture sliding tiles.
     */
    private Drawable drawableBackground;

    /**
     * The unique id.
     */
    private int id;

    /**
     * Indicate whether the mine tile is opened.
     */
    private boolean isOpened;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Getter for a drawable background.
     *
     * @return the drawable background.
     */
    public Drawable getDrawableBackground() {
        return drawableBackground;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * The constructor for tiles.
     */
    Tile() {
        this.id = 0;
        this.isOpened = false;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the background id of a tile.
     */
    public Tile(int backgroundId, boolean isDrawable) {
        id = backgroundId;
        if (isDrawable) {
            for (int i = 0; i < 25; i++) {
                if (i == id) {
                    drawableBackground = new BitmapDrawable(SettingActivity.chunkedImages.get(i));
                }
            }
        } else {
            switch (backgroundId) {
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
                case 9:
                    background = R.drawable.tile_9;
                    break;
                case 10:
                    background = R.drawable.tile_10;
                    break;
                case 11:
                    background = R.drawable.tile_11;
                    break;
                case 12:
                    background = R.drawable.tile_12;
                    break;
                case 13:
                    background = R.drawable.tile_13;
                    break;
                case 14:
                    background = R.drawable.tile_14;
                    break;
                case 15:
                    background = R.drawable.tile_15;
                    break;
                case 16:
                    background = R.drawable.tile_16;
                    break;
                case 17:
                    background = R.drawable.tile_17;
                    break;
                case 18:
                    background = R.drawable.tile_18;
                    break;
                case 19:
                    background = R.drawable.tile_19;
                    break;
                case 20:
                    background = R.drawable.tile_20;
                    break;
                case 21:
                    background = R.drawable.tile_21;
                    break;
                case 22:
                    background = R.drawable.tile_22;
                    break;
                case 23:
                    background = R.drawable.tile_23;
                    break;
                case 24:
                    background = R.drawable.tile_24;
                    break;
                case 101:
                    background = R.drawable.tile_101;
                    break;
                case 102:
                    background = R.drawable.tile_102;
                    break;
                case 103:
                    background = R.drawable.tile_103;
                    break;
                case 104:
                    background = R.drawable.tile_104;
                    break;
                case 105:
                    background = R.drawable.tile_105;
                    break;
                case 106:
                    background = R.drawable.tile_106;
                    break;
                case 107:
                    background = R.drawable.tile_107;
                    break;
                case 108:
                    background = R.drawable.tile_108;
                    break;
                case 109:
                    background = R.drawable.tile_109;
                    break;
                default:
                    background = R.drawable.tile_0;
            }
        }
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
