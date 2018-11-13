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
    int x;
    /**
     * The y coordinate.
     */
    int y;
    /**
     * The board's number of cols.
     */
    int boardCol;
    /**
     * The board's number of rows.
     */
    int boardRow;
    /**
     * The board's number of booms.
     */
    private int numBoom;
    /**
     * The mineTile's status : not boom.
     */
    private static short EMPTY = 0;
    /**
     * The mineTile's status : is boom.
     */
    private static short BOOM = -1;
    /**
     * The 2D array of tiles.
     */
    MineTile[][] mineTile;
    /**
     * The mineTile's width.
     */
    int tileWidth;
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
    int boardWidth;
    /**
     * The board's height.
     */
    int boardHeight;
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
            {1, -1}//lower-right
    };
    private final ArrayList<Integer> gameSettings;
    MineBoard lastMineBoard;


    /**
     * Getter for number of booms.
     * @return number of booms
     */
    private int getNumBoom() { return numBoom; }
    /**
     * Getter for colour of tile number text..
     * @return tileNumberPaint
     */
    private Paint getTileNumberPaint() { return tileNumberPaint; }
    /**
     * Getter for the colour of boom tiles.
     * @return boomPaint
     */
    private Paint getBoomPaint() { return boomPaint; }
    /**
     * Getter for the colour of normal tiles.
     * @return tilePaint
     */
    private Paint getTilePaint() { return tilePaint; }
    /**
     * Getter for the colour of separation lines.
     * @return separationLinePaint
     */
    private Paint getSeparationLinePaint() { return separationLinePaint; }
    /**
     * Getter for the surrounding directions.
     * @return surrounding_directions
     */
    private int[][] getSurrounding_directions() { return surrounding_directions; }

    private void setTileNumberPaint() {
        tileNumberPaint = new Paint();
        tileNumberPaint.setAntiAlias(true);
        tileNumberPaint.setTextSize(MineGameActivity.Width / 10);
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

//    /**
//     * Setter for Mine Paint.
//     */
//    private void setMinePaint(){
//        Paint minePaint = new Paint();
//        minePaint.setAntiAlias(true);
//        minePaint.setColor(0xffff981d);
//    }

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
        this.gameSettings = gameSettings;
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
//        setMinePaint();
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
                mineTile[row][col].value = EMPTY;
                mineTile[row][col].isOpen = false;
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
        List<MinePoint> allMinePoint = new LinkedList<>();

        for (int row = 0; row < boardRow; row++)//y
        {
            for (int col = 0; col < boardCol; col++)//x
            {
                MinePoint minePoint = new MinePoint(col, row);
                if (!minePoint.equals(exception)) {
                    allMinePoint.add(minePoint);
                }
            }
        }

        List<MinePoint> mineMinePoint = new LinkedList<>();
        //Randomly generate booms.
        for (int i = 0; i < getNumBoom(); i++) {
            int idx = randomize.nextInt(allMinePoint.size());
            mineMinePoint.add(allMinePoint.get(idx));
            allMinePoint.remove(idx);
        }

        //Mark the position of booms.
        for (MinePoint nextMinePoint : mineMinePoint) {
            mineTile[nextMinePoint.y][nextMinePoint.x].value = BOOM;
        }

        //Add number to some tiles.
        for (int row = 0; row < boardRow; row++)//y
        {
            for (int col = 0; col < boardCol; col++)//x
            {
                short tile = this.mineTile[row][col].value;
                if (tile == BOOM) {
                    for (int k = 0; k < 8; k++) {
                        int offsetX = col + getSurrounding_directions()[k][0],
                                offsetY = row + getSurrounding_directions()[k][1];
                        if (offsetX >= 0 && offsetX < boardCol && offsetY >= 0 &&
                                offsetY < boardRow) {
                            if (this.mineTile[offsetY][offsetX].value != -1)
                                this.mineTile[offsetY][offsetX].value += 1;
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
     * @param isFirst First touch or not.
     */
    void touchOpen(MinePoint openMinePoint, boolean isFirst) {
        lastMineBoard = this.copy();
        if (isFirst) {
            createBooms(openMinePoint);
        }

        mineTile[openMinePoint.y][openMinePoint.x].isOpen = true;
        if (mineTile[openMinePoint.y][openMinePoint.x].value == -1)
            return;
            //tap the mineTile with a number.
        else if (mineTile[openMinePoint.y][openMinePoint.x].value > 0)
        {
            return;
        }
        Queue<MinePoint> minePointQueue = new LinkedList<>();
        //add the first point.
        minePointQueue.offer(new MinePoint(openMinePoint.x, openMinePoint.y));
        //Search all 8 surroundings.
        while (minePointQueue.size() != 0) {
            MinePoint minePoint = minePointQueue.poll();
            assert minePoint != null;
            mineTile[minePoint.y][minePoint.x].isOpen = true;
            for (int i = 0; i < 8; i++) {
                int surroundingX = minePoint.x + getSurrounding_directions()[i][0],
                        surroundingY = minePoint.y + getSurrounding_directions()[i][1];
                //Check given minePoint's surroundings and whether they are opened or not.
                boolean isOpenable = surroundingX >= 0 && surroundingX < boardCol &&
                        surroundingY >= 0 && surroundingY < boardRow;
                if (isOpenable) {
                    //Make all surroundings that is not boom white new tiles with nothing in it.
                    if (mineTile[surroundingY][surroundingX].value == 0 &&
                            !mineTile[surroundingY][surroundingX].isOpen) {
                        minePointQueue.offer(new MinePoint(surroundingX, surroundingY));
                        //Show the special tile.
                    } else if (mineTile[surroundingY][surroundingX].value > 0) {
                        mineTile[surroundingY][surroundingX].isOpen = true;
                    }
                }

            }
        }

    }

    /**
     * Copy the current Mine board.
     */
    private MineBoard copy(){
        MineBoard newBoard = new MineBoard(this.gameSettings);
        newBoard.mineTile = this.mineTile.clone();
        newBoard.isDrawBooms = false;
        return newBoard;
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
                if (mineTile.isOpen) {
                    if (mineTile.value > 0) {
                        canvas.drawText(mineTile.value + "", x + col * tileWidth,
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
                if (isDrawBooms && this.mineTile[row][col].value == -1) {
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
        for (int i = 0; i < boardRow; i++) {
            canvas.drawLine(x, y + i * tileWidth, x + boardWidth,
                    y + i * tileWidth, getSeparationLinePaint());
        }
        //Draw the vertical lines.
        for (int i = 0; i < boardCol; i++) {
            canvas.drawLine(x + i * tileWidth, y, x + i * tileWidth,
                    y + boardHeight, getSeparationLinePaint());
        }

    }

}
