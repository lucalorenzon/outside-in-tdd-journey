package it.gabrieletondi.journey.messageduplication;

public interface UserBusinessMetrics {
    void incrementLoginErrorMetric();
    void incrementSuccessfulLoginMetric();
}
