package com.example.parking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;

//class: matches the JSON returned by the function
public class CountIngestRequest {

    @NotNull
    @Min(0)
    private Integer occupied;
    private List<Integer> occupiedIds;
    private String reason;

    // optional metadata if you want later
    private String source;
    private Instant timestamp;

    public Integer getOccupied() { return occupied; }
    public void setOccupied(Integer occupied) { this.occupied = occupied; }

    public List<Integer> getOccupiedIds() {
        return occupiedIds;
    }

    public void setOccupiedIds(List<Integer> occupiedIds) {
        this.occupiedIds = occupiedIds;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}