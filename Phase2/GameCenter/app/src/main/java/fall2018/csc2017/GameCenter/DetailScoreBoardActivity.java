package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class DetailScoreBoardActivity extends AppCompatActivity implements Serializable {

    /**
     * The boardManager that is used in that round.
     */
    private DetailScoreBoard detailScoreBoard;

    private String gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_score_board);
        Intent intent = getIntent();
        gameType = intent.getStringExtra("gameTypeWantedToSee");

        getGameType();
        String filename = gameType + "ScoreBoard.ser";
        loadFromFile(filename);
        if (detailScoreBoard == null){
            detailScoreBoard = DetailScoreBoard.getDetailScoreBoard(gameType, this);
        }
        detailScoreBoard.display();
        TextView gameView = findViewById(R.id.GameView);
        gameView.setText(gameType);
        setTopOnes(detailScoreBoard);
        setModeData(detailScoreBoard);

        addScoreQuitButtonsListener();
        detailScoreBoard.destroyDetailScoreBoard();
        saveToFile(filename);
    }

    /**
     * Load the user account from fileName.
     *
     * @param fileName the save file which contains the dictionary of username and password
     */
    void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                detailScoreBoard = (DetailScoreBoard) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: "
                    + e.toString());
        }
    }


    /**
     * Save the user account to fileName.
     *
     * @param fileName the save file which contains the dictionary of username and password
     */
    private void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(detailScoreBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        if (!gameType.equals("")) {
            detailScoreBoard.destroyDetailScoreBoard();
        }
        super.onBackPressed();
    }

    private void getGameType(){

    }

    private void setTopOnes(DetailScoreBoard scoreBoard){
        TextView easyTopOne = findViewById(R.id.easyTopOne);
        TextView mediumTopOne = findViewById(R.id.mediumTopOne);
        TextView hardTopOne = findViewById(R.id.hardTopOne);

        easyTopOne.setText(scoreBoard.getEasyTopOne());
        mediumTopOne.setText(scoreBoard.getMediumTopOne());
        hardTopOne.setText(scoreBoard.getHardTopOne());
    }

    private void setModeData(DetailScoreBoard scoreBoard){
        TextView easyData = findViewById(R.id.easyTextView);
        TextView mediumData = findViewById(R.id.mediumTextView);
        TextView hardData = findViewById(R.id.hardTextView);

        easyData.setText(othersToString(scoreBoard.getEasySortedList()));
        mediumData.setText(othersToString(scoreBoard.getMediumSortedList()));
        hardData.setText(othersToString(scoreBoard.getHardSortedList()));
    }

    private String othersToString(List<String> sortedOthers){
        StringBuilder result = new StringBuilder();
        if (sortedOthers.size() > 0) {
            for (int i = 0; i < sortedOthers.size(); i++) {
                result.append(sortedOthers.get(i));
                result.append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Activate the buttons for Quit.
     */
    private void addScoreQuitButtonsListener() {
        Button quitButton = findViewById(R.id.scoreBoardQuitbutton);
        quitButton.setOnClickListener((v) -> {
            switch (gameType){
                case "SlidingTile":
                    BoardManager.destroyBoardManager();
                    break;
                case "Mine":
                    //TODO
                    break;
                case "Sudoku":
                    SudokuBoardManager.destroySudokuBoardManager();
                    break;
            }
            Intent tmp = new Intent(this, StartingActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Dispatch onStop() to fragments.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
