package com.example.parking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpsertResult {

    @JsonProperty("lot_id")
    private String lotId;

    private Integer previous;
    private Integer current;

    @JsonProperty("wrote_history")
    private Boolean wroteHistory;

    public String getLotId() { return lotId; }
    public void setLotId(String lotId) { this.lotId = lotId; }

    public Integer getPrevious() { return previous; }
    public void setPrevious(Integer previous) { this.previous = previous; }

    public Integer getCurrent() { return current; }
    public void setCurrent(Integer current) { this.current = current; }

    public Boolean getWroteHistory() { return wroteHistory; }
    public void setWroteHistory(Boolean wroteHistory) { this.wroteHistory = wroteHistory; }
}