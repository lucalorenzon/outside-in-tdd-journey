package it.gabrieletondi.journey.jmock101;

public interface Collaborator {
    int queryForSomething(String parameter);

    void command();

    void doNotCallMe();
}
