package fall2018.csc2017.GameCenter;

import java.io.Serializable;

import static java.lang.Math.pow;

/**
 * Calculate a score that round of game when game is finished.
 */
public class MineScore implements Serializable {

    /**
     * calculate score with given level and moves.
     *
     * @param numBooms level of difficulty
     * @param time number of move.
     * @return return the calculate result
     */
    static int calculateScore(int numBooms, int time) {
        if (time > 0) {
            return (int) ((1000 * numBooms * pow(0.995, time)));
        }
        return (1000 * numBooms);
    }
}
