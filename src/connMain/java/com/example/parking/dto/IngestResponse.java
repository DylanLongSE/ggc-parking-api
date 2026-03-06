package com.example.parking.dto;

public class IngestResponse {
    private String lotId;
    private int occupied;
    private Integer previous;        // can be null first time
    private boolean wroteHistory;

    public IngestResponse(String lotId, int occupied, Integer previous, boolean wroteHistory) {
        this.lotId = lotId;
        this.occupied = occupied;
        this.previous = previous;
        this.wroteHistory = wroteHistory;
    }

    public String getLotId() { return lotId; }
    public int getOccupied() { return occupied; }
    public Integer getPrevious() { return previous; }
    public boolean isWroteHistory() { return wroteHistory; }
}