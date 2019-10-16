package it.gabrieletondi.journey.messageduplication;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class CompositeUserLoginObserverTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final UserLoginObserver observer1 = context.mock(UserLoginObserver.class, "observer1");
    private final UserLoginObserver observer2 = context.mock(UserLoginObserver.class, "observer2");
    private final UserLoginObserver composite = new CompositeUserLoginObserver(observer1, observer2);

    private static final User USER = new User("username");

    @Test
    public void onUserLoggedIn() {
        context.checking(new Expectations(){{
            oneOf(observer1).onUserLoggedIn(USER);
            oneOf(observer2).onUserLoggedIn(USER);
        }});

        composite.onUserLoggedIn(USER);
    }

    @Test
    public void onLoginFailed() {
        context.checking(new Expectations(){{
            oneOf(observer1).onLoginFailedForUsername("username");
            oneOf(observer2).onLoginFailedForUsername("username");
        }});

        composite.onLoginFailedForUsername("username");
    }
}
