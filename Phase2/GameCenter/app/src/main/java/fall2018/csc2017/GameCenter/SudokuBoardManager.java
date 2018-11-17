package fall2018.csc2017.GameCenter;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SudokuBoardManager implements Serializable {
    /**
     * The serialVersionUID.
     */
    //TODO: don't know for now
    //public static final long serialVersionUID = 7738996747003692034L;

    /**
     * The slidingTile being managing
     */
    private Sudoku sudoku;

    /**
     * The boardManager.
     */
    private static SudokuBoardManager sudokuBoardManager;

    /**
     * The number of moves taken by the users.
     */
    private int numMoves;

    /**
     * The list of storing Boards.
     */
    private List<Integer> listOfPosition;

    private static int[] sudokuNum = new int[81];
    private static Random random = new Random();

    /**
     * The AccountManager.
     */
    private AccountManager accountManager = AccountManager.getAccountManager();

    /**
     * The user's name.
     */
    String userName = accountManager.getUserName();

    /**
     * The context used to connect to activity.
     */
    private transient Context context;

    /**
     * After score for one game round.
     */
    private int score;

    /**
     * After time for one game round.
     */
    private int time;

    /**
     * Getter for score.
     *
     * @return score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for time.
     *
     * @return time.
     */
    public int getTime() {
        return time;
    }

    /**
     * Getter for slidingTile.
     *
     * @return the last slidingTile get stored.
     */
    // change from getSlidingTile
    Sudoku getSudoku() {
        return this.sudoku;
    }

    int move;


    /**
     * Create a initial list of Tiles for game with matching sizes.
     *
     * @return list of Tiles.
     */
    private List CreateTiles() {
        createRandomSudoku();
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Sudoku.size * Sudoku.size;
        for (int i = 0; i != numTiles; i++) {
            tiles.add(new Tile(sudokuNum[i], false));
        }
        return tiles;
    }

    /**
     * Constructor for BoardManager.
     */
    private SudokuBoardManager(Context context) {
        this.context = context;
        if (this.listOfPosition == null) {
            this.listOfPosition = new ArrayList<>();
            List tiles = CreateTiles();
            this.sudoku = new Sudoku(tiles);
        }
    }

    /**
     * Getter for singleton BoardManager.
     *
     * @param context The context used for connecting activity.
     * @return The singleton BoardManager.
     */
    static SudokuBoardManager getSudokuBoardManager(Context context) {
        if (sudokuBoardManager == null) {
            sudokuBoardManager = new SudokuBoardManager(context);
        }
        return sudokuBoardManager;
    }

    /**
     * Destroy current BoardManager.
     */
    static void destroySudokuBoardManager() {
        sudokuBoardManager = null;
    }

    /**
     * Add a modified SlidingTile in the list of boards.
     *
     * @param position The position that was a blank tile.
     */
    private void addPosition(int position) {
        listOfPosition.add(position);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        int[] sudokuNum = new int[Sudoku.size * Sudoku.size];
        Iterator<Tile> iter = sudoku.iterator();
        int acc = 0;
        while (iter.hasNext()) {
            Tile next = iter.next();
            sudokuNum[acc] = next.getId();
            acc++;
        }
        return checkCol(sudokuNum) && checkRow(sudokuNum) && checkSquare(sudokuNum);
        //TODO: calculate score
//        if (solved) {
//            score = calculateScore(sudoku.getSlidingTile().getLevel(), boardManager.getNumMoves());
//            undoLimit = 0;
//            ScoreBoard scoreBoard = ScoreBoard.getScoreBoard(context);
//            scoreBoard.update(SlidingTile.level, userName, score);
//            scoreBoard.updateHighestScore();
//        }
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {
        int row = position / Sudoku.size;
        int col = position % Sudoku.size;
        return sudoku.getTile(row, col).getId() == 0 || sudoku.getTile(row, col).getId() > 100;
    }

    /**
     * Process a touch at position in the slidingTile, swapping tiles as appropriate.
     *
     * @param position the position
     */
    // change from touchMove
    void makeMove(int position) {

        int row = position / Sudoku.size;
        int col = position % Sudoku.size;
        if (isValidTap(position)) {
            sudoku.writeNum(row, col, move);
        }
    }

    private boolean checkCol(int[] sudokuNum) {
        for (int i = 0; i < Sudoku.size; i++) {
            List<Integer> col = new ArrayList<>();
            for (int j = 0; j < Sudoku.size; j++) {
                col.add(sudokuNum[i * Sudoku.size + j]);
            }
            for (int check = 1; check <= Sudoku.size; check++) {
                if (!col.contains(check)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkRow(int[] sudokuNum) {
        for (int i = 0; i < Sudoku.size; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < Sudoku.size; j++) {
                row.add(sudokuNum[j * Sudoku.size + i]);
            }
            for (int check = 1; check <= Sudoku.size; check++) {
                if (!row.contains(check)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkSquare(int[] sudokuNum) {
        for (int position = 0; position < Sudoku.size * Sudoku.size; position++) {
            List<Integer> square = new ArrayList<>();
            int row = position / Sudoku.size;
            int col = position % Sudoku.size;
            int xOff = row / 3 * 3;
            int yOff = col / 3 * 3;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    square.add(sudokuNum[(xOff + i) * 9 + yOff + j]);
                }
            }
            if (position % 9 == 8) {
                for (int check = 1; check <= Sudoku.size; check++) {
                    if (!square.contains(check)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void createRandomSudoku() {
        randomChoose(0);
        int rand = random.nextInt(sudokuNum.length);
        // TODO: Easy, Medium, Hard
        int difficulty = 20;
        int[] x = new int[Sudoku.size * Sudoku.size];
        for (int i = 0; i < Sudoku.size * Sudoku.size; i++) {
            x[i] = i;
        }
        for (int i = 0; i < difficulty; i++) {
            int randomNum = random.nextInt(Sudoku.size * Sudoku.size - i) + i;
            int temp = x[i];
            x[i] = x[randomNum];
            x[randomNum] = temp;
            sudokuNum[x[i]] = 0;
        }
    }

    private boolean randomChoose(int position) {
        if (position == Sudoku.size * Sudoku.size) {
            return true;
        } else if (sudokuNum[position] != 0) {
            return randomChoose(position + 1);
        } else {
            int[] randOrder = new int[Sudoku.size];
            for (int val = 0; val < Sudoku.size; val++) {
                randOrder[val] = val + 1;
            }
            for (int val = 0; val < Sudoku.size; val++) {
                int rand = random.nextInt(Sudoku.size);
                int tmp = randOrder[rand];
                randOrder[rand] = randOrder[val];
                randOrder[val] = tmp;
            }

            for (int val = 0; val < Sudoku.size; val++) {
                if (isLegal(position, randOrder[val])) {
                    sudokuNum[position] = randOrder[val];
                    if (randomChoose(position + 1)) {
                        return true;
                    }
                }
            }
        }
        sudokuNum[position] = 0;
        return false;
    }

    private boolean isLegal(int position, int value) {
        int row = position / Sudoku.size;
        int col = position % Sudoku.size;
        int xOff = row / 3 * 3;
        int yOff = col / 3 * 3;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (value == sudokuNum[(xOff + x) * 9 + yOff + y]) {
                    return false;
                }
            }
        }
        for (int val = 0; val < Sudoku.size; val++) {
            if (value == sudokuNum[row * Sudoku.size + val]) {
                return false;
            }
        }
        for (int val = 0; val < Sudoku.size; val++) {
            if (value == sudokuNum[val * Sudoku.size + col]) {
                return false;
            }
        }

        return true;
    }

    void clear() {
        for (int i = 0; i < Sudoku.size; i++) {
            for (int j = 0; j < Sudoku.size; j++) {
                if (sudoku.tiles[i][j].getId() > 100) {
                    sudoku.writeNum(i, j, 0);
                }
            }
        }
    }

//    private int[][] randomChoose(int[][] sudokuNum, List<Integer> list, int position){
//        int row = position / Sudoku.size;
//        int col = position % Sudoku.size;
//        int xOff = row / 3 * 3;
//        int yOff = col / 3 * 3;
//        List<Integer> possibleNum = list;
//        int[][] result = sudokuNum;
//        for (int otherRow = 0; otherRow < Sudoku.size; otherRow++){
//            for (int i = 0; i < possibleNum.size(); i++){
//                if (possibleNum.get(i) == sudokuNum[otherRow][col]){
//                    possibleNum.remove(i);
//                }
//            }
//        }
//        //check col
//        for (int otherCol = 0; otherCol < Sudoku.size; otherCol++){
//            for (int i = 0; i < possibleNum.size(); i++){
//                if (possibleNum.get(i) == sudokuNum[row][otherCol]){
//                    possibleNum.remove(i);
//                }
//            }
//        }
//        //check square
//        for (int squareX = 0; squareX < Sudoku.size/3; squareX++){
//            for (int squareY = 0; squareY < Sudoku.size/3; squareY++){
//                for (int i = 0; i < possibleNum.size(); i++){
//                    if (possibleNum.get(i) == sudokuNum[xOff+squareX][yOff+squareY]){
//                        possibleNum.remove(i);
//                    }
//                }
//            }
//        }
//        if(possibleNum.isEmpty()) {
//            result = createRandomSudoku();
//        }else {
//            int random=(int)(possibleNum.size()*Math.random());
//            result[row][col] = possibleNum.get(random);
//        }
//        return result;
//    }

    // don't need undo
//    /**
//     * Undo the previous moves as required properly.
//     */
//    void undo() {
//        if (undoLimit > 0) {
//            numMoves -= 2;
//            undoLimit -= 2;
//            touchMove(listOfPosition.remove(listOfPosition.size() - 1));
//            listOfPosition.remove(listOfPosition.size() - 1);
//        }
//    }
//
//    /**
//     * Undo the previous move(can only be used 3 times).
//     */
//    void undo3() {
//        if (undoLimit3 > 0) {
//            numMoves -= 2;
//            undoLimit3 -= 1;
//            touchMove(listOfPosition.remove(listOfPosition.size() - 1));
//            listOfPosition.remove(listOfPosition.size() - 1);
//        }
//    }
}
