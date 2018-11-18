package fall2018.csc2017.GameCenter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * The Mine board.
 */
class MineBoard {
    /**
     * The x coordinate.
     */
    private int x;

    /**
     * The y coordinate.
     */
    private int y;
    /**
     * The board's number of cols.
     */
    private int boardCol;
    /**
     * The board's number of rows.
     */
    private int boardRow;
    /**
     * The board's number of booms.
     */
    private int numBoom;
    /**
     * The 2D array of tiles.
     */
    private MineTile[][] mineTile;
    /**
     * The mineTile's width.
     */
    private int tileWidth;
    /**
     * The colour of mineTile's number text.
     */
    private Paint tileNumberPaint;
    /**
     * The colour of booms.
     */
    private Paint boomPaint;
    /**
     * The colour of tiles that haven't been revealed.
     */
    private Paint tilePaint;
    /**
     * The lines for the board.
     */
    private Paint separationLinePaint;
    /**
     * The randomizer of the tiles(booms).
     */
    private Random randomize;
    /**
     * The board's width.
     */
    private int boardWidth;
    /**
     * The board's height.
     */
    private int boardHeight;
    /**
     * Whether booms are drawn.
     */
    boolean isDrawBooms = false;
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
     * Get the x coordinate.
     *
     * @return The x coordinate.
     */
    int getX() {
        return x;
    }
    /**
     * Get the y coordinate.
     *
     * @return The y coordinate.
     */
    int getY() {
        return y;
    }
    /**
     * Get the board's number of cols.
     *
     * @return The board's number of cols.
     */
    int getBoardCol() {
        return boardCol;
    }
    /**
     * Get the board's number of rows.
     *
     * @return The board's number of rows.
     */
    int getBoardRow() {
        return boardRow;
    }
    /**
     * Get the 2D array of tiles.
     *
     * @return The 2D array of tiles.
     */
    MineTile[][] getMineTile() {
        return mineTile;
    }
    /**
     * Get the mineTile's width.
     *
     * @return The mineTile's width.
     */
    int getTileWidth() {
        return tileWidth;
    }
    /**
     * Get the board's width.
     *
     * @return The board's width.
     */
    int getBoardWidth() {
        return boardWidth;
    }
    /**
     * Get the board's height.
     *
     * @return The board's height.
     */
    int getBoardHeight() {
        return boardHeight;
    }

    /**
     * Getter for number of booms.
     *
     * @return number of booms.
     */
    private int getNumBoom() {
        return numBoom;
    }

    /**
     * Getter for colour of tile number text.
     *
     * @return tileNumberPaint
     */
    private Paint getTileNumberPaint() {
        return tileNumberPaint;
    }

    /**
     * Getter for the colour of boom tiles.
     *
     * @return boomPaint
     */
    private Paint getBoomPaint() {
        return boomPaint;
    }

    /**
     * Getter for the colour of normal tiles.
     *
     * @return tilePaint
     */
    private Paint getTilePaint() {
        return tilePaint;
    }

    /**
     * Getter for the colour of separation lines.
     *
     * @return separationLinePaint
     */
    private Paint getSeparationLinePaint() {
        return separationLinePaint;
    }

    /**
     * Getter for the surrounding directions.
     *
     * @return surrounding_directions.
     */
    private int[][] getSurrounding_directions() {
        return surrounding_directions;
    }


    /**
     * Setter for Tile number Paint.
     */
    private void setTileNumberPaint() {
        tileNumberPaint = new Paint();
        tileNumberPaint.setAntiAlias(true);
        tileNumberPaint.setTextSize(MineGameActivity.Width / 9);
        tileNumberPaint.setColor(Color.RED);
    }

    /**
     * Setter for Boom Paint.
     */
    private void setBoomPaint() {
        boomPaint = new Paint();
        boomPaint.setAntiAlias(true);
        boomPaint.setColor(Color.DKGRAY);
    }

    /**
     * Setter for Tile Paint.
     */
    private void setTilePaint() {
        tilePaint = new Paint();
        tilePaint.setAntiAlias(true);
        tilePaint.setColor(Color.LTGRAY);
    }

