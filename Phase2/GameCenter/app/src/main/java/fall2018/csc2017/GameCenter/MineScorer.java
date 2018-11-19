package fall2018.csc2017.GameCenter;

import java.io.Serializable;
import java.util.TimerTask;

import static java.lang.Math.pow;

/**
 * Calculate a score that round of game when game is finished.
 */
public class MineScorer extends Scorer {

//    /**
//     * The time score for one game play.
//     */
//    private int timeScore;

    /**
     * calculate score with given level and moves.
     *
     * @param numBooms level of difficulty
     * @param time number of move.
     * @return return the calculate result
     */
    @Override
    public int calculateScore(int numBooms, int time) {
        if (time > 0) {
            return (int) ((1000 * numBooms * pow(0.995, time)));
        }
        return (1000 * numBooms);
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
