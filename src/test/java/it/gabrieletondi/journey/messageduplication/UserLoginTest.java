package it.gabrieletondi.journey.messageduplication;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class UserLoginTest {
    private static final User USER = new User("username");

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final UserRepository userRepository = context.mock(UserRepository.class);
    private final UserLoginObserver observer = context.mock(UserLoginObserver.class);

    private UserLogin userLogin = new UserLogin(userRepository, observer);

    @Test
    public void loginFailed() {
        context.checking(new Expectations(){{
            allowing(userRepository).userWith("username", "wrong password");
            will(returnValue(null));

            oneOf(observer).onLoginFailedForUsername("username");
            never(observer).onUserLoggedIn(with(any(User.class)));
        }});

        userLogin.execute("username", "wrong password");
    }

    @Test
    public void successfulLogin() {
        context.checking(new Expectations(){{
            allowing(userRepository).userWith("username", "wrong password");
            will(returnValue(USER));

            oneOf(observer).onUserLoggedIn(USER);
        }});

        userLogin.execute("username", "wrong password");
    }
}