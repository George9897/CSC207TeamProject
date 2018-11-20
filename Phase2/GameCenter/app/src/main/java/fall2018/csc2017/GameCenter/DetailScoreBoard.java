package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class DetailScoreBoard {

//    private TreeMap<Integer, String> scoreNameMap;
//
//    private List<Integer> sortedList;
//
//    public Integer getTopOneScore(String mode){
//        return 1;
//    }
//
//    public Integer getTopOneName(String mode){
//        return 1;
//    }

    String gametype;

    String filename = gametype + "ScoreBoard.ser";

    Map<Integer, List<String>> easyMap = new HashMap<>();

    Map<Integer, List<String>> mediumMap = new HashMap<>();

    Map<Integer, List<String>> hardMap = new HashMap<>();

    List<Integer> easyScoreList = new ArrayList();

    List<Integer> mediumScoreList = new ArrayList();

    List<Integer> hardScoreList = new ArrayList();

    int easyTopOneScore;
    String easyTopOneName;

    int mediumTopOneScore;
    String mediumTopOneName;

    int hardTopOneScore;
    String hardTopOneName;

    int score;

    String level;

    /**
     * The boardManager that is used in that round.
     */
    private BoardManager boardManager;

    private MineManager mineManager;

    private SudokuBoardManager sudokuBoardManager;

    Context context;

    /**
     * This detailScoreBoard
     */
    private static DetailScoreBoard detailScoreBoard;

    /**
     * Init AccountManager.
     */
    private DetailScoreBoard(String gametype, Context context) {
        this.gametype = gametype;
        this.context = context;
    }

    /**
     * if DetailScoreBoard exist, get this DetailScoreBoard. Otherwise, createBooms one.
     *
     * @return this Detail ScoreBoard
     */
    public static DetailScoreBoard getDetailScoreBoard(String gametype, Context context) {
        if (detailScoreBoard == null) {
            detailScoreBoard = new DetailScoreBoard(gametype,context);
        }
        return detailScoreBoard;
    }

    void collectScoreLevel(String userName){
        switch (gametype) {
            case "SlidingTile":
                score = boardManager.getScore();
                level = boardManager.slidingtileDifficulty;
                if (!easyMap.containsKey(score)){
                    List l = new ArrayList();
                    l.add(userName);
                    easyMap.put(score,l);
                }else{
                    easyMap.get(score).add(userName);
                }
                break;
            case "Mine":
                score = mineManager.getScore();
                level = mineManager.mineDifficulty;
                break;
            case "Sudoku":
                score = sudokuBoardManager.getScore();
                level = sudokuBoardManager.sudokuDifficulty;
                break;
        }
        helper(userName);
        saveToFile(filename, context);
    }

    void helper(String userName){
        if(level.equals("Easy")){
                if (!easyMap.containsKey(score)){
                    List l = new ArrayList();
                    l.add(userName);
                    easyMap.put(score,l);
                }else{
                    easyMap.get(score).add(userName);
                }
        }else if(level.equals("Medium")){
            if (!mediumMap.containsKey(score)){
                List l = new ArrayList();
                l.add(userName);
                mediumMap.put(score,l);
            }else{
                mediumMap.get(score).add(userName);
            }
        }else if(level.equals("Hard")){
            if (!hardMap.containsKey(score)){
                List l = new ArrayList();
                l.add(userName);
                hardMap.put(score,l);
            }else{
                hardMap.get(score).add(userName);
            }
        }
    }

    public void createSortedList() {
        switch (level) {
            case "Easy":
                easyScoreList.add(score);
                Collections.sort(easyScoreList);
                break;
            case "Medium":
                mediumScoreList.add(score);
                Collections.sort(mediumScoreList);
                break;
            case "Hard":
                hardScoreList.add(score);
                Collections.sort(hardScoreList);
                break;
        }
    }


    public String findTop1(int oldTop1Score, String oldTop1Name,
                           int newScore, String newName){
        return "";
    }

    public void modifyEasyTop1(){

    }

    public void modifyMediumTop1(){

    }

    public void modifyHardTop1(){

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
                detailScoreBoard = (DetailScoreBoard) input.readObject();
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
            outputStream.writeObject(detailScoreBoard);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
