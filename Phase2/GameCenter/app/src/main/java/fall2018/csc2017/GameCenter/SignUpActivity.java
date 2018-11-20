package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The sign up activity.
 */
public class SignUpActivity extends AppCompatActivity implements Serializable {
    /**
     * The display for check username and password.
     */
    private TextView meg_box;

//    /**
//     * for upload avatar.
//     */
//    private ImageView picImageView;
//
//    /**
//     * avatar.
//     */
//    private static final int PICK_IMAGE = 100;
//
//    //TODO
//    /**
//     * uri of avatar.
//     */
//    Uri imageUri;
//
//    /**
//     * the string of uri.
//     */
//    String stringUri;

    /**
     * The accountManager.
     */
    AccountManager accountManager = AccountManager.getAccountManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        Button choosePictureButton = findViewById(R.id.choose_picture_button);
//        picImageView = findViewById(R.id.imageButton);
//
//        choosePictureButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openGallery();
//            }
//        });
        setupCreateUserButtonListener();
    }


//    private void openGallery() {
//        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        startActivityForResult(gallery, PICK_IMAGE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
//            imageUri = data.getData();
//            picImageView.setImageURI(imageUri);
//        }
//    }

    /**
     * Set up the createBooms user button.
     */
    private void setupCreateUserButtonListener() {
        Button createUserButton = findViewById(R.id.create_user);
        createUserButton.setOnClickListener((v) -> {
            meg_box = findViewById(R.id.suc_or_not);
            EditText nameWantToHave = findViewById(R.id.create_name);
            EditText password = findViewById(R.id.new_pw);
            EditText reEnterPassword = findViewById(R.id.re_pw);
            String nameWantToHave_string = nameWantToHave.getText().toString();
            String password_string = password.getText().toString();
            String reEnterPassword_string = reEnterPassword.getText().toString();
            if (!checkType(nameWantToHave_string)) {
                meg_box.setText("This name is invalid! The first character shouldn't " +
                        "be number and this name shouldn't contain other things except " +
                        "numbers and characters Please type again!");
            } else if (accountManager.checkUsername(nameWantToHave_string, this)) {
                meg_box.setText("This name is used!");
            } else {
                helper_check_password_and_sign_up(nameWantToHave_string,
                        password_string, reEnterPassword_string);
            }

        });
    }

    /**
     * helper function for check username and password type.
     *
     * @param string: the username or password
     * @return can the username or password be used
     */
    private boolean checkType(String string) {
        boolean includeNumOrLetter = false;
        boolean includeOther = false;
        boolean firstCharNum = false;

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(0) >= '0' && string.charAt(0) <= '9') {
                firstCharNum = true;
            }
            char character = string.charAt(i);
            if (character >= '0' && character <= '9') {
                includeNumOrLetter = true;
            } else if ((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z')) {
                includeNumOrLetter = true;
            } else {
                includeOther = true;
            }
        }
        return !firstCharNum && !includeOther && includeNumOrLetter;
    }

    /**
     * helper function for check passwords same and sign up.
     *
     * @param password  the password
     * @param password2 re-enter password
     * @param username  the username
     */
    private void helper_check_password_and_sign_up(String username, String password,
                                                   String password2) {
        if (!password.equals(password2)) {
            meg_box.setText("Different password!");
        } else {
            if (!checkType(password)) {
                meg_box.setText("This password is invalid! The first character shouldn't " +
                        "be number and this name shouldn't contain other things except " +
                        "numbers and characters Please type again!");
            } else {
                meg_box.setText("Sign up successfully! Welcome!");
                accountManager.setUp(username, password, this);
                // stringUri = imageUri.toString();
                saveToFile(username + "Avatar.ser");
                Intent tmp = new Intent(this, GameCenterActivity.class);
                startActivity(tmp);
            }
        }
    }

    /**
     * Save the user account to fileName.
     *
     * @param fileName the save file which contains the dictionary of username and password
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
//            outputStream.writeObject(stringUri);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
