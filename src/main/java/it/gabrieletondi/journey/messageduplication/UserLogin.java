package it.gabrieletondi.journey.messageduplication;

public class UserLogin {
    private final UserRepository userRepository;
    private final UserMailService userMailService;
    private final UserAudit userAudit;
    private final UserBusinessMetrics userBusinessMetrics;
    private final Session session;

    public UserLogin(UserRepository userRepository,
                     UserMailService userMailService,
                     UserAudit userAudit,
                     UserBusinessMetrics userBusinessMetrics,
                     Session session) {
        this.userRepository = userRepository;
        this.userMailService = userMailService;
        this.userAudit = userAudit;
        this.userBusinessMetrics = userBusinessMetrics;
        this.session = session;
    }

    public void execute(String username, String password) {
        User user = userRepository.userWith(username, password);

        if (user == null) {
            userMailService.notifyTentativeAccessFor(username);
            userAudit.registerInvalidLoginFor(username);
            userBusinessMetrics.incrementLoginErrorMetric();
            return;
        }

        userAudit.registerValidUserLoginFor(username);
        userBusinessMetrics.incrementSuccessfulLoginMetric();
        session.storeLoggedInUser(user);
    }
}
