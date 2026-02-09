package edu.ggc.parking.api.dto;

import java.time.Instant;

/**
 * DTO for incoming count updates from devices.
 */
public class CountUpdateRequest {
    private Instant timestamp;
    private int carCount;
    private String sourceDeviceId;
    private Double avgConfidence;

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public int getCarCount() {
        return carCount;
    }

    public void setCarCount(int carCount) {
        this.carCount = carCount;
    }

    public String getSourceDeviceId() {
        return sourceDeviceId;
    }

    public void setSourceDeviceId(String sourceDeviceId) {
        this.sourceDeviceId = sourceDeviceId;
    }

    public Double getAvgConfidence() {
        return avgConfidence;
    }

    public void setAvgConfidence(Double avgConfidence) {
        this.avgConfidence = avgConfidence;
    }
}
