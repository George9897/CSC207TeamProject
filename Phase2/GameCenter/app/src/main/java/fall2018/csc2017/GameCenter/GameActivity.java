package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer, Serializable {

    /**
     * The slidingTile manager.
     */
    BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The method state of undo button.
     */
    protected boolean undo;

    /**
     * The GestureDetectGridView of this game.
     */
    private GestureDetectGridView gridView;

    /**
     * The width and height of column.
     */
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    @SuppressLint("SetTextI18n")
    public void display() {
        updateTileButtons();
        TextView steps = findViewById(R.id.step);
        steps.setText("Step:" + Integer.toString(boardManager.getNumMoves()));
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = BoardManager.getBoardManager(this);
        loadFromFile(boardManager.userName + ".ser");
        createTileButtons(this);
        setContentView(R.layout.activity_main);
        addQuitButtonsListener();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            undo = intent.getExtras().getBoolean("undo");
        }
        System.out.println(undo);

        if (undo) {
            addUndoButtonListener();
        } else {
            addUndo3ButtonListener();
        }

        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(SlidingTile.level);
        gridView.setBoardManager(boardManager);
        boardManager.getSlidingTile().addObserver(GameActivity.this);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / SlidingTile.level;
                        columnHeight = displayHeight / SlidingTile.level;

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
        boardManager = BoardManager.getBoardManager(this);
        SlidingTile slidingTile = boardManager.getSlidingTile();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != SlidingTile.level; row++) {
            for (int col = 0; col != SlidingTile.level; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(slidingTile.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        boardManager = BoardManager.getBoardManager(this);
        SlidingTile slidingTile = boardManager.getSlidingTile();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / SlidingTile.level;
            int col = nextPos % SlidingTile.level;
            b.setBackgroundResource(slidingTile.getTile(row, col).getBackground());
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
     * Load the slidingTile manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
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
     * Save the slidingTile manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Activate the unlimited undo button.
     */
    @SuppressLint("SetTextI18n")
    private void addUndoButtonListener() {
        Button undo_Button = findViewById(R.id.eight);
        undo_Button.setOnClickListener((v) -> {
            boardManager.undo();
            display();
            if (boardManager.getUndoLimit() == 0) {
                Toast.makeText(this, "NOT UNDOABLE!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Activate the limited undo3 button.
     */
    @SuppressLint("SetTextI18n")
    private void addUndo3ButtonListener() {
        Button undo_Button = findViewById(R.id.eight);
        undo_Button.setOnClickListener((v) -> {
            boardManager.undo3();
            display();
            if (boardManager.getUndoLimit3() == 0) {
                Toast.makeText(this, "NOT UNDOABLE!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Activate the buttons for Quit.
     */
    private void addQuitButtonsListener() {
        Button quitButton = findViewById(R.id.nine);
        quitButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, StartingActivity.class);
            startActivity(tmp);
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
