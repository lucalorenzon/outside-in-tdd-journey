package it.gabrieletondi.journey.expecteverything.it.gabrieletondi.journey.toomanycollaborators;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static java.util.Arrays.asList;

public class BlogTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    private final UserRepository userRepository = context.mock(UserRepository.class);
    private final Log log = context.mock(Log.class);
    private final BannedUserRepository bannedUserRepository = context.mock(BannedUserRepository.class);
    private final BadWordsService badWordsService = context.mock(BadWordsService.class);
    private final UrlObfuscatorService urlObfuscatorService = context.mock(UrlObfuscatorService.class);
    private final EmojiDictionary emojiDictionary = context.mock(EmojiDictionary.class);
    private final CommentRepository commentRepository = context.mock(CommentRepository.class);

    private final Blog blog = new Blog(
            userRepository,
            log,
            bannedUserRepository,
            badWordsService,
            commentRepository,
            emojiDictionary,
            urlObfuscatorService);

    @Test
    public void wrongPassword() {
        context.checking(new Expectations(){{
            allowing(userRepository).findByUsername("username");
            will(returnValue(new User("username", "correct password")));

            oneOf(log).info("wrong password for user with username username");
        }});

        blog.postComment("username", "wrong password", "cool story bro!");
    }

    @Test
    public void correctPassword_bannedUser() {
        context.checking(new Expectations(){{
            allowing(userRepository).findByUsername("bannedUser");
            will(returnValue(new User("bannedUser", "password")));

            allowing(bannedUserRepository).listBannedUsers();
            will(returnValue(asList("bannedUser")));

            oneOf(log).info("banned user bannedUser tried to comment");
        }});

        blog.postComment("bannedUser", "password", "cool story bro!");
    }

    @Test
    public void authorizedUser_messageWithBadWords() {
        context.checking(new Expectations(){{
            allowing(userRepository).findByUsername("username");
            will(returnValue(new User("username", "password")));

            allowing(bannedUserRepository).listBannedUsers();
            will(returnValue(asList("bannedUser")));

            allowing(badWordsService).listBadWords();
            will(returnValue(asList("bad", "duck")));

            oneOf(log).info("comment [this is a bad word] contains bad words");
        }});

        blog.postComment("username", "password", "this is a bad word");
    }

    @Test
    public void authorizedUser_messageWithEmojiAndUrl() {
        context.checking(new Expectations(){{
            allowing(userRepository).findByUsername("username");
            will(returnValue(new User("username", "password")));

            allowing(bannedUserRepository).listBannedUsers();
            will(returnValue(asList("bannedUser")));

            allowing(badWordsService).listBadWords();
            will(returnValue(asList("bad", "duck")));

            allowing(emojiDictionary).allEmojis();
            will(returnValue(asList(new Emoji(":)", "\uD83D\uDE00"))));

            allowing(urlObfuscatorService).obfuscateAllUrlsIn("this is a smile :) and this is an url: https://www.connexxo.it");
            will(returnValue("this is a smile :) and this is an url: OBFUSCATED_URL"));

            oneOf(commentRepository).store(new Comment(
                    new User("username", "password"),
                    "this is a smile \uD83D\uDE00 and this is an url: OBFUSCATED_URL"
            ));
        }});

        blog.postComment("username", "password", "this is a smile :) and this is an url: https://www.connexxo.it");
    }
}