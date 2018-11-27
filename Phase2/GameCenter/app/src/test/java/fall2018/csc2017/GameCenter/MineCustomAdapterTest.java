package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;
import android.view.ViewGroup;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MineCustomAdapterTest {

    private MineCustomAdapter mineCustomAdapter;

    /**
     * The list of buttons.
     */
    private ArrayList<Button> mButtons;
    /**
     * The matrix width and height.
     */
    private int mColumnWidth, mColumnHeight;


    @Before
    public void setUp() {
        mButtons = new ArrayList<Button>();
        mColumnWidth = 0;
        mColumnHeight = 0;
        mineCustomAdapter = new MineCustomAdapter(mButtons, mColumnWidth, mColumnHeight);
    }

    @Test
    public void getCount() {
        setUp();
        assertEquals(0, mineCustomAdapter.getCount());
    }

    @Test
    public void getItem() {
        setUp();
        Context context = new MockContext();
        mButtons.add(new Button(context));
        assertEquals(Button.class, mineCustomAdapter.getItem(0).getClass());
    }

    @Test
    public void getItemId() {
        setUp();
        assertEquals(0, mineCustomAdapter.getItemId(0));
    }

    @Test
    public void getView() {
//        setUp();
//        Context context = new MockContext();
//        mButtons.add(new Button(context));
//        mineCustomAdapter.getView(0, null, null);
    }
}