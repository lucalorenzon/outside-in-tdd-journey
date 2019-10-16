package it.gabrieletondi.journey.toomanycollaborators;

import static java.lang.String.format;

public class Blog {
    private final Log log;
    private final CommentRepository commentRepository;
    private final UserLoginService userLoginService;
    private final MessageSanitizerService messageSanitizerService;

    public Blog(Log log,
                CommentRepository commentRepository,
                UserLoginService userLoginService,
                MessageSanitizerService messageSanitizerService) {
        this.log = log;
        this.commentRepository = commentRepository;
        this.userLoginService = userLoginService;
        this.messageSanitizerService = messageSanitizerService;
    }

    public void postComment(String username, String password, String comment) {
        User user = loginUser(username, password);
        if (user == null) {
            return;
        }

        String formattedMessage = this.formatMessage(comment);
        if (formattedMessage == null) {
            return;
        }

        commentRepository.store(new Comment(user, formattedMessage));
    }

    private String formatMessage(String comment) {
        try {
            return messageSanitizerService.sanitize(comment);
        } catch (MessageContainsBadWordsException e) {
            log.info(format("comment [%s] contains bad words", comment));
            return null;
        }
    }

    private User loginUser(String username, String password) {
        try {
            return userLoginService.loginUser(username, password);
        } catch (UserWrongPasswordException e) {
            log.info(format("wrong password for user with username %s", username));
            return null;
        } catch (UserBannedException e) {
            log.info(format("banned user %s tried to comment", username));
            return null;
        }
    }

}
