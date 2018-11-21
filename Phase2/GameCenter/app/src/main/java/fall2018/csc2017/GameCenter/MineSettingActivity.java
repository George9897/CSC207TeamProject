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

/**
 * The setting activity for Mine game.
 */
public class MineSettingActivity extends AppCompatActivity implements Serializable {

    private MineManager mineManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_setting);
        Spinner boomDifficulty = findViewById(R.id.Difficulty_numBooms);
        ArrayList<String> categories = new ArrayList<>();
        categories.add("EASY");
        categories.add("INTERMEDIATE");
        categories.add("PROFESSIONAL");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boomDifficulty.setAdapter(dataAdapter);

        MineManager.destroyMineManager();
        mineManager = MineManager.getMineManager(this);

        boomDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Perform actions when an item is selected.
             *
             * @param arg0 the first argument.
             * @param arg1 the second argument.
             * @param arg2 the third argument.
             * @param arg3 the forth argument.
             */
          @Override
          public void onItemSelected(AdapterView<?> arg0, View arg1,
                                     int arg2, long arg3) {
              String item = (String) arg0.getSelectedItem();
              Toast.makeText(getBaseContext(),
                      "You have selected difficulty : " + item, Toast.LENGTH_SHORT).show();
              switch (item) {
                  case "EASY":
                      mineManager.getMineBoard().setNumBoom(26);
                      mineManager.setMineDifficulty("Easy");
                      break;
                  case "INTERMEDIATE":
                      mineManager.getMineBoard().setNumBoom(40);
                      mineManager.setMineDifficulty("Medium");
                      break;
                  case "PROFESSIONAL":
                      mineManager.getMineBoard().setNumBoom(52);
                      mineManager.setMineDifficulty("Hard");
                      break;
              }
          }

            /**
             * default method when nothing selected.
             *
             * @param arg0 the argument view.
             */
          @Override
          public void onNothingSelected(AdapterView<?> arg0) {}
        });
        addMineConfirmButtonListener();
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
        Intent tmp = new Intent(this, MineGameActivity.class);
        startActivity(tmp);
        finish();
    }

    /**
     * Activate the confirm button.
     */
    private void addMineConfirmButtonListener() {
        Button mineConfirmButton = findViewById(R.id.MineConfirmButton);
        mineConfirmButton.setOnClickListener(v -> switchToGame());
    }
}
