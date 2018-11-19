package fall2018.csc2017.GameCenter;

import java.io.Serializable;
import java.util.TimerTask;

import static java.lang.Math.pow;

public class SudokuScorer extends Scorer{

//    /**
//     * The time score for one game play.
//     */
//    private int timeScore;

    /**
     * calculate score with given level and moves.
     *
     * @param level level of difficulty
     * @param timeScore number of move.
     * @return return the calculate result
     */
    @Override
    public int calculateScore(int level, int timeScore) {
        return (int) ((500 * level - timeScore));
    }
//
//    /**
//     * Getter for time score.
//     * @return the Time score.
//     */
//    int getTimeScore() { return timeScore; }
//
//    @Override
//    public void run() { timeScore++; }
}
