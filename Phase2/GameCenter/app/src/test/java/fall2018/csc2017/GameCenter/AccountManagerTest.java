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

    @Before
    public void setUpCorrect(){
        context = Mockito.mock(Context.class);
        accountManager = new AccountManager(context);

    }

    @After
    public void tearDown() throws Exception {
        context = null;
        accountManager = null;
    }

    @Test
    public void setUp() {
//        setUpCorrect();
//        accountManager.setUp("Tom","tom123");

    }

    @Test
    public void login() {
//        accountManager.login("Tom");
    }

    @Test
    public void checkUsername() {
//        accountManager.setUp("Tom","tom123");
//        accountManager.setUserName("Tom");
//        assertTrue(accountManager.checkUsername("Tom"));
//        assertFalse(accountManager.checkUsername("Jerry"));
    }

    @Test
    public void checkPassword() {
//        accountManager.setUp("Tom","tom123");
//        assertTrue(accountManager.checkPassword("Tom","tom123"));
//        assertFalse(accountManager.checkPassword("Tom","jerry123"));
    }
}