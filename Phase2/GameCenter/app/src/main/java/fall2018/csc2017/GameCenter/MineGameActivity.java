package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The GameActivity for Mine.
 */
public class MineGameActivity extends AppCompatActivity implements Observer, Serializable {

    /**
     * The mine game manager.
     */
    MineManager mineManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The GestureDetectGridView of this game.
     */
    private MineGestureDetectGridView gridView;

    /**
     * The width and height of column.
     */
    private int columnWidth, columnHeight;


    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    @SuppressLint("SetTextI18n")
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new MineCustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        loadFromFile(mineManager.getUserName() + "Mine.ser");
        mineManager = MineManager.getNewMineManager(this);
        createTileButtons(this);
        setContentView(R.layout.activity_mine_game);

        gridView = findViewById(R.id.minegrid);
        gridView.setNumColumns(MineBoard.getSize());
        gridView.setMineManager(mineManager);
        mineManager.getMineBoard().addObserver(MineGameActivity.this);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / MineBoard.getSize();
                        columnHeight = displayHeight / MineBoard.getSize();

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        mineManager = MineManager.getMineManager(this);
        tileButtons = new ArrayList<>();
        for (int row = 0; row < MineBoard.getSize(); row++) {
            for (int col = 0; col < MineBoard.getSize(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(mineManager.mineTiles.get(row*9 + col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        mineManager = MineManager.getMineManager(this);
        MineBoard mineBoard = mineManager.getMineBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / MineBoard.getSize();
            int col = nextPos % MineBoard.getSize();
            b.setBackgroundResource(mineBoard.getMineTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
    }

    /**
     * Load the mine manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                mineManager = (MineManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the mine manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(mineManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
