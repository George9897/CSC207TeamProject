package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class SudokuSettingActivity extends AppCompatActivity implements Serializable {

    private String sudokuDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_setting);
        Spinner boomDifficulty = findViewById(R.id.sudokuSpinner);
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Easy");
        categories.add("Medium");
        categories.add("Hard");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boomDifficulty.setAdapter(dataAdapter);
        boomDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String item = (String) arg0.getSelectedItem();
                Toast.makeText(getBaseContext(),
                        "You have selected difficulty : " + item, Toast.LENGTH_SHORT).show();
                switch (item) {
                    //TODO FIX
                    case "Easy":
                        sudokuDifficulty = "Easy";
                        break;
                    case "Medium":
                        sudokuDifficulty = "Medium";
                        break;
                    case "Hard":
                        sudokuDifficulty = "Hard";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
        addSudokuConfirmButtonListener();
    }


    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Dispatch onStop() to fragments.
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Switch to the MineGameActivity view to play the game.
     */
    private void switchToGame() {
        Intent intent = getIntent();
//        String username = intent.getExtras().getString("userName");
        Intent tmp = new Intent(this, SudokuBoardActivity.class);
        tmp.putExtra("sudokuDifficulty", sudokuDifficulty);
        //tmp.putExtra("sudokuusername", username);
        startActivity(tmp);
        finish();
    }

    /**
     * Activate the confirm button.
     */
    private void addSudokuConfirmButtonListener() {
        Button sudokuConfirmButton = findViewById(R.id.sudokuConfirmButton);
        sudokuConfirmButton.setOnClickListener(v -> switchToGame());
    }
}
