package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity implements Serializable {
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_setting_file_tmp.ser";
    /**
     * Chunked Images
     */
    public static ArrayList<Bitmap> chunkedImages;

    /**
     * The slidingTile.
     */
    protected SlidingTile slidingTile;

    /**
     * The boardManager.
     */
    private BoardManager boardManager;

    /**
     * Track difficulty selected
     */
    protected int difficulty;

    ImageView picImageView;

    private static final int PICK_IMAGE = 100;

    Uri imageUri;

    /**
     * Switch for undo method.
     */
    Switch undoSwitch;

    /**
     * Limited undo or not.
     */
    boolean undoLimited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveToFile(TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_setting);

        Spinner spinner = findViewById(R.id.spinner);

        ArrayList<String> categories = new ArrayList<String>();
        categories.add("4x4");
        categories.add("3x3");
        categories.add("5x5");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                String item = (String) arg0.getSelectedItem();
                Toast.makeText(getBaseContext(),
                        "You have selected size : " + item, Toast.LENGTH_SHORT).show();
                switch (item) {
                    case "3x3":
                        difficulty = 3;
                        SlidingTile.level = 3;
                        break;
                    case "4x4":
                        difficulty = 4;
                        SlidingTile.level = 4;
                        break;
                    default:
                        difficulty = 5;
                        SlidingTile.level = 5;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        addConfirmButtonListener();
        Button uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setBackgroundColor(Color.DKGRAY);
        picImageView = findViewById(R.id.upimageView);

        Switch simpleSwitch = (Switch) findViewById(R.id.switchMode);
        simpleSwitch.setChecked(false);
        simpleSwitch.setTextOn("On");
        simpleSwitch.setTextOff("Off");
        simpleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch;
                if (simpleSwitch.isChecked()) {
                    statusSwitch = simpleSwitch.getTextOn().toString();
                    uploadButton.setEnabled(true);
                    uploadButton.setBackgroundColor(Color.LTGRAY);
                } else {
                    statusSwitch = simpleSwitch.getTextOff().toString();
                    uploadButton.setBackgroundColor(Color.DKGRAY);
                    uploadButton.setEnabled(false);
                }
                Toast.makeText(getApplicationContext(), "Picture Mode :" +
                        statusSwitch, Toast.LENGTH_LONG).show();
            }
        });

        undoSwitch = findViewById(R.id.undoSwitch);
        undoSwitch.setChecked(false);
        undoSwitch.setTextOn("Unlimited");
        undoSwitch.setTextOff("limited");
        undoSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoLimited = undoSwitch.isChecked();
                String statusSwitch;
                if (undoSwitch.isChecked()) {
                    statusSwitch = undoSwitch.getTextOn().toString();
                } else {
                    statusSwitch = undoSwitch.getTextOff().toString();
                }
                Toast.makeText(getApplicationContext(), "Undo Mode :" +
                        statusSwitch, Toast.LENGTH_LONG).show();
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    /**
     * Split image.
     *
     * @param image: the picture upload
     * @param level: the difficulty
     */
    private void splitImage(ImageView image, int level) {
        int chunkHeight, chunkWidth;

        chunkedImages = new ArrayList<Bitmap>(level * level);

        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        chunkHeight = bitmap.getHeight() / level;
        chunkWidth = bitmap.getWidth() / level;

        int yCoord = 0;
        for (int x = 0; x < level; x++) {
            int xCoord = 0;
            for (int y = 0; y < level; y++) {
                chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            picImageView.setImageURI(imageUri);
        }
    }

    /**
     * Read the temporary slidingTile from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
//        loadFromFile(TEMP_SAVE_FILENAME);
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
     * Dispatch onStop() to fragments.
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        boardManager = BoardManager.getBoardManager(this);
        saveToFile(boardManager.userName + ".ser");
        tmp.putExtra("slidingTile", slidingTile);
        tmp.putExtra("undo", undoLimited);
        startActivity(tmp);
        finish();
    }

    /**
     * Save the slidingTile manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
    }

    /**
     * Activate the confirm button.
     */
    private void addConfirmButtonListener() {
        Switch simpleSwitch = findViewById(R.id.switchMode);
        Button confirmButton = findViewById(R.id.ConfirmButton);
        confirmButton.setOnClickListener(view -> {
            if (!simpleSwitch.isChecked()) {
                BoardManager.destroyBoardManager();
                boardManager = BoardManager.getBoardManager(this);
                System.out.println(boardManager.getSlidingTile().tiles.length);
                SettingActivity.this.switchToGame();
                System.out.println("still numbers mode");
            } else if (simpleSwitch.isChecked()) {
                if (picImageView != null) {
                    splitImage(picImageView, difficulty);
                    BoardManager.destroyBoardManager();
                    boardManager = BoardManager.getBoardManager(this);
                    boardManager.getSlidingTile().isDrawable = true;

                    System.out.println(boardManager.getSlidingTile().tiles.length);
                    SettingActivity.this.switchToGame();
                    System.out.println("there is a picture already");
                } else {
                    Toast.makeText(getApplicationContext(), "Please choose a photo!", Toast.LENGTH_LONG).show();
                    System.out.println("please choose a photo");
                }
            }
        });
    }
}