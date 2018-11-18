package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class ChooseGameScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game_score);

        addSTButtonListener();
        addSDButtonListener();
        addMGButtonListener();
    }
// TODO: Remember to update intent activity name.
    private void addMGButtonListener() {
        Button mgButton = findViewById(R.id.minescore);
        mgButton.setOnClickListener(view -> {
            //Intent tmp = new Intent(this, xxx.class);
            //startActivity(tmp);
        });
    }

    private void addSDButtonListener() {
        Button sdButton = findViewById(R.id.sudoukuscore);
        sdButton.setOnClickListener(view -> {
            //Intent tmp = new Intent(this, xxx.class);
            //startActivity(tmp);
        });
    }

    private void addSTButtonListener() {
        Button stButton = findViewById(R.id.slidingscore);
        stButton.setOnClickListener(view -> {
            //Intent tmp = new Intent(this, xxx.class);
            //startActivity(tmp);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
