package fall2018.csc2017.GameCenter;

import java.io.Serializable;
import java.util.TimerTask;

import static java.lang.Math.pow;

/**
 * Calculate a score that round of game when game is finished.
 */
public class MineScorer extends TimerTask implements Serializable {

    private int timeScore;

    /**
     * calculate score with given level and moves.
     *
     * @param numBooms level of difficulty
     * @param time number of move.
     * @return return the calculate result
     */
    private int calculateScore(int numBooms, int time) {
        if (time > 0) {
            return (int) ((1000 * numBooms * pow(0.995, time)));
        }
        return (1000 * numBooms);
    }

    public void resetTimer(){
        this.timeScore = 0;
    }

    public int getTimeScore() {
        return timeScore;
    }

    public int getFinalScore(int numBooms) {
        return calculateScore(numBooms, timeScore);
    }

    @Override
    public void run() {
        timeScore++;
    }
}
