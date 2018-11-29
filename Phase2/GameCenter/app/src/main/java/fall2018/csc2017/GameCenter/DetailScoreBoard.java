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

    private String gameType;

    private BoardManager boardManager;

    private SudokuBoardManager sudokuBoardManager;

    private MineManager mineManager;

    @SuppressLint("UseSparseArrays")
    private Map<Integer, List<String>> easyMap = new HashMap<>();

    @SuppressLint("UseSparseArrays")
    private Map<Integer, List<String>> mediumMap = new HashMap<>();

    @SuppressLint("UseSparseArrays")
    private Map<Integer, List<String>> hardMap = new HashMap<>();

    private List<Integer> easyScoreList = new ArrayList<>();

    private List<Integer> mediumScoreList = new ArrayList<>();

    private List<Integer> hardScoreList = new ArrayList<>();

    private int easyTopOneScore;
    private String easyTopOneName = "";

    private int mediumTopOneScore;
    private String mediumTopOneName = "";

    private int hardTopOneScore;
    private String hardTopOneName = "";

    private int score = 0;

    private String username;

    private String easyLevel;

    private String mediumLevel;

    private String hardLevel;
    private String level = "neverPlayed";

    private transient Context context;

    /**
     * Init AccountManager.
     */
    DetailScoreBoard(String gameType, Context context) {
        this.gameType = gameType;
        this.context = context;
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void setSudokuBoardManager(SudokuBoardManager sudokuBoardManager) {
        this.sudokuBoardManager = sudokuBoardManager;
    }

    public void setMineManager(MineManager mineManager) {
        this.mineManager = mineManager;
    }

    public void destroyAllManager() {
        setSudokuBoardManager(null);
        setBoardManager(null);
        setMineManager(null);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void collectScoreLevel() {
        switch (gameType) {
            case "SlidingTile":
                loadFromFile(StartingActivity.slidingFile);
                if (getBoardManager() == null) {
                    setBoardManager(new BoardManager(this.context, 3, false));
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
                    setMineManager(new MineManager(this.context, username, "Easy"));
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
                    setSudokuBoardManager(new SudokuBoardManager
                            (this.context, "Easy"));
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

    public void createSortedList() {
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

    public String getEasyLevel() {
        return easyLevel;
    }

    public void setEasyLevel(String easyLevel) {
        this.easyLevel = easyLevel;
    }

    public String getEasyTopOneName() {
        return easyTopOneName;
    }

    public void setEasyTopOneName(String easyTopOneName) {
        this.easyTopOneName = easyTopOneName;
    }

    public int getEasyTopOneScore() {
        return easyTopOneScore;
    }

    public void setEasyTopOneScore(int easyTopOneScore) {
        this.easyTopOneScore = easyTopOneScore;
    }

    public List<Integer> getEasyScoreList() {
        return easyScoreList;
    }

    public void setEasyScoreList(List<Integer> easyScoreList) {
        this.easyScoreList = easyScoreList;
    }

    public void modifyEasyTopOne() {
        if (getEasyScoreList().isEmpty()) {
            setEasyLevel("neverPlayed");
        } else {
            setEasyLevel("played");
        }
        if (level.equals("neverPlayed") || getEasyLevel().equals("neverPlayed") ||
                findTopOne(getEasyTopOneScore(), getEasyTopOneName(),
                        score, username) == null) {
            setEasyTopOneName("No data");
        } else if (score != 0) {
        } else {
            setEasyTopOneName(findTopOne(getEasyTopOneScore(), getEasyTopOneName(),
                    score, username));
            setEasyTopOneScore(easyScoreList.get(easyScoreList.size() - 1));
        }
    }

    public String getMediumLevel() {
        return mediumLevel;
    }

    public void setMediumLevel(String mediumLevel) {
        this.mediumLevel = mediumLevel;
    }

    public String getMediumTopOneName() {
        return mediumTopOneName;
    }

    public void setMediumTopOneName(String mediumTopOneName) {
        this.mediumTopOneName = mediumTopOneName;
    }

    public int getMediumTopOneScore() {
        return mediumTopOneScore;
    }

    public void setMediumTopOneScore(int mediumTopOneScore) {
        this.mediumTopOneScore = mediumTopOneScore;
    }

    public List<Integer> getMediumScoreList() {
        return mediumScoreList;
    }

    public void setMediumScoreList(List<Integer> mediumScoreList) {
        this.mediumScoreList = mediumScoreList;
    }

    public void modifyMediumTopOne() {
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

    public String getHardLevel() {
        return hardLevel;
    }

    public void setHardLevel(String hardLevel) {
        this.hardLevel = hardLevel;
    }

    public int getHardTopOneScore() {
        return hardTopOneScore;
    }

    public void setHardTopOneScore(int hardTopOneScore) {
        this.hardTopOneScore = hardTopOneScore;
    }

    public String getHardTopOneName() {
        return hardTopOneName;
    }

    public void setHardTopOneName(String hardTopOneName) {
        this.hardTopOneName = hardTopOneName;
    }

    public List<Integer> getHardScoreList() {
        return hardScoreList;
    }

    public void setHardScoreList(List<Integer> hardScoreList) {
        this.hardScoreList = hardScoreList;
    }

    public void modifyHardTopOne() {
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


    public String getEasyTopOne() {
        if (getEasyTopOneName().equals("No data")) {
            return getEasyTopOneName();
        }
        return getEasyTopOneScore() + "  " + getEasyTopOneName();
    }

    public String getMediumTopOne() {
        if (getMediumTopOneName().equals("No data")) {
            return getMediumTopOneName();
        }
        return getMediumTopOneScore() + "  " + getMediumTopOneName();
    }

    public String getHardTopOne() {
        if (getHardTopOneName().equals("No data")) {
            return getHardTopOneName();
        }
        return getHardTopOneScore() + "  " + getHardTopOneName();
    }

    public Map<Integer, List<String>> getEasyMap() {
        return easyMap;
    }

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

    public Map<Integer, List<String>> getMediumMap() {
        return mediumMap;
    }

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
                        setBoardManager((BoardManager) input.readObject());
                        break;
                    case "Mine":
                        setMineManager((MineManager) input.readObject());
                        break;
                    case "Sudoku":
                        setSudokuBoardManager((SudokuBoardManager) input.readObject());
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
