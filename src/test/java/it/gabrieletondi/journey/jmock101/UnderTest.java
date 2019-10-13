package it.gabrieletondi.journey.jmock101;

public class UnderTest {
    private final Collaborator collaborator;

    public UnderTest(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public void doSomething(String parameter) {
        collaborator.command();
    }
}
