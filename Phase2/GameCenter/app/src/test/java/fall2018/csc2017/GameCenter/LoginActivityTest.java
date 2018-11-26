package fall2018.csc2017.GameCenter;

import android.app.Activity;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.Spinner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginActivityTest extends ActivityUnitTestCase {

    public LoginActivityTest(Class LoginActivity) {
        super(LoginActivity);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

//        setActivityInitialTouchMode(false);

        Activity mActivity = getActivity();

        Button mButton =
                (Button) mActivity.findViewById(R.id.login_button);
//
//        Object mPlanetData = mButton.getAdapter();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
    }
}