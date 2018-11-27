package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginActivityTest extends ActivityUnitTestCase<LoginActivity> {
    private Intent mLoginIntent;
    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mLoginIntent = new Intent(getInstrumentation().getTargetContext(), LoginActivity.class);
    }

    @After
    public void tearDown() throws Exception {
    }
//    @MediumTest
//    public void testLoginButton_labelTest() {
//        startActivity(mLoginIntent, null, null);
//
//        final Button loginButton = (Button) getActivity().findViewById(R.id.login_button);
//        final String buttonText = getActivity().getString(R.string.login);
//
//        assertEquals("Unexpected button ladel text", buttonText, loginButton.getText());
//    }
@MediumTest
    public void testLoginActivityMoveToIndex() {
        startActivity(mLoginIntent, null, null);

        final Button loginButton = (Button) getActivity().findViewById(R.id.login_button);
        // 测试Button的点击事件
        loginButton.performClick();

        final Intent intent = getStartedActivityIntent();

        // 去判断是否为空,如果为空就说明跳转失败
        assertNotNull("Intent was null", intent);

        // 这一句是判断你在跳转后有没调finish()
        assertTrue(isFinishCalled());

    }

//    @Test
//    public void onCreate() {
//    }
}