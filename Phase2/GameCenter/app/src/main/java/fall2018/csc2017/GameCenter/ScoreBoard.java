package fall2018.csc2017.GameCenter;

import android.content.Context;
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
import java.util.Map;


import static android.content.Context.MODE_PRIVATE;

public class ScoreBoard implements Serializable {

    /**
     * The ScoreBoard scoreBoard.
     */
    private static ScoreBoard scoreBoard;

    /**
     * The least steps taken in the history.
     */
    private int highestScore;

    /**
     * Number of difficulty level.
     */
    private Integer[] levelOption = {3, 4, 5};

    /**
     * context from StartingActivity.
     */
    private transient Context context;

    /**
     * default value of scoreboard.
     */
    private fall2018.csc2017.GameCenter.Pair<Integer, String> defaultPair =
            new fall2018.csc2017.GameCenter.Pair<>(0, " ");

    /**
     * Map of key of difficulty level and value of users' scores.
     */
    private static Map<Integer, ArrayList<fall2018.csc2017.GameCenter.Pair<Integer, String>>>
            levelMap = new HashMap<>();

    /**
     * Create a ScoreBoard.
     */
    private ScoreBoard(Context context) {
        this.context = context;
        buildLevelMap();
    }

    /**
     * Build a Map from level files.
     */
    void buildLevelMap() {
        for (Integer level : levelOption) {
            if (levelMap.get(level) == null) {
                levelMap.put(level, loadLevelFiles(level, context));
            } else {
                levelMap.replace(level, loadLevelFiles(level, context));
            }
        }
    }

    /**
     * get level Map of key of difficulty level and value of users' scores..
     *
     * @return levelMap.
     */
    Map<Integer, ArrayList<fall2018.csc2017.GameCenter.Pair<Integer, String>>> getLevelMap() {
        return levelMap;
    }

    /**
     * get number of difficulty level.
     *
     * @return levelOption : number of difficulty level
     */
    Integer[] getLevelOption() {
        return levelOption;
    }

    /**
     * get highest score
     *
     * @return highest score.
     */
    int getHighestScore() {
        return highestScore;
    }

    /**
     * Sort user scores with given level.
     *
     * @param level level of difficulty
     * @return a list of sorted list.
     */
    private ArrayList<fall2018.csc2017.GameCenter.Pair<Integer, String>>
    sortedLevelList(Integer level) {
        ArrayList<Integer> scoreList;
        ArrayList<fall2018.csc2017.GameCenter.Pair<Integer, String>>
                levelPairList;
        ArrayList<fall2018.csc2017.GameCenter.Pair<Integer, String>>
                result = new ArrayList<>();
        levelPairList = levelMap.get(level);
        scoreList = generateSortedScoreList(level);

        if (levelPairList != null) {
            int compare = 0;
            for (int index = 0; index < levelPairList.size(); index++) {
                int sortedScore = scoreList.get(index);
                String user = "";
                int index2 = 0;
                while (index2 < levelPairList.size()) {
                    if (sortedScore == scoreList.get(index2) && index2 >= compare) {
                        compare = index2;
                        user = levelPairList.get(index2).second;
                        break;
                    }
                    index2++;
                }
                fall2018.csc2017.GameCenter.Pair<Integer, String>
                        userScorePair = new fall2018.csc2017.GameCenter.Pair<>(sortedScore, user);
                result.add(userScorePair);
            }
        }
        return result;
    }

    /**
     * Sort user scores with given level.
     *
     * @param level level of difficulty
     * @return a list of sorted list.
     */
    private ArrayList<Integer> generateSortedScoreList(int level) {
        ArrayList<Integer> scoreList = new ArrayList<>();
        ArrayList<fall2018.csc2017.GameCenter.Pair<Integer, String>> levelPairList;
        levelPairList = levelMap.get(level);

        if (levelPairList != null) {
            for (int index = 0; index < levelPairList.size(); index++) {
                if (levelPairList.get(index) != null) {
                    int score = levelPairList.get(index).first;
                    scoreList.add(score);
                }
            }
        }
        Collections.sort(scoreList);
        return scoreList;
    }

