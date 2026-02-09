package edu.ggc.parking.api.dto;

import java.time.Instant;

/**
 * DTO for outgoing parking status responses.
 */
public class ParkingStatusResponse {
    private String lotId;
    private int carCount;
    private Integer capacity;
    private Double percentFull;
    private Instant asOf;

    public ParkingStatusResponse() {
    }

    public ParkingStatusResponse(String lotId, int carCount, Integer capacity, Double percentFull, Instant asOf) {
        this.lotId = lotId;
        this.carCount = carCount;
        this.capacity = capacity;
        this.percentFull = percentFull;
        this.asOf = asOf;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public int getCarCount() {
        return carCount;
    }

    public void setCarCount(int carCount) {
        this.carCount = carCount;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getPercentFull() {
        return percentFull;
    }

    public void setPercentFull(Double percentFull) {
        this.percentFull = percentFull;
    }

    public Instant getAsOf() {
        return asOf;
    }

    public void setAsOf(Instant asOf) {
        this.asOf = asOf;
    }
}