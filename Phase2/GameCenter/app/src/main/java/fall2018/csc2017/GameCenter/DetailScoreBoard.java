package fall2018.csc2017.GameCenter;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailScoreBoard implements Serializable {

    /**
     * The serialVersionUID.
     */
    public static final long serialVersionUID = -2227760083029889845L;

    private String gameType;

    private BoardManager boardManager;

    private SudokuBoardManager sudokuBoardManager;

    private MineManager mineManager;

    private Map<Integer, List<String>> easyMap = new HashMap<>();

    private Map<Integer, List<String>> mediumMap = new HashMap<>();

    private Map<Integer, List<String>> hardMap = new HashMap<>();

    private List<Integer> easyScoreList = new ArrayList<>();

    private List<Integer> mediumScoreList = new ArrayList<>();

    private List<Integer> hardScoreList = new ArrayList<>();

    private int easyTopOneScore;
    private String easyTopOneName;

    private int mediumTopOneScore;
    private String mediumTopOneName;

    private int hardTopOneScore;
    private String hardTopOneName;

    private int score = 0;

    private String username;

    private String easyLevel;

    private String mediumLevel;

    private String hardLevel;
    private String level = "neverPlayed";

    transient Context context;

    /**
     * Init AccountManager.
     */
    DetailScoreBoard(String gameType, Context context) {
        this.gameType = gameType;
        this.context = context;
    }

    void destroyAllManager(){
        SudokuBoardManager.destroySudokuBoardManager();
        boardManager = null;
        MineManager.destroyMineManager();
    }

    void setContext(Context context){
        this.context = context;
    }

    public void display(){
        collectScoreLevel();
        createSortedList();
        modifyEasyTopOne();
        modifyMediumTopOne();
        modifyHardTopOne();
    }

    private void collectScoreLevel(){
        mineManager = new MineManager(this.context);
        switch (gameType) {
            case "SlidingTile":
                boardManager = new BoardManager(this.context, 3);
                score = boardManager.getScore();
                if (boardManager.getSlidingTileDifficulty() !=null) {
                    level = boardManager.getSlidingTileDifficulty();
                }
                username = boardManager.userName;
                break;
            case "Mine":
                score = mineManager.getScore();
                if (mineManager.getMineDifficulty()!=null) {
                    level = mineManager.getMineDifficulty();
                }
                username = mineManager.getUserName();
                break;
            case "Sudoku":
                sudokuBoardManager = SudokuBoardManager.getSudokuBoardManager(this.context);
                score = sudokuBoardManager.getScore();
                if (sudokuBoardManager.getSudokuDifficulty()!=null) {
                    level = sudokuBoardManager.getSudokuDifficulty();
                }
                username = sudokuBoardManager.getUserName();
                break;
        }
        if (score != 0 || mineManager.getLose()) {
            updateScore();
        }
    }

    private void updateScore(){
        switch (level) {
            case "Easy":
                if (!easyMap.containsKey(score)) {
                    List l = new ArrayList();
                    l.add(username);
                    easyMap.put(score, l);
                } else {
                    easyMap.get(score).add(username);
                }
                break;
            case "Medium":
                if (!mediumMap.containsKey(score)) {
                    List l = new ArrayList();
                    l.add(username);
                    mediumMap.put(score, l);
                } else {
                    mediumMap.get(score).add(username);
                }
                break;
            case "Hard":
                if (!hardMap.containsKey(score)) {
                    List l = new ArrayList();
                    l.add(username);
                    hardMap.put(score, l);
                } else {
                    hardMap.get(score).add(username);
                }
                break;
            }
    }

    private void createSortedList() {
        switch (level) {
            case "Easy":
                if(!easyScoreList.contains(score)) {
                    easyScoreList.add(score);
                    Collections.sort(easyScoreList);
                }
                System.out.println(easyScoreList.get(0));
                break;
            case "Medium":
                if(!mediumScoreList.contains(score)) {
                    mediumScoreList.add(score);
                    Collections.sort(mediumScoreList);
                }
                break;
            case "Hard":
                if(!hardScoreList.contains(score)) {
                    hardScoreList.add(score);
                    Collections.sort(hardScoreList);
                }
                break;
            case "neverPlayed":
                break;
        }
    }

    private String findTopOne(int oldTopOneScore, String oldTopOneName,
                           int newScore, String newName){
        if (newScore > oldTopOneScore){
            return  newName;
        }
        return oldTopOneName;
    }

    private void modifyEasyTopOne(){
        if (easyScoreList.isEmpty()){
            easyLevel = "neverPlayed";
        } else {
            easyLevel = "played";
        }
        if (level.equals("neverPlayed")|| easyLevel.equals("neverPlayed") ||
                findTopOne(easyTopOneScore, easyTopOneName, score, username)==null) {
            easyTopOneName = "No data";
        } else if(score != 0) {
            easyTopOneName = findTopOne(easyTopOneScore, easyTopOneName, score, username);
            easyTopOneScore = easyScoreList.get(easyScoreList.size()-1);
        }
    }

    private void modifyMediumTopOne(){
        if (mediumScoreList.isEmpty()){
            mediumLevel = "neverPlayed";
        } else {
            mediumLevel = "played";
        }
        if (level.equals("neverPlayed") || mediumLevel.equals("neverPlayed")
                || findTopOne(mediumTopOneScore, mediumTopOneName, score, username) == null) {
            mediumTopOneName = "No data";
        } else if(score != 0){
            mediumTopOneName = findTopOne(mediumTopOneScore, mediumTopOneName, score, username);
            mediumTopOneScore = mediumScoreList.get(mediumScoreList.size()-1);
        }
    }

    private void modifyHardTopOne(){
        if (hardScoreList.isEmpty()){
            hardLevel = "neverPlayed";
        } else {
            hardLevel = "played";
        }
        if (level.equals("neverPlayed")|| hardLevel.equals("neverPlayed") ||
                findTopOne(hardTopOneScore, hardTopOneName, score, username) == null) {
            hardTopOneName = "No data";
        } else if(score != 0){
            hardTopOneName = findTopOne(hardTopOneScore, hardTopOneName, score, username);
            hardTopOneScore = hardScoreList.get(hardScoreList.size()-1);
        }
    }

    public String getEasyTopOne(){
        if (easyTopOneName.equals("No data")){
            return easyTopOneName;
        }
        return easyTopOneScore + "  " + easyTopOneName;
    }

    public String getMediumTopOne(){
        if (mediumTopOneName.equals("No data")){
            return mediumTopOneName;
        }
        return mediumTopOneScore + "  " + mediumTopOneName;
    }

    public String getHardTopOne(){
        if (hardTopOneName.equals("No data")){
            return hardTopOneName;
        }
        return hardTopOneScore + "  " + hardTopOneName;
    }

    public ArrayList<String> getEasySortedList(){
        ArrayList<String> sortedList = new ArrayList<>();
        if (!level.equals("neverPlayed") && !easyLevel.equals("neverPlayed")
                && !easyTopOneName.equals("No data")) {
            easyMap.get(easyTopOneScore).remove(easyTopOneName);
            if (easyMap.get(easyTopOneScore).isEmpty()|| easyScoreList.size() == 1){
                sortedList.add("No data");
            }
            for (int i = easyScoreList.size()-1; i > 0; i--) {
                for (int j = 0; j < easyMap.get(easyScoreList.get(i)).size(); j++) {
                    sortedList.add(easyScoreList.get(i) + "  " +
                            easyMap.get(easyScoreList.get(i)).get(j));
                }
            }
            easyMap.get(easyTopOneScore).add(0,easyTopOneName);
        }else{ sortedList.add("No data"); }
        if (sortedList.size()> 1 && sortedList.contains("No data")){
            sortedList.remove("No data");
        }
        return sortedList;
    }

    public ArrayList<String> getMediumSortedList(){
        ArrayList<String> sortedList = new ArrayList<>();
        if (!level.equals("neverPlayed") && !mediumLevel.equals("neverPlayed") &&
                !mediumTopOneName.equals("No data") && mediumScoreList.size()>1) {
            mediumMap.get(mediumTopOneScore).remove(mediumTopOneName);
            if (mediumMap.get(mediumTopOneScore).isEmpty() || mediumScoreList.size() == 1){
                sortedList.add("No data");
            }
            for (int i = mediumScoreList.size() - 1; i > 0 ; i--) {
                for (int j = 0; j < mediumMap.get(mediumScoreList.get(i)).size(); j++) {
                    sortedList.add(mediumScoreList.get(i) + "  " +
                            mediumMap.get(mediumScoreList.get(i)).get(j));
                }
            }
            mediumMap.get(mediumTopOneScore).add(0,mediumTopOneName);
        }else{ sortedList.add("No data"); }
        if (sortedList.size()> 1 && sortedList.contains("No data")){
            sortedList.remove("No data");
        }
        return sortedList;
    }

    public ArrayList<String> getHardSortedList(){
        ArrayList<String> sortedList = new ArrayList<>();
        if (!level.equals("neverPlayed") && !hardLevel.equals("neverPlayed") &&
                !hardTopOneName.equals("No data")&&hardScoreList.size()>1) {
            hardMap.get(hardTopOneScore).remove(hardTopOneName);
            if (hardMap.get(hardTopOneScore).isEmpty()|| hardScoreList.size() == 1){
                sortedList.add("No data");
            }
            for (int i = hardScoreList.size()-1; i >0; i--) {
                for (int j = 0; j < hardMap.get(hardScoreList.get(i)).size(); j++) {
                    sortedList.add(hardScoreList.get(i) + "  " +
                            hardMap.get(hardScoreList.get(i)).get(j));
                }
            }
            hardMap.get(hardTopOneScore).add(0, hardTopOneName);
        }else{ sortedList.add("No data"); }
        if (sortedList.size()> 1 && sortedList.contains("No data")){
            sortedList.remove("No data");
        }
        return sortedList;
    }

    /**
     * Return Highest score by a given username;
     *
     * @param username given username
     * @return Highest score
     */
    public int getHighestScoreByUser(String username){
        int highestScore = 0;
        List<Integer> scores = new ArrayList<>();
        for (int index = 0; index < easyScoreList.size(); index++){
            if (easyMap.get(easyScoreList.get(index)).contains(username)){
                scores.add(easyScoreList.get(index));
            }
        }
        for (int index = 0; index < mediumScoreList.size(); index++){
            if (mediumMap.get(mediumScoreList.get(index)).contains(username)){
                scores.add(mediumScoreList.get(index));
            }
        }
        for (int index = 0; index < hardScoreList.size(); index++){
            if (hardMap.get(hardScoreList.get(index)).contains(username)){
                scores.add(hardScoreList.get(index));
            }
        }
        if (scores.size() > 0) {
            Collections.sort(scores);
            highestScore = scores.get(scores.size() - 1);
        }
        return highestScore;
    }
}
