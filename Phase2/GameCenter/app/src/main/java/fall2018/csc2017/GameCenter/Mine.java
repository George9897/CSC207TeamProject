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
public class Mine {
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
    public static short EMPTY = 0;
    /**
     * The mineTile's status : is boom.
     */
    public static short BOOM = -1;
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
    private Random randomize = new Random();
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

    /**
     * The constructor of Mine game.
     *
     * @param gameSettings The game settings for one game play.
     */
    Mine(ArrayList<Integer> gameSettings) {
        this.x = gameSettings.get(0);
        this.y = gameSettings.get(1);
        this.boardCol = gameSettings.get(2);
        this.boardRow = gameSettings.get(3);
        this.numBoom = gameSettings.get(4);
        this.tileWidth = gameSettings.get(5);
        boardWidth = boardCol * tileWidth;
        boardHeight = boardRow * tileWidth;

        tileNumberPaint = new Paint();
        tileNumberPaint.setAntiAlias(true);
        tileNumberPaint.setTextSize(MineGameActivity.Width / 10);
        tileNumberPaint.setColor(Color.RED);

        boomPaint = new Paint();
        boomPaint.setAntiAlias(true);
        boomPaint.setColor(Color.DKGRAY);

        tilePaint = new Paint();
        tilePaint.setAntiAlias(true);
        tilePaint.setColor(0xff1faeff);

        Paint minePaint = new Paint();
        minePaint.setAntiAlias(true);
        minePaint.setColor(0xffff981d);

        separationLinePaint = new Paint();
        separationLinePaint.setAntiAlias(true);
        separationLinePaint.setColor(0xff000000);
        separationLinePaint.setStyle(Paint.Style.STROKE);

        mineTile = new MineTile[boardRow][boardCol];
    }

    /**
     * Initialize the board.
     */
    void init() {
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
    public void create(MinePoint exception) {
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
        for (int i = 0; i < numBoom; i++) {
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
                        int offsetX = col + surrounding_directions[k][0], offsetY = row + surrounding_directions[k][1];
                        if (offsetX >= 0 && offsetX < boardCol && offsetY >= 0 && offsetY < boardRow) {
                            if (this.mineTile[offsetY][offsetX].value != -1)
                                this.mineTile[offsetY][offsetX].value += 1;
                        }
                    }
                }
            }
        }

    }


    /**
     * Tap to isOpen some position.
     *
     * @param openMinePoint The point being isOpen.
     * @param isFirst   First touch or not.
     */
    void touchOpen(MinePoint openMinePoint, boolean isFirst) {
        if (isFirst) {
            create(openMinePoint);
        }

        mineTile[openMinePoint.y][openMinePoint.x].isOpen = true;
        if (mineTile[openMinePoint.y][openMinePoint.x].value == -1)
            return;
        else if (mineTile[openMinePoint.y][openMinePoint.x].value > 0)//tap the mineTile with a number.
        {
            return;
        }
        Queue<MinePoint> minePointQueue = new LinkedList<>();
        //add the first point.
        minePointQueue.offer(new MinePoint(openMinePoint.x, openMinePoint.y));

        //Search all 8 surroundings.
        for (int i = 0; i < 8; i++) {
            int offsetX = openMinePoint.x + surrounding_directions[i][0], offsetY = openMinePoint.y + surrounding_directions[i][1];
            //判断越界和是否已访问
            boolean isCan = offsetX >= 0 && offsetX < boardCol && offsetY >= 0 && offsetY < boardRow;
            if (isCan) {
                if (mineTile[offsetY][offsetX].value == 0 && !mineTile[offsetY][offsetX].isOpen) {
                    minePointQueue.offer(new MinePoint(offsetX, offsetY));
                } else if (mineTile[offsetY][offsetX].value > 0) {
                    mineTile[offsetY][offsetX].isOpen = true;
                }
            }

        }

        while (minePointQueue.size() != 0) {
            MinePoint minePoint = minePointQueue.poll();
            assert minePoint != null;
            mineTile[minePoint.y][minePoint.x].isOpen = true;
            for (int i = 0; i < 8; i++) {
                int offsetX = minePoint.x + surrounding_directions[i][0], offsetY = minePoint.y + surrounding_directions[i][1];
                //Check given minePoint is offset.
                boolean isCan = offsetX >= 0 && offsetX < boardCol && offsetY >= 0 && offsetY < boardRow;
                if (isCan) {
                    if (mineTile[offsetY][offsetX].value == 0 && !mineTile[offsetY][offsetX].isOpen) {
                        minePointQueue.offer(new MinePoint(offsetX, offsetY));
                    } else if (mineTile[offsetY][offsetX].value > 0) {
                        mineTile[offsetY][offsetX].isOpen = true;
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
                if (mineTile.isOpen) {
                    if (mineTile.value > 0) {
                        canvas.drawText(mineTile.value + "", x + col * tileWidth, y + row * tileWidth + tileWidth, tileNumberPaint);
                    }

                } else {
                    //Draw rectangle tiles.
                    RectF reactF = new RectF(x + col * tileWidth, y + row * tileWidth, x + col * tileWidth + tileWidth, y + row * tileWidth + tileWidth);
                    canvas.drawRoundRect(reactF, 0, 0, tilePaint);
                }
                //check all booms are drawn.
                if (isDrawBooms && this.mineTile[row][col].value == -1) {
                    canvas.drawCircle((x + col * tileWidth) + tileWidth / 2, (y + row * tileWidth) + tileWidth / 2, tileWidth / 2, boomPaint);
                }
            }
        }
        //Draw the main frame.
        canvas.drawRect(x, y, x + boardWidth, y + boardHeight, separationLinePaint);
        //Draw the horizontal lines.
        for (int i = 0; i < boardRow; i++) {
            canvas.drawLine(x, y + i * tileWidth, x + boardWidth, y + i * tileWidth, separationLinePaint);
        }
        //Draw the vertical lines.
        for (int i = 0; i < boardCol; i++) {
            canvas.drawLine(x + i * tileWidth, y, x + i * tileWidth, y + boardHeight, separationLinePaint);
        }

    }

}
