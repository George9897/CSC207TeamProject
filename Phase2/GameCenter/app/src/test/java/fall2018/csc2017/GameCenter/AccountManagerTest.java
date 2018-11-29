package fall2018.csc2017.GameCenter;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AccountManagerTest {
    /**
     * The mine manager for test.
     */
    private AccountManager accountManager;
    private Context context;
    private String userName = "Tom";


    @Before
    public void setUpCorrect() throws Exception {
//        Context context = mock(Context.class);
        Context context = mock(Context.class);
        accountManager = new AccountManager(context);
        accountManager.setUp(userName,"tom123");
    }

    @After
    public void tearDown() throws Exception {
        accountManager = null;
    }

    @Test
    public void setUp() {
    }

    @Test
    public void login() {
    }

    @Test
    public void checkUsername() {
    }

    @Test
    public void checkPassword() {
    }

    @Test
    public void getUserName() {
        assertEquals("Tom", accountManager.getUserName());
    }
}