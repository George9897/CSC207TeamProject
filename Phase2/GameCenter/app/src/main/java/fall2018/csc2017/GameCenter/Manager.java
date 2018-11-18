package fall2018.csc2017.GameCenter;

import android.content.Context;

public abstract class Manager {
    public int score;

    private int time;

    private Context context;

    private AccountManager accountManager = AccountManager.getAccountManager();

    String userName = accountManager.getUserName();

    int getScore() { return score; }

    int getTime() { return time; }

    boolean puzzleSolved(){
        return false;
    }

    abstract void makeMove();

    abstract Manager getManager();

    abstract void destroyManager();
}
