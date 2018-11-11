package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.io.Serializable;

/**
 * The Home Activity.
 */
public class HomeActivity extends AppCompatActivity implements Serializable {

    /**
     * generate buttons when create this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupHomeLoginButtonListener();
        setupHomeSignUpButtonListener();
    }

    /**
     * Create Button for Login activity.
     */
    private void setupHomeLoginButtonListener() {
        Button homeLoginButton = findViewById(R.id.home_login);
        homeLoginButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, LoginActivity.class);
            startActivity(tmp);
        });
    }

    /**
     * Create Button for signUp.
     */
    private void setupHomeSignUpButtonListener() {
        Button homeSignUpButton = findViewById(R.id.home_sign_up);
        homeSignUpButton.setOnClickListener((v) -> {
            Intent tmp = new Intent(this, SignUpActivity.class);
            startActivity(tmp);
        });
    }
}
