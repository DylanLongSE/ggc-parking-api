package com.example.parking.service;

import com.example.parking.dto.IngestResponse;
import com.example.parking.dto.UpsertResult;
import com.example.parking.supabase.SupabaseRpcClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class IngestService {

    private final SupabaseRpcClient supabaseRpcClient;

    public IngestService(SupabaseRpcClient supabaseRpcClient) {
        this.supabaseRpcClient = supabaseRpcClient;
    }

    public IngestResponse ingest(String lotId, int occupied, Instant timestamp, String reason, List<Integer> occupiedIds) {
        UpsertResult result = supabaseRpcClient.upsertLotCountIfChanged(lotId, occupied, occupiedIds, reason);

        return new IngestResponse(
                result.getLotId(),
                result.getCurrent() != null ? result.getCurrent() : occupied,
                result.getPrevious(),
                Boolean.TRUE.equals(result.getWroteHistory())
        );
    }
}