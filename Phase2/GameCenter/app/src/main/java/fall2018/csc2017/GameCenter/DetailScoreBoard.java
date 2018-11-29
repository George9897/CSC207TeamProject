package fall2018.csc2017.GameCenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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

    /**
     * The String gameType.
     */
    private String gameType;

    /**
     * The BoardManager boardManager.
     */
    private BoardManager boardManager;

    /**
     * The SudokuBoardManager sudokuBoardManager.
     */
    private SudokuBoardManager sudokuBoardManager;

    /**
     * The MineManager mineManager.
     */
    private MineManager mineManager;

    /**
     * The Map<Integer, List<String>> easyMap.
     */
    @SuppressLint("UseSparseArrays")
    private Map<Integer, List<String>> easyMap = new HashMap<>();

    /**
     * The Map<Integer, List<String>> mediumMap.
     */
    @SuppressLint("UseSparseArrays")
    private Map<Integer, List<String>> mediumMap = new HashMap<>();

    /**
     * The Map<Integer, List<String>> hardMap.
     */
    @SuppressLint("UseSparseArrays")
    private Map<Integer, List<String>> hardMap = new HashMap<>();

    /**
     * The List<Integer> easyScoreList.
     */
    private List<Integer> easyScoreList = new ArrayList<>();

    /**
     * The List<Integer> mediumScoreList.
     */
    private List<Integer> mediumScoreList = new ArrayList<>();

    /**
     * The List<Integer> hardScoreList.
     */
    private List<Integer> hardScoreList = new ArrayList<>();

    /**
     * The int easyTopOneScore.
     */
    private int easyTopOneScore;

    /**
     * The String easyTopOneName.
     */
    private String easyTopOneName = "";

    /**
     * The int mediumTopOneScore.
     */
    private int mediumTopOneScore;

    /**
     * The String mediumTopOneName.
     */
    private String mediumTopOneName = "";

    /**
     * The int hardTopOneScore.
     */
    private int hardTopOneScore;

    /**
     * The String hardTopOneName.
     */
    private String hardTopOneName = "";

    /**
     * The int score.
     */
    private int score = 0;

    /**
     * The String username.
     */
    private String username;

    /**
     * The String easyLevel.
     */
    private String easyLevel;

    /**
     * The String mediumLevel.
     */
    private String mediumLevel;

    /**
     * The String hardLevel.
     */
    private String hardLevel;

    /**
     * The String level.
     */
    private String level = "neverPlayed";

    /**
     * The transient Context context.
     */
    private transient Context context;

    /**
     * Init AccountManager.
     */
    DetailScoreBoard(String gameType, Context context) {
        this.gameType = gameType;
        this.context = context;
    }

    /**
     * Set all manager in this DetailScoreBoard to null.
     */
    void destroyAllManager() {
        sudokuBoardManager = null;
        boardManager = null;
        mineManager = null;
    }

    /**
     * Set Context on this DetailScoreBoard.
     *
     * @param context allow to save and load from file.
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Set level on this DetailScoreBoard.
     * @param level level of difficulty
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Update this score, userName, and manager of this DetailScoreBoard by gameType.
     */
    void collectScoreLevel() {
        switch (gameType) {
            case "SlidingTile":
                loadFromFile(StartingActivity.slidingFile);
                if (boardManager == null) {
                    boardManager = new BoardManager(this.context, 3, false);
                }
                score = boardManager.getScore();
                if (boardManager.getSlidingTileDifficulty() != null) {
                    setLevel(boardManager.getSlidingTileDifficulty());
                }
                username = boardManager.getUserName();
                break;
            case "Mine":
                loadFromFile(StartingActivity.mineFile);
                if (mineManager == null) {
                    mineManager = new MineManager(this.context, username, "Easy");
                }
                score = mineManager.getScore();
                if (mineManager.getMineDifficulty() != null) {
                    setLevel(mineManager.getMineDifficulty());
                }
                username = mineManager.getUserName();
                break;
            case "Sudoku":
                loadFromFile(StartingActivity.sudokuFile);
                if (sudokuBoardManager == null) {
                    sudokuBoardManager = new SudokuBoardManager(this.context, "Easy");
                }
                score = sudokuBoardManager.getScore();
                if (sudokuBoardManager.getSudokuDifficulty() != null) {
                    setLevel(sudokuBoardManager.getSudokuDifficulty());
                }
                username = sudokuBoardManager.getUserName();
                break;
        }
        if (mineManager != null) {
            if (mineManager.getLose() || mineManager.getWin()) {
                updateScore();
            }
        } else {
            if (score != 0) {
                updateScore();
            }
        }
    }

    /**
     * Update Score.
     */
    private void updateScore() {
        switch (level) {
            case "Easy":
                if (!getEasyMap().containsKey(score)) {
                    List<String> l = new ArrayList<>();
                    l.add(username);
                    getEasyMap().put(score, l);
                } else {
                    getEasyMap().get(score).add(username);
                }
                break;
            case "Medium":
                if (!getMediumMap().containsKey(score)) {
                    List<String> l = new ArrayList<>();
                    l.add(username);
                    getMediumMap().put(score, l);
                } else {
                    getMediumMap().get(score).add(username);
                }
                break;
            case "Hard":
                if (!getHardMap().containsKey(score)) {
                    List<String> l = new ArrayList<>();
                    l.add(username);
                    getHardMap().put(score, l);
                } else {
                    getHardMap().get(score).add(username);
                }
                break;
        }
    }

    /**
     * create SortedList by level.
     */
    void createSortedList() {
        switch (level) {
            case "Easy":
                if (!getEasyScoreList().contains(score)) {
                    getEasyScoreList().add(score);
                    Collections.sort(getEasyScoreList());
                }
                break;
            case "Medium":
                if (!getMediumScoreList().contains(score)) {
                    getMediumScoreList().add(score);
                    Collections.sort(getMediumScoreList());
                }
                break;
            case "Hard":
                if (!getHardScoreList().contains(score)) {
                    getHardScoreList().add(score);
                    Collections.sort(getHardScoreList());
                }
                break;
            case "neverPlayed":
                break;
        }
    }

    /**
     * The find the user name of top one player.
     *
     * @param oldTopOneScore the score of previous top one player.
     * @param oldTopOneName the name of previous top one player.
     * @param newScore the score of current player.
     * @param newName the name of current player.
     * @return The top one player.
     */
    private String findTopOne(int oldTopOneScore, String oldTopOneName,
                              int newScore, String newName) {
        if (newScore > oldTopOneScore) {
            return newName;
        } else if (newScore == 0 && oldTopOneName != null) {
            if (oldTopOneName.equals("No data")) {
                return newName;
            }
        }
        return oldTopOneName;
    }

    /**
     * Get easy level.
     *
     * @return easyLevel.
     */
    String getEasyLevel() {
        return easyLevel;
    }

    /**
     * Set easy level.
     *
     * @param easyLevel easy level.
     */
    void setEasyLevel(String easyLevel) {
        this.easyLevel = easyLevel;
    }

    /**
     * Get Easy TopOne Name.
     *
     * @return Easy TopOne Name.
     */
    String getEasyTopOneName() {
        return easyTopOneName;
    }

    /**
     * Set Easy TopOne Name.
     *
     * @param easyTopOneName Easy TopOne Name.
     */
    void setEasyTopOneName(String easyTopOneName) {
        this.easyTopOneName = easyTopOneName;
    }

    /**
     * Get Easy TopOne score.
     *
     * @return Easy TopOne score.
     */
    int getEasyTopOneScore() {
        return easyTopOneScore;
    }

    /**
     * Set Easy TopOne score.
     *
     * @param easyTopOneScore Easy TopOne score.
     */
    void setEasyTopOneScore(int easyTopOneScore) {
        this.easyTopOneScore = easyTopOneScore;
    }

    /**
     * Get Easy ScoreList.
     *
     * @return Easy ScoreList.
     */
    List<Integer> getEasyScoreList() {
        return easyScoreList;
    }

    /**
     * Set Easy ScoreList.
     *
     * @param easyScoreList Easy ScoreList.
     */
    void setEasyScoreList(List<Integer> easyScoreList) {
        this.easyScoreList = easyScoreList;
    }

    /**
     * modify Easy TopOne.
     */
    void modifyEasyTopOne() {
        if (getEasyScoreList().isEmpty()) {
            setEasyLevel("neverPlayed");
        } else {
            setEasyLevel("played");
        }
        if (level.equals("neverPlayed") || getEasyLevel().equals("neverPlayed") ||
                findTopOne(getEasyTopOneScore(), getEasyTopOneName(),
                        score, username) == null) {
            setEasyTopOneName("No data");
        } else if (score == 0) {
            setEasyTopOneName(findTopOne(getEasyTopOneScore(), getEasyTopOneName(),
                    score, username));
            setEasyTopOneScore(easyScoreList.get(easyScoreList.size() - 1));
        }
    }

    /**
     * get Medium Level.
     *
     * @return Medium Level.
     */
    String getMediumLevel() {
        return mediumLevel;
    }

    /**
     * set Medium Level.
     *
     * @param mediumLevel Medium Level.
     */
    void setMediumLevel(String mediumLevel) {
        this.mediumLevel = mediumLevel;
    }

    /**
     * get Medium TopOne Name.
     *
     * @return Medium TopOne Name.
     */
    String getMediumTopOneName() {
        return mediumTopOneName;
    }

    /**
     * set Medium TopOne Name.
     *
     * @param mediumTopOneName Medium TopOne Name.
     */
    void setMediumTopOneName(String mediumTopOneName) {
        this.mediumTopOneName = mediumTopOneName;
    }

    /**
     * get Medium TopOne Score.
     * @return Medium TopOne Score.
     */
    int getMediumTopOneScore() {
        return mediumTopOneScore;
    }

    /**
     * set Medium TopOne Score.
     *
     * @param mediumTopOneScore Medium TopOne Score.
     */
    void setMediumTopOneScore(int mediumTopOneScore) {
        this.mediumTopOneScore = mediumTopOneScore;
    }

    /**
     * get Medium ScoreList.
     *
     * @return Medium ScoreList.
     */
    List<Integer> getMediumScoreList() {
        return mediumScoreList;
    }

    /**
     * set MediumScore List.
     *
     * @param mediumScoreList MediumScore List.
     */
    void setMediumScoreList(List<Integer> mediumScoreList) {
        this.mediumScoreList = mediumScoreList;
    }

    /**
     * modify Medium TopOne.
     */
    void modifyMediumTopOne() {
        if (getMediumScoreList().isEmpty()) {
            setMediumLevel("neverPlayed");
        } else {
            setMediumLevel("played");
        }
        if (level.equals("neverPlayed") || getMediumLevel().equals("neverPlayed")
                || findTopOne(getMediumTopOneScore(), getMediumTopOneName(),
                score, username) == null) {
            setMediumTopOneName("No data");
        } else {
            setMediumTopOneName(findTopOne(getMediumTopOneScore(), getMediumTopOneName(),
                    score, username));
            setMediumTopOneScore(mediumScoreList.get(mediumScoreList.size() - 1));
        }
    }

    /**
     * get Hard Level.
     *
     * @return Hard Level.
     */
    String getHardLevel() {
        return hardLevel;
    }

    /**
     * set Hard Level.
     *
     * @param hardLevel Hard Level.
     */
    void setHardLevel(String hardLevel) {
        this.hardLevel = hardLevel;
    }

    /**
     * get Hard TopOne Score.
     *
     * @return Hard TopOne Score.
     */
    int getHardTopOneScore() {
        return hardTopOneScore;
    }

    /**
     * set Hard TopOne Score.
     *
     * @param hardTopOneScore Hard TopOne Score.
     */
    void setHardTopOneScore(int hardTopOneScore) {
        this.hardTopOneScore = hardTopOneScore;
    }

    /**
     * get Hard TopOne Name.
     *
     * @return Hard TopOne Name.
     */
    String getHardTopOneName() {
        return hardTopOneName;
    }

    /**
     * set Hard TopOne Name.
     *
     * @param hardTopOneName Hard TopOne Name.
     */
    void setHardTopOneName(String hardTopOneName) {
        this.hardTopOneName = hardTopOneName;
    }

    /**
     * get Hard ScoreList.
     *
     * @return Hard ScoreList.
     */
    List<Integer> getHardScoreList() {
        return hardScoreList;
    }

    /**
     * set Hard ScoreList.
     *
     * @param hardScoreList Hard ScoreList.
     */
    void setHardScoreList(List<Integer> hardScoreList) {
        this.hardScoreList = hardScoreList;
    }

    /**
     * modify Hard TopOne.
     */
    void modifyHardTopOne() {
        if (getHardScoreList().isEmpty()) {
            setHardLevel("neverPlayed");
        } else {
            setHardLevel("played");
        }
        if (level.equals("neverPlayed") || getHardLevel().equals("neverPlayed") ||
                findTopOne(getHardTopOneScore(), getHardTopOneName(),
                        score, username) == null) {
            setHardTopOneName("No data");
        } else {
            setHardTopOneName(findTopOne(getHardTopOneScore(), getHardTopOneName(),
                    score, username));
            setHardTopOneScore(hardScoreList.get(hardScoreList.size() - 1));
        }
    }

    /**
     * get Easy TopOne.
     *
     * @return Easy TopOne.
     */
    public String getEasyTopOne() {
        if (getEasyTopOneName().equals("No data")) {
            return getEasyTopOneName();
        }
        return getEasyTopOneScore() + "  " + getEasyTopOneName();
    }

    /**
     * get Medium TopOne.
     *
     * @return Medium TopOne.
     */
    public String getMediumTopOne() {
        if (getMediumTopOneName().equals("No data")) {
            return getMediumTopOneName();
        }
        return getMediumTopOneScore() + "  " + getMediumTopOneName();
    }

    /**
     * get Hard TopOne.
     *
     * @return Hard TopOne.
     */
    public String getHardTopOne() {
        if (getHardTopOneName().equals("No data")) {
            return getHardTopOneName();
        }
        return getHardTopOneScore() + "  " + getHardTopOneName();
    }

    /**
     * get Easy Map.
     *
     * @return Easy Map.
     */
    Map<Integer, List<String>> getEasyMap() {
        return easyMap;
    }

    /**
     * get Easy SortedList.
     *
     * @return SortedList.
     */
    ArrayList<String> getEasySortedList() {
        ArrayList<String> sortedList = new ArrayList<>();
        if (!level.equals("neverPlayed") && !getEasyLevel().equals("neverPlayed")
                && !getEasyTopOneName().equals("No data")) {
            getEasyMap().get(getEasyTopOneScore()).remove(getEasyTopOneName());
            if (getEasyMap().get(getEasyTopOneScore()).isEmpty() && getEasyScoreList().size() == 1) {
                sortedList.add("No data");
            }
            for (int i = getEasyScoreList().size() - 1; i > 0; i--) {
                for (int j = 0; j < getEasyMap().get(getEasyScoreList().get(i)).size(); j++) {
                    sortedList.add(getEasyScoreList().get(i) + "  " +
                            getEasyMap().get(getEasyScoreList().get(i)).get(j));
                }
            }
            if (getEasyMap().get(getEasyScoreList().get(0)) != null) {
                if ((!gameType.equals("Mine") && getEasyScoreList().get(0) != 0) ||
                        (gameType.equals("Mine"))) {
                    for (int j = 0; j < getEasyMap().get(getEasyScoreList().get(0)).size(); j++) {
                        sortedList.add(getEasyScoreList().get(0) + "  " +
                                getEasyMap().get(getEasyScoreList().get(0)).get(j));
                    }
                }
            }
            getEasyMap().get(getEasyTopOneScore()).add(0, getEasyTopOneName());
        } else {
            sortedList.add("No data");
        }
        if (sortedList.size() > 1) sortedList.remove("No data");
        return sortedList;
    }

    /**
     * get Medium Map.
     *
     * @return Medium Map.
     */
    public Map<Integer, List<String>> getMediumMap() {
        return mediumMap;
    }

    /**
     * get Medium SortedList.
     *
     * @return Medium SortedList.
     */
    ArrayList<String> getMediumSortedList() {
        ArrayList<String> sortedList = new ArrayList<>();
        if (!level.equals("neverPlayed") && !getMediumLevel().equals("neverPlayed") &&
                !getMediumTopOneName().equals("No data") && getMediumScoreList().size() > 1) {
            getMediumMap().get(getMediumTopOneScore()).remove(getMediumTopOneName());
            if (getMediumMap().get(getMediumTopOneScore()).isEmpty() &&
                    getMediumScoreList().size() == 1) {
                sortedList.add("No data");
            }
            for (int i = getMediumScoreList().size() - 1; i > 0; i--) {
                for (int j = 0; j < getMediumMap().get(getMediumScoreList().get(i)).size(); j++) {
                    sortedList.add(getMediumScoreList().get(i) + "  " +
                            getMediumMap().get(getMediumScoreList().get(i)).get(j));
                }
            }
            if (getMediumMap().get(getMediumScoreList().get(0)) != null) {
                for (int j = 0; j < getMediumMap().get(getMediumScoreList().get(0)).size(); j++) {
                    sortedList.add(getMediumScoreList().get(0) + "  " +
                            getMediumMap().get(getMediumScoreList().get(0)).get(j));
                }
            }
            getMediumMap().get(getMediumTopOneScore()).add(0, getMediumTopOneName());
        } else {
            sortedList.add("No data");
        }
        if (sortedList.size() > 1) {
            sortedList.remove("No data");
        }
        return sortedList;
    }

    /**
     * get HardMap.
     *
     * @return HardMap.
     */
    public Map<Integer, List<String>> getHardMap() {
        return hardMap;
    }

    ArrayList<String> getHardSortedList() {
        ArrayList<String> sortedList = new ArrayList<>();
        if (!level.equals("neverPlayed") && !getHardLevel().equals("neverPlayed") &&
                !getHardTopOneName().equals("No data") && getHardScoreList().size() > 1) {
            getHardMap().get(getHardTopOneScore()).remove(getHardTopOneName());
            if (getHardMap().get(getHardTopOneScore()).isEmpty() && getHardScoreList().size() == 1) {
                sortedList.add("No data");
            }
            for (int i = getHardScoreList().size() - 1; i > 0; i--) {
                for (int j = 0; j < getHardMap().get(getHardScoreList().get(i)).size(); j++) {
                    sortedList.add(getHardScoreList().get(i) + "  " +
                            getHardMap().get(getHardScoreList().get(i)).get(j));
                }
            }
            if (getHardMap().get(getHardScoreList().get(0)) != null) {
                for (int j = 0; j < getHardMap().get(getHardScoreList().get(0)).size(); j++) {
                    sortedList.add(getHardScoreList().get(0) + "  " +
                            getHardMap().get(getHardScoreList().get(0)).get(j));
                }
            }
            getHardMap().get(getHardTopOneScore()).add(0, getHardTopOneName());
        } else {
            sortedList.add("No data");
        }
        if (sortedList.size() > 1) {
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
    int getHighestScoreByUser(String username) {
        int highestScore = 0;
        List<Integer> scores = new ArrayList<>();
        for (int index = 0; index < getEasyScoreList().size(); index++) {
            if (getEasyMap().get(getEasyScoreList().get(index)).contains(username)) {
                scores.add(getEasyScoreList().get(index));
            }
        }
        for (int index = 0; index < getMediumScoreList().size(); index++) {
            if (getMediumMap().get(getMediumScoreList().get(index)).contains(username)) {
                scores.add(getMediumScoreList().get(index));
            }
        }
        for (int index = 0; index < getHardScoreList().size(); index++) {
            if (getHardMap().get(getHardScoreList().get(index)).contains(username)) {
                scores.add(getHardScoreList().get(index));
            }
        }
        if (scores.size() > 0) {
            Collections.sort(scores);
            highestScore = scores.get(scores.size() - 1);
        }
        return highestScore;
    }

    /**
     * Load the slidingTile manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                switch (gameType) {
                    case "SlidingTile":
                        boardManager = (BoardManager) input.readObject();
                        break;
                    case "Mine":
                        mineManager = (MineManager) input.readObject();
                        break;
                    case "Sudoku":
                        sudokuBoardManager = (SudokuBoardManager) input.readObject();
                        break;
                }
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
