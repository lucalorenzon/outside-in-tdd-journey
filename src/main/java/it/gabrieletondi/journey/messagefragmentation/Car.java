package it.gabrieletondi.journey.messagefragmentation;

public class Car {
    private final Transmission transmission;
    private final Engine engine;

    public Car(Transmission transmission, Engine engine) {
        this.transmission = transmission;
        this.engine = engine;
    }

    public void accelerate() {
        if (engine.currentRpm() >= 5000) {
            transmission.shiftUp();
        }
    }
}
