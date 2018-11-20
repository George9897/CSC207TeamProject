package fall2018.csc2017.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
class MineBoard extends Observable implements Serializable, Iterable<MineTile> {
    /**
     * The board's number of booms.
     */
    private int numBoom;
    /**
     * The board's size.
     */
    private final static int size = 16;
    /**
     * The 2D array of tiles.
     */
    private MineTile[][] mineTile = new MineTile[size][size];
    /**
     * The randomizer of the tiles(booms).
     */
    private Random randomize;
    /**
     * The surrounding_directions.
     */
    private int[][] surrounding_directions = {
            {-1, 1},//upper-left
            {0, 1},//upper
            {1, 1},//upper-right
            {-1, 0},//left
            {1, 0},//right
            {-1, -1},//lower-left
            {0, -1},//lower
            {1, -1}};//lower-right

    /**
     * @return the size of the board.
     */
    public static int getSize() {
        return size;
    }

    /**
     * Get the 2D array of tiles.
     *
     * @return The 2D array of tiles.
     */
    MineTile[][] getMineTiles() {
        return mineTile;
    }

    /**
     * Return the tile at (row, col).
     *
     * @param row the tile row.
     * @param col the tile column.
     * @return the tile at (row, col).
     */
    MineTile getMineTiles(int row, int col) {
        return mineTile[row][col];
    }

    /**
     * Get the iterator for tiles.
     *
     * @return the iterator for tiles.
     */
    @NonNull
    @Override
    public Iterator<MineTile> iterator() {
        return new MineBoard.MineTileIterator();
    }

    /**
     * Iterate over tiles on the slidingTile.
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

        for (int col = 0; col != size; col++) {
            for (int row = 0; row != size; row++) {
                this.mineTile[row][col] = iter.next();
                this.mineTile[row][col].setX(row);
                this.mineTile[row][col].setY(col);
            }
        }
    }

    /**
     * Return the number of tiles on the mine game.
     *
     * @return the number of tiles on the mine game.
     */
    private int numTiles() {
        return size * size;
    }

    /**
     * Generate booms.
     *
     * @param exception This position doesn't contain booms.
     */
    private void createBooms(MineTile exception) {
        List<MineTile> allTile = new ArrayList<>();

        //Add all the positions.
        for (int row = 0; row < size; row++) {
            allTile.addAll(Arrays.asList(mineTile[row]).subList(0, size));
        }
        List<MineTile> boomTile = new LinkedList<>();
        //Randomly generate booms.
        for (int i = 0; i < numBoom; i++) {
            int idx = randomize.nextInt(allTile.size());
            boomTile.add(allTile.get(idx));
            allTile.remove(idx);
        }
        //In order to make sure that the first tap is not a boom.
        if (!allTile.contains(exception)) {
            allTile.add(exception);
            boomTile.remove(exception);
        }
        //Mark the position of booms.
        for (MineTile nextBoomTile : boomTile) {
            mineTile[nextBoomTile.getX()][nextBoomTile.getY()].setValue(-1);
        }
        //Add number to some tiles.
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int tileNum = this.mineTile[row][col].getValue();
                if (tileNum == -1) {
                    for (int k = 0; k < 8; k++) {
                        int surroundingX = row + surrounding_directions[k][0],
                                surroundingY = col + surrounding_directions[k][1];
                        if (surroundingX >= 0 && surroundingX < size && surroundingY >= 0 &&
                                surroundingY < size) {
                            int currentValue = this.mineTile[surroundingX][surroundingY].getValue();
                            if (currentValue != -1)
                                currentValue += 1;
                            this.mineTile[surroundingX][surroundingY].setValue(currentValue);
                        }
                    }
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Tap to open some position.
     *
     * @param firstTap First touch or not.
     */
    void touchOpen(int position, boolean firstTap) {
        int row = position / MineBoard.getSize();
        int col = position % MineBoard.getSize();
        if (firstTap) {
            createBooms(mineTile[row][col]);
        }
        replaceToTrue(row, col);
        if (mineTile[row][col].getValue() == -1) {
            displayAllBoom();
        }
        //tap the mineTile with a number.
        else if (mineTile[row][col].getValue() == 0) {
            Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
            queue = putSurroundingOnQueue(row, col, queue);
            recursiveSurroundingOnQueue(queue);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * @param row The row of the mine tile that needs to be replaced.
     * @param col The col of the mine tile that needs to be replaced.
     */
    private void replaceToTrue(int row, int col) {
        mineTile[row][col] = new MineTile(mineTile[row][col].getValue(), true);
    }

    /**
     * Display all boom when the user failed.
     */
    private void displayAllBoom() {
        for (int boomRow = 0; boomRow < size; boomRow++) {
            for (int boomCol = 0; boomCol < size; boomCol++) {
                if (mineTile[boomRow][boomCol].getValue() == -1) {
                    replaceToTrue(boomRow, boomCol);
                }
            }
        }
    }

    /**
     * Recursively put surrounding empty mine tiles into the queue.
     *
     * @param queue the queue that stores all the surrounding empty mine tiles.
     *              Note: the "surrounding" might recursively gets bigger until there is a number
     *              tile border.
     */
    private void recursiveSurroundingOnQueue
    (Queue<Pair<Integer, Integer>> queue) {
        if (queue.size() != 0) {
            Pair<Integer, Integer> pointPair = queue.poll();
            int row = pointPair.first;
            int col = pointPair.second;
            replaceToTrue(row, col);
            putSurroundingOnQueue(row, col, queue);
            recursiveSurroundingOnQueue(queue);
        }
    }

    /**
     * Put the surrounding empty mine tiles in the queue.
     * Helper for recursiveSurroundingOnQueue.
     *
     * @param row   The row of the mine tile.
     * @param col   The col of the mine tile.
     * @param queue The queue of empty mine tiles.
     * @return The modified queue.
     */
    private Queue<Pair<Integer, Integer>> putSurroundingOnQueue
    (int row, int col, Queue<Pair<Integer, Integer>> queue) {
        for (int i = 0; i < 8; i++) {
            Integer surroundingX = row + surrounding_directions[i][0],
                    surroundingY = col + surrounding_directions[i][1];
            //Check given minePoint's surroundings and whether they are opened or not.
            boolean isOpenable = surroundingX >= 0 && surroundingX < size &&
                    surroundingY >= 0 && surroundingY < size;
            if (isOpenable) {
                //Make all surroundings empty tiles appear inside a number tile border.
                if (mineTile[surroundingX][surroundingY].getValue() == 0 &&
                        !mineTile[surroundingX][surroundingY].getIsOpened()) {
                    replaceToTrue(surroundingX, surroundingY);
                    queue.offer(new Pair<>(surroundingX, surroundingY));
                    //Show the number tile.
                } else if (mineTile[surroundingX][surroundingY].getValue() > 0) {
                    replaceToTrue(surroundingX, surroundingY);
                }
            }
        }
        return queue;
    }
}
