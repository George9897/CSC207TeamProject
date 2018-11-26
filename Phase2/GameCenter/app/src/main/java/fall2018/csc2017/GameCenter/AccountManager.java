package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * a singleton Manage all users' username and password.
 */
public class AccountManager implements Serializable {
    /**
     * The serialVersionUID.
     */
    public static final long serialVersionUID = -1529656476951534478L;
    /**
     * a Map of username and password.
     */
    Map<String, String> map = new HashMap<>();

    /**
     * Current user's user name.
     */
    private String userName = null;

    /**
     * The context used to connect to activity.
     */
    private transient Context context;

    /**
     * The save file which contains the dictionary of username and password.
     */
    private static final String SAVE_FILENAME = "save_file.ser";

    /**
     * Init AccountManager.
     */
    AccountManager(Context context) {
        this.context = context;
        loadFromFile();
    }

    /**
     * record userName and password.
     *
     * @param userName the name the user input
     * @param password the password the user input
     */
    public void setUp(String userName, String password) {
        this.userName = userName;
        map.put(userName, password);
        saveToFile(context);
    }

    /**
     * login current player.
     *
     * @param userName current player
     */
    public void login(String userName) {
        this.userName = userName;
    }

    /**
     * check does username exist in the recording map.
     *
     * @param userName the name the user input
     * @return True iff username is in the file
     */
    boolean checkUsername(String userName) {
        loadFromFile();
        return map.containsKey(userName);
    }

    /**
     * check password with given username and password.
     *
     * @param userName the name the user input
     * @param password the password the user input
     * @return True iff the password is correct
     */
    boolean checkPassword(String userName, String password) {
        loadFromFile();
        String result = map.get(userName);
        return result.equals(password);
    }

    /**
     * Load the user account from fileName.
     *
     */
    private void loadFromFile() {
        try {
            InputStream inputStream = this.context.openFileInput(AccountManager.SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                map = (Map<String, String>) input.readObject();
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

    /**
     * Save the user account to fileName.
     *
     */
    private void saveToFile(Context context) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.context.openFileOutput(AccountManager.SAVE_FILENAME, MODE_PRIVATE));
            outputStream.writeObject(map);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Getter for user name.
     *
     * @return the user name.
     */
    String getUserName() {
        return userName;
    }
}
