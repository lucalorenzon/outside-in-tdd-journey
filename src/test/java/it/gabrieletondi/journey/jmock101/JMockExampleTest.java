package it.gabrieletondi.journey.jmock101;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class JMockExampleTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void examples() {
        Collaborator collaborator = context.mock(Collaborator.class);
        UnderTest underTest = new UnderTest(collaborator);

        context.checking(new Expectations(){{
            allowing(collaborator).queryForSomething("a parameter");
            will(returnValue(42));

            oneOf(collaborator).command();

            never(collaborator).doNotCallMe();
        }});

        underTest.doSomething("a parameter");
    }
}
