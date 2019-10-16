package it.gabrieletondi.journey.messageduplication;

public class UserLogin {
    private final UserRepository userRepository;
    private final UserLoginObserver observer;

    public UserLogin(UserRepository userRepository, UserLoginObserver observer) {
        this.userRepository = userRepository;
        this.observer = observer;
    }

    public void execute(String username, String password) {
        User user = userRepository.userWith(username, password);

        if (user == null) {
            observer.onLoginFailedForUsername(username);
            return;
        }

        observer.onUserLoggedIn(user);
    }
}
