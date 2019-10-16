package it.gabrieletondi.journey.toomanycollaborators;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class BlogTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private final Log log = context.mock(Log.class);
    private final CommentRepository commentRepository = context.mock(CommentRepository.class);
    private final UserLoginService userLoginService = context.mock(UserLoginService.class);
    private final MessageSanitizerService messageSanitizerService = context.mock(MessageSanitizerService.class);

    private final Blog blog = new Blog(
            log,
            commentRepository,
            userLoginService,
            messageSanitizerService);

    @Test
    public void wrongPassword() {
        context.checking(new Expectations(){{
            allowing(userLoginService).loginUser("username", "wrong password");
            will(throwException(new UserWrongPasswordException()));

            oneOf(log).info("wrong password for user with username username");
        }});

        blog.postComment("username", "wrong password", "cool story bro!");
    }

    @Test
    public void correctPassword_bannedUser() {
        context.checking(new Expectations(){{
            allowing(userLoginService).loginUser("bannedUser", "password");
            will(throwException(new UserBannedException()));

            oneOf(log).info("banned user bannedUser tried to comment");
        }});

        blog.postComment("bannedUser", "password", "cool story bro!");
    }

    @Test
    public void authorizedUser_messageWithBadWords() {
        context.checking(new Expectations(){{
            allowing(userLoginService).loginUser("username", "password");
            will(returnValue(new User("username", "password")));

            allowing(messageSanitizerService).sanitize("this is a bad word");
            will(throwException(new MessageContainsBadWordsException()));

            oneOf(log).info("comment [this is a bad word] contains bad words");
        }});

        blog.postComment("username", "password", "this is a bad word");
    }

    @Test
    public void authorizedUser_messageWithEmojiAndUrl() {
        context.checking(new Expectations(){{
            allowing(userLoginService).loginUser("username", "password");
            will(returnValue(new User("username", "password")));

            allowing(messageSanitizerService).sanitize("this is a smile :) and this is an url: https://www.connexxo.it");
            will(returnValue("this is a smile \uD83D\uDE00 and this is an url: OBFUSCATED_URL"));

            oneOf(commentRepository).store(new Comment(
                    new User("username", "password"),
                    "this is a smile \uD83D\uDE00 and this is an url: OBFUSCATED_URL"
            ));
        }});

        blog.postComment("username", "password", "this is a smile :) and this is an url: https://www.connexxo.it");
    }
}