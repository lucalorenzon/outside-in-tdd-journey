package it.gabrieletondi.journey.messagefragmentation;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class CarTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final Engine engine = context.mock(Engine.class);
    private final Transmission transmission = context.mock(Transmission.class);

    private final Car car = new Car(transmission, engine);

    @Test
    public void acceleratingAbove5000RPM() {
        context.checking(new Expectations(){{
            allowing(engine).currentRpm();
            will(returnValue(5000));

            oneOf(transmission).shiftUp();
        }});

        car.accelerate();
    }

    @Test
    public void acceleratingBelow5000RPM() {
        context.checking(new Expectations(){{
            allowing(engine).currentRpm();
            will(returnValue(4000));

            never(transmission);
        }});

        car.accelerate();
    }
}