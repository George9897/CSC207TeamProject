package fall2018.csc2017.GameCenter;

import java.io.Serializable;
import java.util.TimerTask;

public class SudokuScorer extends TimerTask implements Serializable {

    /**
     * The time score for one game play.
     */
    private int timeScore;

    /**
     * Getter for time score.
     * @return the Time score.
     */
    int getTimeScore() { return timeScore; }

    @Override
    public void run() { timeScore++; }
}
