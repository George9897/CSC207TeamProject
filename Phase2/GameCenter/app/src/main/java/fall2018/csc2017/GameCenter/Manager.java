package fall2018.csc2017.GameCenter;

/**
 * The Manager interface for board games.
 */
public abstract class Manager {
    /**
     * @return the score of a game play through.
     */
    abstract int getScore();

    /**
     * @return Whether the board game is solved or not.
     */
    abstract boolean puzzleSolved();

//    /**
//     * The move maker for games.
//     */
//    abstract void makeMove(int position);
}
