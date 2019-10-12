package it.gabrieletondi.journey.toomanycollaborators;

public interface UserRepository {
    User findByUsername(String username);
}
