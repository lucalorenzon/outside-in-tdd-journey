package it.gabrieletondi.journey.expecteverything.it.gabrieletondi.journey.toomanycollaborators;

import java.util.List;

import static java.lang.String.format;

public class Blog {
    private final UserRepository userRepository;
    private final Log log;
    private final BannedUserRepository bannedUserRepository;
    private final BadWordsService badWordsService;
    private final CommentRepository commentRepository;
    private final EmojiDictionary emojiDictionary;

    public Blog(UserRepository userRepository,
                Log log,
                BannedUserRepository bannedUserRepository,
                BadWordsService badWordsService,
                CommentRepository commentRepository,
                EmojiDictionary emojiDictionary) {
        this.userRepository = userRepository;
        this.log = log;
        this.bannedUserRepository = bannedUserRepository;
        this.badWordsService = badWordsService;
        this.commentRepository = commentRepository;
        this.emojiDictionary = emojiDictionary;
    }

    public void postComment(String username, String password, String comment) {
        User user = loginUser(username, password);
        if (user == null) return;

        if (isUserBanned(username)) {
            log.info(format("banned user %s tried to comment", username));
            return;
        }

        if (doesCommentContainsBadWords(comment)) {
            log.info(format("comment [%s] contains bad words", comment));
            return;
        }

        commentRepository.store(new Comment(user, formatComment(comment)));
    }

    private User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (!user.getPassword().equals(password)) {
            log.info(format("wrong password for user with username %s", username));
            return null;
        }
        
        return user;
    }

    private boolean isUserBanned(String username) {
        List<String> bannedUsers = bannedUserRepository.listBannedUsers();

        return bannedUsers.contains(username);
    }

    private boolean doesCommentContainsBadWords(String comment) {
        List<String> badWords = badWordsService.listBadWords();

        for (String badWord : badWords) {
            if (comment.contains(badWord)) {
                return true;
            }
        }

        return false;
    }

    private String formatComment(String comment) {
        String formattedComment = comment;

        List<Emoji> emojis = emojiDictionary.allEmojis();

        for (Emoji emoji : emojis) {
            formattedComment = formattedComment.replace(emoji.getShortcut(), emoji.getValue());
        }

        return formattedComment;
    }
}
