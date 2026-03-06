package com.example.parking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

//class: matches the JSON returned by the function
public class CountIngestRequest {

    @NotNull
    @Min(0)
    private Integer occupied;

    // optional metadata if you want later
    private String source;
    private String timestamp;

    public Integer getOccupied() { return occupied; }
    public void setOccupied(Integer occupied) { this.occupied = occupied; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}