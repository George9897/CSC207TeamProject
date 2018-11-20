package fall2018.csc2017.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Queue;
import java.util.Random;

/**
 * The Mine board.
 */
class MineBoard extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The board's number of booms.
     */
    private int numBoom;
    /**
     * The board's size.
     */
    private final static int size = 9;
    /**
     * The 2D array of tiles.
     */
    MineTile[][] mineTile = new MineTile[size][size];
    /**
     * The randomizer of the tiles(booms).
     */
    Random randomize;
    /**
     * Whether booms are drawn.
     */
    boolean isDrawBooms = false;
    /**
     * The surrounding_directions.
     */
    int[][] surrounding_directions = {
            {-1, 1},//upper-left
            {0, 1},//upper
            {1, 1},//upper-right
            {-1, 0},//left
            {1, 0},//right
            {-1, -1},//lower-left
            {0, -1},//lower
            {1, -1}};//lower-right

    public static int getSize() { return size; }

    /**
     * Get the 2D array of tiles.
     *
     * @return The 2D array of tiles.
     */
    MineTile[][] getMineTile() {
        return mineTile;
    }

    @NonNull
    @Override
    public Iterator iterator() {
        return new MineBoard.MineTileIterator();
    }
    /**
     * Iterate over tiles on the slidingTile
     */
    private class MineTileIterator implements Iterator<MineTile> {

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
        public MineTile next() {
            if (hasNext()) {
                int row = next / size;
                int col = next % size;
                next++;
                return mineTile[row][col];
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * A new Mine game board.
     * Precondition: len(mineTiles) == level * level
     *
     * @param tiles the tiles for the mine game.
     */
    MineBoard(List<MineTile> tiles, int numBoom, Random randomize) {
        this.numBoom = numBoom;
        this.randomize = randomize;
        Iterator<MineTile> iter = tiles.iterator();

        for (int row = 0; row != size; row++) {
            for (int col = 0; col != size; col++) {
                this.mineTile[row][col] = iter.next();
                this.mineTile[row][col].setX(col);
                this.mineTile[row][col].setY(row);
            }
        }
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    MineTile getMineTile(int row, int col) {
        return mineTile[row][col];
    }

    /**
     * Return the number of tiles on the mine game.
     *
     * @return the number of tiles on the mine game.
     */
    private int numTiles() { return size * size; }

    /**
     * Generate booms.
     *
     * @param exception This position doesn't contain booms.
     */
    public void createBooms(MineTile exception) {
        List<MineTile> allTile = new ArrayList<>();

        //Add all the positions.
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                allTile.add(mineTile[row][col]);
            }
        }
        List<MineTile> boomTile = new LinkedList<>();
        //Randomly generate booms.
        for (int i = 0; i < numBoom; i++) {
            int idx = randomize.nextInt(allTile.size());
            boomTile.add(allTile.get(idx));
            allTile.remove(idx);
        }
        //Mark the position of booms.
        for (MineTile nextBoomTile : boomTile) {
            mineTile[nextBoomTile.getY()][nextBoomTile.getX()].setValue(-1);
        }
        //Add number to some tiles.
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int tileNum = this.mineTile[row][col].getValue();
                if (tileNum == -1) {
                    for (int k = 0; k < 8; k++) {
                        int surroundingX = col + surrounding_directions[k][0],
                                surroundingY = row + surrounding_directions[k][1];
                        if (surroundingX >= 0 && surroundingX < size && surroundingY >= 0 &&
                                surroundingY < size) {
                            int currentValue = this.mineTile[surroundingY][surroundingX].getValue();
                            if (currentValue != -1)
                                currentValue += 1;
                            this.mineTile[surroundingY][surroundingX].setValue(currentValue);
                        }
                    }
                }
            }
        }
//        for (int row = 0; row < size; row++) {
//            for (int col = 0; col < size; col++) {
//                mineTile[row][col] = new MineTile(mineTile[row][col].getValue(), false);
//            }
//        }
        setChanged();
        notifyObservers();
    }

    /**
     * Tap to open some position.
     *
     * @param tappedOnce First touch or not.
     */
    void touchOpen(int position, boolean tappedOnce) {
        int row = position / MineBoard.getSize();
        int col = position % MineBoard.getSize();
        if (!tappedOnce) {
            createBooms(mineTile[row][col]);
            mineTile[row][col] = new MineTile(mineTile[row][col].getValue(), true);
        }
        else {
            mineTile[row][col] = new MineTile(mineTile[row][col].getValue(), true);
            if (mineTile[row][col].getValue() == -1) {
                isDrawBooms = true;
                for (int boomRow = 0; boomRow < size; boomRow++) {
                    for (int boomCol = 0; boomCol < size; boomCol++) {
                        if (mineTile[boomRow][boomCol].getValue() == -1) {
                            mineTile[boomRow][boomCol] = new MineTile(-1, true);
                        }
                    }
                }
            }
            //tap the mineTile with a number.
            else if (mineTile[row][col].getValue() > 0) {
                Queue<Pair<Integer, Integer>> queue = new LinkedList<>();

                for (int i = 0; i < 8; i++) {
                    int surroundingX = col + surrounding_directions[i][0],
                            surroundingY = row + surrounding_directions[i][1];
                    //Check given minePoint's surroundings and whether they are opened or not.
                    boolean isOpenable = surroundingX >= 0 && surroundingX < size &&
                            surroundingY >= 0 && surroundingY < size;
                    if (isOpenable) {
                        //Make all surroundings that is not boom white new tiles with nothing in it.
                        if (mineTile[surroundingY][surroundingX].getValue() == 0 &&
                                !mineTile[surroundingY][surroundingX].isOpened()) {
                                queue.offer(new Pair<>(surroundingY, surroundingX));
                                mineTile[surroundingY][surroundingX] = new MineTile(mineTile[surroundingY][surroundingX].getValue(), true);
                            //Show the special tile.
                        } else if (mineTile[surroundingY][surroundingX].getValue() > 0) {
                            mineTile[surroundingY][surroundingX] = new MineTile(mineTile[surroundingY][surroundingX].getValue(), true);
                        }
                    }
                }
                while (queue.size() != 0) {
                    Pair<Integer, Integer> pointPair = queue.poll();
                    mineTile[pointPair.first][pointPair.second].setOpened(true);
                    for (int i = 0; i < 8; i++) {
                        Integer surroundingX = col + surrounding_directions[i][0],
                                surroundingY = row + surrounding_directions[i][1];
                        //Check given minePoint's surroundings and whether they are opened or not.
                        boolean isOpenable = surroundingX >= 0 && surroundingX < size &&
                                surroundingY >= 0 && surroundingY < size;
                        if (isOpenable) {
                            //Make all surroundings that is not boom white new tiles with nothing in it.
                            if (mineTile[surroundingY][surroundingX].getValue() == 0 &&
                                    !mineTile[surroundingY][surroundingX].isOpened()) {
                                mineTile[surroundingY][surroundingX]
                                        = new MineTile(mineTile[surroundingY][surroundingX].getValue(),
                                        true);
                                queue.offer(new Pair<>(surroundingY, surroundingX));
                                //Show the special tile.
                            } else if (mineTile[surroundingY][surroundingX].getValue() > 0) {
                                mineTile[surroundingY][surroundingX]
                                        = new MineTile(mineTile[surroundingY][surroundingX].getValue(),
                                        true);
                            }
                        }
                    }
                }
            }
        }
        setChanged();
        notifyObservers();
    }
}