    /**
     * Setter for Separation lines in the board.
     */
    private void setSeparationLinePaint() {
        separationLinePaint = new Paint();
        separationLinePaint.setAntiAlias(true);
        separationLinePaint.setColor(Color.BLACK);
        separationLinePaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * The constructor of Mine game.
     *
     * @param gameSettings The game settings for one game play.
     */
    MineBoard(ArrayList<Integer> gameSettings) {
        this.x = gameSettings.get(0);
        this.y = gameSettings.get(1);
        this.boardCol = gameSettings.get(2);
        this.boardRow = gameSettings.get(3);
        this.numBoom = gameSettings.get(4);
        this.tileWidth = gameSettings.get(5);
        boardWidth = boardCol * tileWidth;
        boardHeight = boardRow * tileWidth;
        setTileNumberPaint();
        setBoomPaint();
        setTilePaint();
        setSeparationLinePaint();
        mineTile = new MineTile[boardRow][boardCol];
        randomize = new Random();
    }

    /**
     * Create the board.
     */
    void createBoard() {
        for (int row = 0; row < boardRow; row++) {
            for (int col = 0; col < boardCol; col++) {
                mineTile[row][col] = new MineTile();
                mineTile[row][col].setValue(0);
                mineTile[row][col].setOpened(false);
                isDrawBooms = false;
            }
        }
    }

    /**
     * Generate booms.
     *
     * @param exception This position doesn't contain booms.
     */
    private void createBooms(MinePoint exception) {
        List<MinePoint> allPoint = new LinkedList<>();

        //Add all the positions.
        for (int row = 0; row < boardRow; row++) {
            for (int col = 0; col < boardCol; col++) {
                MinePoint minePoint = new MinePoint(col, row);
                if (!minePoint.equals(exception)) {
                    allPoint.add(minePoint);
                }
            }
        }
        List<MinePoint> boomPoint = new LinkedList<>();
        //Randomly generate booms.
        for (int i = 0; i < getNumBoom(); i++) {
            int idx = randomize.nextInt(allPoint.size());
            boomPoint.add(allPoint.get(idx));
            allPoint.remove(idx);
        }
        //Mark the position of booms.
        int boom = -1;
        for (MinePoint nextBoomPoint : boomPoint) {
            mineTile[nextBoomPoint.getY()][nextBoomPoint.getX()].setValue(boom);
        }
        //Add number to some tiles.
        for (int row = 0; row < boardRow; row++) {
            for (int col = 0; col < boardCol; col++) {
                int tile = this.mineTile[row][col].getValue();
                if (tile == boom) {
                    for (int k = 0; k < 8; k++) {
                        int surroundingX = col + getSurrounding_directions()[k][0],
                                surroundingY = row + getSurrounding_directions()[k][1];
                        if (surroundingX >= 0 && surroundingX < boardCol && surroundingY >= 0 &&
                                surroundingY < boardRow) {
                            int currentValue = this.mineTile[surroundingY][surroundingX].getValue();
                            if (currentValue != -1)
                                currentValue += 1;
                            this.mineTile[surroundingY][surroundingX].setValue(currentValue);
                        }
                    }
                }
            }
        }
    }

    /**
     * Tap to open some position.
     *
     * @param openMinePoint The point being isOpen.
     * @param tappedOnce First touch or not.
     */
    void touchOpen(MinePoint openMinePoint, boolean tappedOnce) {
        if (tappedOnce) {
            createBooms(openMinePoint);
        }
        mineTile[openMinePoint.getY()][openMinePoint.getX()].setOpened(true);
        if (mineTile[openMinePoint.getY()][openMinePoint.getX()].getValue() == -1)
            return;
            //tap the mineTile with a number.
        else if (mineTile[openMinePoint.getY()][openMinePoint.getX()].getValue() > 0) {
            return;
        }
        Queue<MinePoint> boomPointQueue = new LinkedList<>();
        //add the first point.
        boomPointQueue.offer(new MinePoint(openMinePoint.getX(), openMinePoint.getY()));
        //Search all 8 surroundings.
        while (boomPointQueue.size() != 0) {
            MinePoint minePoint = boomPointQueue.poll();
            assert minePoint != null;
            mineTile[minePoint.getY()][minePoint.getX()].setOpened(true);
            for (int i = 0; i < 8; i++) {
                int surroundingX = minePoint.getX() + getSurrounding_directions()[i][0],
                        surroundingY = minePoint.getY() + getSurrounding_directions()[i][1];
                //Check given minePoint's surroundings and whether they are opened or not.
                boolean isOpenable = surroundingX >= 0 && surroundingX < boardCol &&
                        surroundingY >= 0 && surroundingY < boardRow;
                if (isOpenable) {
                    //Make all surroundings that is not boom white new tiles with nothing in it.
                    if (mineTile[surroundingY][surroundingX].getValue() == 0 &&
                            !mineTile[surroundingY][surroundingX].isOpened()) {
                        boomPointQueue.offer(new MinePoint(surroundingX, surroundingY));
                        //Show the special tile.
                    } else if (mineTile[surroundingY][surroundingX].getValue() > 0) {
                        mineTile[surroundingY][surroundingX].setOpened(true);
                    }
                }
            }
        }
    }

    /**
     * Draw the board.
     *
     * @param canvas The drawing tool for the board.
     */
    void draw(Canvas canvas) {
        for (int row = 0; row < boardRow; row++) {
            for (int col = 0; col < boardCol; col++) {
                MineTile mineTile = this.mineTile[row][col];
                if (mineTile.isOpened()) {
                    if (mineTile.getValue() > 0) {
                        canvas.drawText(mineTile.getValue() + "", x + col * tileWidth,
                                y + row * tileWidth + tileWidth, getTileNumberPaint());
                    }
                } else {
                    //Draw rectangle tiles.
                    RectF reactF = new RectF(x + col * tileWidth, y + row * tileWidth,
                            x + col * tileWidth + tileWidth, y + row * tileWidth +
                            tileWidth);
                    canvas.drawRoundRect(reactF, 0, 0, getTilePaint());
                }
                //check all booms are drawn.
                if (isDrawBooms && this.mineTile[row][col].getValue() == -1) {
                    canvas.drawCircle((x + col * tileWidth) + tileWidth / 2,
                            (y + row * tileWidth) + tileWidth / 2,
                            tileWidth / 2, getBoomPaint());
                }
            }
        }
        //Draw the main frame.
        canvas.drawRect(x, y, x + boardWidth, y + boardHeight,
                getSeparationLinePaint());
        //Draw the horizontal lines.
        for (int row = 0; row < boardRow; row++) {
            canvas.drawLine(x, y + row * tileWidth, x + boardWidth,
                    y + row * tileWidth, getSeparationLinePaint());
        }
        //Draw the vertical lines.
        for (int col = 0; col < boardCol; col++) {
            canvas.drawLine(x + col * tileWidth, y, x + col * tileWidth,
                    y + boardHeight, getSeparationLinePaint());
        }
    }
}
