package it.gabrieletondi.journey.messageduplication;

public interface UserAudit {
    void registerInvalidLoginFor(String username);
    void registerValidUserLoginFor(String username);
}
