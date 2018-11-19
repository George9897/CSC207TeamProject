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
        boomDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> arg0, View arg1,
                                     int arg2, long arg3) {
              String item = (String) arg0.getSelectedItem();
              Toast.makeText(getBaseContext(),
                      "You have selected difficulty : " + item, Toast.LENGTH_SHORT).show();
              switch (item) {
                  case "EASY":
                      MineManager.setNumBoom(10);
                      MineManager.setColNum(8);
                      MineManager.setRowNum(15);
                      MineManager.setDivider(10);
                      MineManager.mineDifficulty = "Easy";
                      break;
                  case "INTERMEDIATE":
                      MineManager.setNumBoom(36);
                      MineManager.setColNum(12);
                      MineManager.setRowNum(20);
                      MineManager.setDivider(13);
                      MineManager.mineDifficulty = "Medium";
                      break;
                  case "PROFESSIONAL":
                      MineManager.setNumBoom(132);
                      MineManager.setColNum(20);
                      MineManager.setRowNum(33);
                      MineManager.setDivider(22);
                      MineManager.mineDifficulty = "Hard";
                      break;
              }
          }
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
