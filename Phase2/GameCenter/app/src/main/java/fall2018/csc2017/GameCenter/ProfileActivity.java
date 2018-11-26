package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * The profile Activity.
 */
public class ProfileActivity extends AppCompatActivity {
    /**
     * username information.
     */
    TextView usernameInfo;
    /**
     * password information.
     */
    TextView passwordInfo;
    /**
     * record information.
     */
    TextView recordInfo;
    /**
     * avatar information.
     */
    ImageView userAvatar;
    /**
     * The AccountManager.
     */
    AccountManager accountManager;

    /**
     * The Uri of avatar.
     */
    Uri avatar;
    /**
     * The string of uri.
     */
    String stringUri;

    /**
     * generate information when createBooms this activity.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        accountManager = AccountManager.getAccountManager();
        usernameInfo = findViewById(R.id.usernameInfo);
        passwordInfo = findViewById(R.id.passwordInfo);
        recordInfo = findViewById(R.id.recordInfo);

        loadFromFile(accountManager.userName + "Avatar.ser");
        usernameInfo.setText("Username: " + accountManager.userName);
        passwordInfo.setText("Password: " + accountManager.map.get(accountManager.userName));
        recordInfo.setText("No Record");
        userAvatar.setImageURI(avatar);
    }

//    /**
//     * helper function for records.
//     *
//     * @return record information.
//     */
//    private String record() {
//        String record5 = detailScoreBoard.toString(5, accountManager.userName);
//        String record4 = detailScoreBoard.toString(4, accountManager.userName);
//        String record3 = detailScoreBoard.toString(3, accountManager.userName);
//        if (record3.equals("")) {
//            record3 = "No records.";
//        }
//        if (record4.equals("")) {
//            record4 = "No records.";
//        }
//        if (record5.equals("")) {
//            record5 = "No records.";
//        }
//        return "Records:\n\n5x5\n" + record5 + "\n\n" + "4x4\n" + record4
//                + "\n\n" + "3x3\n" + record3;
//    }

    /**
     * Load the avatar from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                stringUri = (String) input.readObject();
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
}
