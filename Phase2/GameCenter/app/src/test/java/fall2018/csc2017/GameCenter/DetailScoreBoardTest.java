package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DetailScoreBoardTest {

    private Context context = new MockContext();
    private String noDataString = "No data";
    private ArrayList<String> noDataList = new ArrayList<>();
    private String userName = "user";
    private DetailScoreBoard detailScoreBoard;

    @Before
    public void setUp() throws Exception {
        noDataList.add("No data");
        String gameType = "Mine";
        detailScoreBoard = new DetailScoreBoard(gameType, context);
        detailScoreBoard.display();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetEmptyEasyTopOne() {
        assertEquals(noDataString, detailScoreBoard.getEasyTopOne());
    }

    @Test
    public void testGeEmptytMediumTopOne() {
        assertEquals(noDataString, detailScoreBoard.getMediumTopOne());
    }

    @Test
    public void testGetEmptyHardTopOne() {
        assertEquals(noDataString, detailScoreBoard.getHardTopOne());
    }

    @Test
    public void testGetEmptyEasySortedList() {
        assertEquals(noDataList, detailScoreBoard.getEasySortedList());
    }

    @Test
    public void testGetEmptyMediumSortedList() {
        assertEquals(noDataList, detailScoreBoard.getMediumSortedList());
    }

    @Test
    public void testGetEmptyHardSortedList() {
        assertEquals(noDataList, detailScoreBoard.getHardSortedList());
    }

    @Test
    public void testDisplay() {
    }

    @Test
    public void testGetEmptyHighestScoreByUser() {
        assertEquals(0, detailScoreBoard.getHighestScoreByUser(userName));
    }
}