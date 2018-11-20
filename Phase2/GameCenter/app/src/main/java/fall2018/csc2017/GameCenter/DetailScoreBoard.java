package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class DetailScoreBoard implements Serializable {

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

    private String gametype;

    String filename;

    private Map<Integer, List<String>> easyMap = new HashMap<>();

    private Map<Integer, List<String>> mediumMap = new HashMap<>();

    private Map<Integer, List<String>> hardMap = new HashMap<>();

    private List<Integer> easyScoreList = new ArrayList<>();

    private List<Integer> mediumScoreList = new ArrayList<>();

    private List<Integer> hardScoreList = new ArrayList<>();

    int easyTopOneScore;
    String easyTopOneName;

    int mediumTopOneScore;
    String mediumTopOneName;

    int hardTopOneScore;
    String hardTopOneName;

    int score = 0;

    String username;

    String easyLevel;

    String mediumLevel;

    String hardLevel;
    String level = "neverPlayed";

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
        filename = gametype + "ScoreBoard.ser";
        saveToFile(filename, context);
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

    void collectScoreLevel(){
        switch (gametype) {
            case "SlidingTile":
                boardManager = BoardManager.getBoardManager(context);
                score = boardManager.getScore();
                if (boardManager.slidingtileDifficulty!=null) {
                    level = boardManager.slidingtileDifficulty;
                }
                username = boardManager.userName;
                break;
            case "Mine":
                mineManager = MineManager.getMineManager(context);
                score = mineManager.getScore();
                if (mineManager.mineDifficulty!=null) {
                    level = mineManager.mineDifficulty;
                }
                username = mineManager.getUserName();
                break;
            case "Sudoku":
                sudokuBoardManager = SudokuBoardManager.getSudokuBoardManager(context);
                score = sudokuBoardManager.getScore();
                if (sudokuBoardManager.sudokuDifficulty!=null) {
                    level = sudokuBoardManager.sudokuDifficulty;
                }
                username = sudokuBoardManager.getUserName();
                break;
        }
        helper();

    }

    void helper(){
        if(level.equals("Easy")){
                if (!easyMap.containsKey(score)){
                    List l = new ArrayList();
                    l.add(username);
                    easyMap.put(score,l);
                }else{
                    easyMap.get(score).add(username);
                }
        }else if(level.equals("Medium")){
            if (!mediumMap.containsKey(score)){
                List l = new ArrayList();
                l.add(username);
                mediumMap.put(score,l);
            }else{
                mediumMap.get(score).add(username);
            }
        }else if(level.equals("Hard")) {
            if (!hardMap.containsKey(score)) {
                List l = new ArrayList();
                l.add(username);
                hardMap.put(score, l);
            } else {
                hardMap.get(score).add(username);
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
            case "neverPlayed":
                break;
        }
    }


    public String findTopOne(int oldTopOneScore, String oldTopOneName,
                           int newScore, String newName){
        if (newScore> oldTopOneScore){
            return  newName;
        }
        return oldTopOneName;
    }

    public void modifyEasyTopOne(){
        if (easyScoreList.isEmpty()){
            easyLevel = "neverPlayed";
        } else {
            easyLevel = "played";
        }
        if (level.equals("neverPlayed")|| easyLevel.equals("neverPlayed")) {
            easyTopOneName = "No data";
            easyTopOneScore = 0;
        } else {
            easyTopOneName = findTopOne(easyTopOneScore, easyTopOneName, score, username);
            easyTopOneScore = easyScoreList.get(0);
        }

    }

    public void modifyMediumTopOne(){
        if (mediumScoreList.isEmpty()){
            mediumLevel = "neverPlayed";
        } else {
            mediumLevel = "played";
        }
        if (level.equals("neverPlayed") || mediumLevel.equals("neverPlayed")) {
            mediumTopOneName = "No data";
            mediumTopOneScore = 0;
        } else {
            mediumTopOneName = findTopOne(mediumTopOneScore, mediumTopOneName, score, username);
            mediumTopOneScore = mediumScoreList.get(0);
        }
    }

    public void modifyHardTopOne(){
        if (hardScoreList.isEmpty()){
            hardLevel = "neverPlayed";
        } else {
            hardLevel = "played";
        }
        if (level.equals("neverPlayed")|| hardLevel.equals("neverPlayed")) {
            hardTopOneName = "No data";
            hardTopOneScore = 0;
        } else {
            hardTopOneName = findTopOne(hardTopOneScore, hardTopOneName, score, username);
            hardTopOneScore = hardScoreList.get(0);
        }
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

    public String getEasyTopOne(){
        if (easyTopOneName.equals("No data")){
            return easyTopOneName;
        }
        return easyTopOneScore + easyTopOneName;
    }

    public String getMediumTopOne(){
        if (mediumTopOneName.equals("No data")){
            return mediumTopOneName;
        }
        return mediumTopOneScore + mediumTopOneName;
    }

    public String getHardTopOne(){
        if (hardTopOneName.equals("No data")){
            return hardTopOneName;
        }
        return hardTopOneScore + hardTopOneName;
    }

    public ArrayList<String> getEasySortedList(){
        int i;
        ArrayList<String> l = new ArrayList();
        if (!level.equals("neverPlayed") && easyLevel.equals("neverPlayed")) {
            for (i = 0; i < easyScoreList.size(); i++) {
                int j;
                if (easyMap.get(easyScoreList.get(i)).contains(easyTopOneName)){
                    easyMap.get(easyScoreList.get(i)).remove(easyTopOneName);
                }
                for (j = 0; j < easyMap.get(easyScoreList.get(i)).size(); j++) {
                    l.add(easyScoreList.get(i) + easyMap.get(easyScoreList.get(i)).get(j));
                }
            }
        }else if(level.equals("neverPlayed") || easyLevel.equals("neverPlayed") ){
            l.add("No data");
        }
        return l;
    }

    public ArrayList<String> getMediumSortedList(){
        int i;
        ArrayList<String> l = new ArrayList();
        if (!level.equals("neverPlayed") && !mediumLevel.equals("neverPlayed")) {
            for (i = 0; i < mediumScoreList.size(); i++) {
                int j;
                if (mediumMap.get(mediumScoreList.get(i)).contains(mediumTopOneName)){
                    mediumMap.get(mediumScoreList.get(i)).remove(mediumTopOneName);
                }
                for (j = 0; j < mediumMap.get(mediumScoreList.get(i)).size(); j++) {
                    l.add(mediumScoreList.get(i) + mediumMap.get(mediumScoreList.get(i)).get(j));
                }
            }
        }else if(level.equals("neverPlayed") || mediumLevel.equals("neverPlayed") ){
            l.add("No data");
        }
        return l;
    }

    public ArrayList<String> getHardSortedList(){
        int i;
        ArrayList<String> l = new ArrayList();
        if (!level.equals("neverPlayed") && !hardLevel.equals("neverPlayed")) {
            for (i = 0; i < hardScoreList.size(); i++) {
                int j;
                if (hardMap.get(hardScoreList.get(i)).contains(hardTopOneName)){
                    hardMap.get(hardScoreList.get(i)).remove(hardTopOneName);
                }
                for (j = 0; j < hardMap.get(hardScoreList.get(i)).size(); j++) {
                    l.add(hardScoreList.get(i) + hardMap.get(hardScoreList.get(i)).get(j));
                }
            }
        }else{
            l.add("No data");
        }
        return l;
    }

    public void display(){
        filename = gametype + "ScoreBoard.ser";
        loadFromFile(filename, context);
        collectScoreLevel();
        createSortedList();
        modifyEasyTopOne();
        modifyMediumTopOne();
        modifyHardTopOne();
        saveToFile(filename, context);
    }

}
