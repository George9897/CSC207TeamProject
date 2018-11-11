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
    String userName = null;

    /**
     * The save file which contains the dictionary of username and password.
     */
    private static final String SAVE_FILENAME = "save_file.ser";

    /**
     * This accountManager
     */
    private static AccountManager accountManager;

    /**
     * Init AccountManager.
     */
    private AccountManager() {
    }

    /**
     * if AccountManager exist, get this AccountManager. Otherwise, create one.
     *
     * @return this Account Manager
     */
    public static AccountManager getAccountManager() {
        if (accountManager == null) {
            accountManager = new AccountManager();
        }
        return accountManager;
    }

    /**
     * record userName and password.
     *
     * @param userName the name the user input
     * @param password the password the user input
     */
    public void setUp(String userName, String password, Context context) {
        this.userName = userName;
        map.put(userName, password);
        saveToFile(SAVE_FILENAME, context);
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
    boolean checkUsername(String userName, Context context) {
        loadFromFile(SAVE_FILENAME, context);
        return map.containsKey(userName);
    }

    /**
     * check password with given username and password.
     *
     * @param userName the name the user input
     * @param password the password the user input
     * @return True iff the password is correct
     */
    boolean checkPassword(String userName, String password, Context context) {
        loadFromFile(SAVE_FILENAME, context);
        String result = map.get(userName);
        return result.equals(password);
    }

    /**
     * Load the user account from fileName.
     *
     * @param fileName the save file which contains the dictionary of username and password
     */
    private void loadFromFile(String fileName, Context context) {
        try {
            InputStream inputStream = context.openFileInput(fileName);
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
     * @param fileName the save file which contains the dictionary of username and password
     */
    private void saveToFile(String fileName, Context context) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(map);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    String getUserName() {
        return userName;
    }
}
