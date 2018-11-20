package fall2018.csc2017.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

public class Sudoku extends Observable implements Serializable, Iterable<Tile> {

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new Sudoku.SudokuIterator();
    }

    /**
     * The serialVersionUID.
     */
    //TODO: don't know for now
    //public static final long serialVersionUID = L;

//    /**
//     * The boolean of whether this slidingTile is drawable.
//     */
//    boolean isDrawable = false;

    /**
     * Iterate over tiles on the slidingTile
     */
    private class SudokuIterator implements Iterator<Tile> {

        /**
         * The next tile.
         */
        private int next;

        /**
         * Check whether there is a next tile.
         *
         * @return a boolean that represent whether there is a next tile.
         */
        @Override
        public boolean hasNext() {
            return numTiles() > next;
        }

        /**
         * Return the next tile.
         *
         * @return the next tile
         */
        @Override
        public Tile next() {
            if (hasNext()) {
                int row = next / size;
                int col = next % size;
                next++;
                return tiles[row][col];
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * The number of level.
     */
    static final int size = 9;

    /**
     * The tiles on the slidingTile in row-major order.
     */
    private Tile[][] tiles = new Tile[size][size];

    /**
     * A new slidingTile of tiles in row-major order.
     * Precondition: len(tiles) == level * level
     *
     * @param tiles the tiles for the slidingTile
     */
    Sudoku(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != size; row++) {
            for (int col = 0; col != size; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the level.
     *
     * @return the size of slidingTile
     */
    //change from getLevel
    int getSize() {
        return size;
    }

    /**
     * Return the number of tiles on the slidingTile.
     *
     * @return the number of tiles on the slidingTile
     */
    private int numTiles() {
        return size * size;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

//    /**
//     * Swap the tiles at (row1, col1) and (row2, col2)
//     *
//     * @param row1 the first tile row
//     * @param col1 the first tile col
//     * @param row2 the second tile row
//     * @param col2 the second tile col
//     */
    // change from swapTile
    void writeNum(int row, int col, int move) {
        tiles[row][col] = new Tile(move);
        setChanged();
        notifyObservers();
    }

    /**
     * Convert a slidingTile to a String.
     *
     * @return a string representation of a slidingTile.
     */
    @Override
    public String toString() {
        return "Sudoku{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
}

