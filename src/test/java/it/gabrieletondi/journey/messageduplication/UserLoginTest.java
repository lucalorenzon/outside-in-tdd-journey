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
    private final UserAudit userAudit = context.mock(UserAudit.class);
    private final UserMailService userMailService = context.mock(UserMailService.class);
    private final UserBusinessMetrics userBusinessMetrics = context.mock(UserBusinessMetrics.class);
    private final Session session = context.mock(Session.class);

    private UserLogin userLogin = new UserLogin(userRepository,
            userMailService,
            userAudit,
            userBusinessMetrics,
            session);

    @Test
    public void loginFailed() {
        context.checking(new Expectations(){{
            allowing(userRepository).userWith("username", "wrong password");
            will(returnValue(null));

            oneOf(userAudit).registerInvalidLoginFor("username");
            oneOf(userMailService).notifyTentativeAccessFor("username");
            oneOf(userBusinessMetrics).incrementLoginErrorMetric();

            never(session).storeLoggedInUser(with(any(User.class)));
        }});

        userLogin.execute("username", "wrong password");
    }

    @Test
    public void successfulLogin() {
        context.checking(new Expectations(){{
            allowing(userRepository).userWith("username", "wrong password");
            will(returnValue(USER));

            oneOf(userAudit).registerValidUserLoginFor("username");
            oneOf(userBusinessMetrics).incrementSuccessfulLoginMetric();
            oneOf(session).storeLoggedInUser(USER);
        }});

        userLogin.execute("username", "wrong password");
    }
}