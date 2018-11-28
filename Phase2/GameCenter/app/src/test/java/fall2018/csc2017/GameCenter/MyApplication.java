package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MyApplication extends AppCompatActivity {

    private static MyApplication app;

    BoardManagerTest boardManagerTest = new BoardManagerTest();

    public static MyApplication getInstance() {
        System.out.println(app);
        return app;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //boardManagerTest.setContext(this);
        app = this;
    }

}
