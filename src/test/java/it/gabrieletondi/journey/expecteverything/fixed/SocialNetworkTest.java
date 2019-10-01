package it.gabrieletondi.journey.expecteverything.fixed;

import it.gabrieletondi.journey.expecteverything.*;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

public class SocialNetworkTest {
    public static final User A_USER = new User("myUser");
    public static final LocalDateTime NOW = LocalDateTime.now();

    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final UserRepository userRepository = context.mock(UserRepository.class);
    private final MessageValidator messageValidator = context.mock(MessageValidator.class);
    private final Clock clock = context.mock(Clock.class);
    private final PostRepository postRepository = context.mock(PostRepository.class);

    private final SocialNetwork socialNetwork = new SocialNetwork(userRepository, messageValidator, clock, postRepository);

    @Test
    public void validMessage() {
        context.checking(new Expectations(){{
            allowing(userRepository).findByUsername("myUser");
            will(returnValue(A_USER));

            allowing(messageValidator).isValid("this is my update");
            will(returnValue(true));

            allowing(clock).currentTime();
            will(returnValue(NOW));

            oneOf(postRepository).save(new Post(A_USER, "this is my update", NOW));
        }});

        socialNetwork.postUpdate("myUser", "this is my update");
    }

    @Test(expected = InvalidMessageException.class)
    public void invalidMessage() {
        context.checking(new Expectations(){{
            allowing(userRepository).findByUsername("myUser");
            will(returnValue(A_USER));

            allowing(clock).currentTime();
            will(returnValue(NOW));

            allowing(messageValidator).isValid("this is my update");
            will(returnValue(false));

            never(postRepository).save(with(any(Post.class)));
        }});

        socialNetwork.postUpdate("myUser", "this is my update");
    }
}