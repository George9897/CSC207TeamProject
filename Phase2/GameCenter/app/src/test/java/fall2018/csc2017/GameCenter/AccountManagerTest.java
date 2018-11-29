package fall2018.csc2017.GameCenter;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


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
        accountManager.setUserName("tom");
    }

    @After
    public void tearDown(){
        context = null;
        accountManager = null;
    }

    @Test
    public void setUp() {
        context = Mockito.mock(Context.class);
        accountManager = new AccountManager(context);
        accountManager.setUserName("tom");
    }
}