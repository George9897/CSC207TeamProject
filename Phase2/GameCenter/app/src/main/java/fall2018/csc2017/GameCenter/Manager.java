package fall2018.csc2017.GameCenter;


/**
 * The Manager interface for board games.
 */
public interface Manager {
    /**
     * @return the score of a game play through.
     */
    int getScore();

    /**
     * @return Whether the board game is solved or not.
     */
    boolean puzzleSolved();

    /**
     * The move maker for games.
     */
    void makeMove(int position);
}