    /**
     * set highest Score from level 3,4 and 5.
     */
    void updateHighestScore() {
        int result = 0;
        for (Integer level : levelOption) {
            ArrayList<Integer> levelList = generateSortedScoreList(level);
            if (levelList.size() != 0 && levelList.get(levelList.size() - 1) > result) {
                result = levelList.get(levelList.size() - 1);
            }
        }
        this.highestScore = result;
    }

    /**
     * If scoreBoard exist, return scoreBoard. Otherwise, create one.
     */
    static ScoreBoard getScoreBoard(Context context) {
        if (scoreBoard == null) {
            scoreBoard = new ScoreBoard(context);
        }
        return scoreBoard;
    }

    /**
     * get ArrayList levelList based on String level.
     *
     * @param level level of difficulty
     * @return ArrayList levelList
     */
    private ArrayList loadLevelFiles(Integer level, Context context) {
        ArrayList levelList = new ArrayList();
        String fileName = "level_" + Integer.toString(level) + "_File.ser";
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                levelList = (ArrayList) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return levelList;
    }

    /**
     * Save the level File with given level.
     */
    private void saveScoreFile(Integer level) {
        String fileName = "level_" + Integer.toString(level) + "_File.ser";
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(levelMap.get(level));
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Update levelMap when puzzleSolved in BoardManager.
     *
     * @param level level of difficulty.
     * @param user  current player.
     * @param score current score.
     */
    public void update(Integer level, String user, Integer score) {
        ArrayList list = new ArrayList();
        list.add(defaultPair);
        if (levelMap.get(level) == null) {
            levelMap.put(level, list);
        }
        fall2018.csc2017.GameCenter.Pair<Integer, String> userScore =
                new fall2018.csc2017.GameCenter.Pair<>(score, user);
        if (levelMap.get(level) != null) {
            if (levelMap.get(level).size() == 1 && levelMap.get(level).get(0).second.equals(" ")) {
                levelMap.get(level).set(levelMap.get(level).size() - 1, userScore);
            }else if(levelMap.get(level).size() == 0 ){
                levelMap.get(level).add(userScore);
            }else {
                levelMap.get(level).add(levelMap.get(level).size() - 1, userScore);
            }
        }
        saveScoreFile(level);
    }

    /**
     * to String for given level of difficulty.
     *
     * @param level: level of difficulty
     * @return String
     */
    public String toString(Integer level) {
        buildLevelMap();
        updateHighestScore();

        ArrayList<fall2018.csc2017.GameCenter.Pair<Integer, String>>
                levelList = sortedLevelList(level);
        StringBuilder result = new StringBuilder();
        for (int index = 0; index < levelList.size(); index++) {
            fall2018.csc2017.GameCenter.Pair<Integer, String> resultPair = levelList.get(index);
            int Score = resultPair.first;
            System.out.println(Score);
            if (Score == 0) {
                if (index != 0) {
                    result.append("\r\n");
                }
                result.append("+ User name : ").append(resultPair.second).append("    + Score : ")
                        .append("*******");
            } else {
                if (index != 0) {
                    result.append("\r\n");
                }
                result.append("+ User name : ").append(resultPair.second).append("    + Score : ")
                        .append(Integer.toString(Score));
            }
        }
        return result.toString();
    }

    /**
     * to String for given level of difficulty and per user.
     *
     * @param level:    level of difficulty
     * @param username: current player
     * @return String
     */
    public String toString(Integer level, String username) {
        System.out.println("==Processing toString ==");

        buildLevelMap();
        updateHighestScore();

        ArrayList<fall2018.csc2017.GameCenter.Pair<Integer, String>>
                levelList = sortedLevelList(level);
        System.out.println("toString: " + levelList);
        StringBuilder result = new StringBuilder();
        for (int index = 0; index < levelList.size(); index++) {
            fall2018.csc2017.GameCenter.Pair<Integer, String> resultPair = levelList.get(index);
            int Score = resultPair.first;
            System.out.println(Score);
            if (Score == 0 && resultPair.second.equals(username)) {
                if (index != 0) {
                    result.append("\r\n");
                }
                result.append("+ User name : ").append(resultPair.second).append("    + Score : ")
                        .append("*******");
            } else if (Score != 0 && resultPair.second.equals(username)) {
                if (index != 0) {
                    result.append("\r\n");
                }
                result.append("+ User name : ").append(resultPair.second).append("    + Score : ")
                        .append(Integer.toString(Score));
            }
        }
        return result.toString();
    }
}
