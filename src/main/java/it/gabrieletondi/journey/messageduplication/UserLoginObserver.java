package it.gabrieletondi.journey.messageduplication;

public interface UserLoginObserver {
    void onLoginFailedForUsername(String username);

    void onUserLoggedIn(User user);
}
