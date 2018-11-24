package fall2018.csc2017.GameCenter;

/*
Taken from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/CustomAdapter.java

This Class is an overwrite of the Base Adapter class
It is designed to aid setting the button sizes and positions in the GridView
 */


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The custom adapter.
 */
public class CustomAdapter extends BaseAdapter implements Serializable {
    private ArrayList<Button> mButtons = null;
    private int mColumnWidth, mColumnHeight;

    /**
     * The constructor for the cutom adapter.
     *
     * @param buttons      the buttons.
     * @param columnWidth  the width of column.
     * @param columnHeight the height of column.
     */
    CustomAdapter(ArrayList<Button> buttons, int columnWidth, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    /**
     * Get count of buttons
     * @return how much buttons are there
     */
    @Override
    public int getCount() {
        return mButtons.size();
    }

    /**
     * Get a button given a position
     * @param position The position to find a button
     * @return the button at given position
     */
    @Override
    public Object getItem(int position) {
        return mButtons.get(position);
    }

    /**
     * Get id given a position
     * @param position The position to find an id
     * @return the id of the button
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Fet view given position, convertView
     * @param position The position to find
     * @param convertView ConvertView
     * @param parent Parent
     * @return a button
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = mButtons.get(position);
        } else {
            button = (Button) convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}
