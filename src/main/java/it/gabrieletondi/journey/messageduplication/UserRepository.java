package it.gabrieletondi.journey.messageduplication;

public interface UserRepository {
    User userWith(String username, String password);
}
