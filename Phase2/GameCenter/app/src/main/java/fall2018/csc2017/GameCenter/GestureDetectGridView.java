package fall2018.csc2017.GameCenter;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

import java.io.Serializable;

/**
 * The gesture detect grid view of sliding tile game.
 */
public class GestureDetectGridView extends GridView implements Serializable {
    /**
     * The swipe_min_distance
     */
    public static final int SWIPE_MIN_DISTANCE = 100;

    /**
     * The gesture detector
     */
    private GestureDetector gDetector;

    /**
     * The movement controller
     */
    private MovementController mController;
    /**
     * Whether fling is confirmed or not
     */
    private boolean mFlingConfirmed = false;
    /**
     * TouchX
     */
    private float mTouchX;
    /**
     * TouchY
     */
    private float mTouchY;
    /**
     * The board manager
     */
    private BoardManager boardManager;

    /**
     * The first constructor of SlidingTile Gesture Detect Grid View.
     *
     * @param context Context
     */
    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * The second constructor of SlidingTile Gesture Detect Grid View.
     *
     * @param context Context
     * @param attrs AttributeSet
     */
    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * The third constructor of SlidingTile Gesture Detect Grid View.
     * @param context Context
     * @param attrs AttributeSet
     * @param defStyleAttr DefStyleAttr
     */
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
//
//    /**
//     * The fourth constructor of SlidingTile Gesture Detect Grid View.
//     *
//     * @param context Context
//     * @param attrs AttributeSet
//     * @param defStyleAttr DefStyleAttr
//     * @param defStyleRes DefStyleRes
//     */
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
//    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr,
//                                 int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context);
//    }

    /**
     * Initialize a new grid view
     * @param context Context
     */
    private void init(final Context context) {
        mController = new MovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                mController.processTapMovement(context, position);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    /**
     * Whether the event is a intercept touch event.
     * @param ev MotionEvent
     * @return Whether the event is a intercept touch event.
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * Whether the user touched or not.
     * @param ev MotionEvent
     * @return whether the event is a touch event.
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }
}
