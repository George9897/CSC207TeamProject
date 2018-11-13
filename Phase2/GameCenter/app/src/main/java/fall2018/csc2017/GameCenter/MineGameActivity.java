package fall2018.csc2017.GameCenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;

/**
 * The GameActivity for Mine.
 */
public class MineGameActivity extends Activity {

    /**
     * The width of the Mine board.
     */
    public static int Width;
    /**
     * The height of the Mine board.
     */
    public static int Height;

    /**
     * The generator of the GameActivity for Mine game.
     *
     * @param savedInstanceState The default parameter for onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Width = displayMetrics.widthPixels;
        Height = displayMetrics.heightPixels;

        setContentView(MineManager.getNewMineManager(this));
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Rules")
                .setMessage("Tap on those tiles that you think is not a boom，leave those " +
                        "boom tiles along! \n\n--Have Fun!")
                .setPositiveButton("Let the show start", null)
                .create()
                .show();
    }
}
