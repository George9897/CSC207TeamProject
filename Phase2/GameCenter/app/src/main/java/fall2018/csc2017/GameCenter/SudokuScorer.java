package fall2018.csc2017.GameCenter;

import java.io.Serializable;

public class SudokuScorer extends Scorer implements Serializable{

    /**
     * calculate score with given level and moves.
     *
     * @param level level of difficulty
     * @param timeScore number of move.
     * @return return the calculate result
     */
    @Override
    public int calculateScore(int level, int timeScore) {
        return ((500 * level - timeScore));
    }
}
