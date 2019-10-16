package it.gabrieletondi.journey.messageduplication;

import java.util.Arrays;

public class CompositeUserLoginObserver implements UserLoginObserver {
    private final UserLoginObserver[] observers;

    public CompositeUserLoginObserver(UserLoginObserver... observers) {
        this.observers = observers;
    }

    @Override
    public void onLoginFailedForUsername(String username) {
        Arrays.stream(observers).forEach(it -> it.onLoginFailedForUsername(username));
    }

    @Override
    public void onUserLoggedIn(User user) {
        Arrays.stream(observers).forEach(it -> it.onUserLoggedIn(user));
    }
}
