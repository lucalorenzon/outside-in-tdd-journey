package it.gabrieletondi.journey.expecteverything;

public interface UserRepository {
    public User findByUsername(String username);
}
