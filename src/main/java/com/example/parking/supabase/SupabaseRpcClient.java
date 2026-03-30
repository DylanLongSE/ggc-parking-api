package com.example.parking.supabase;

import com.example.parking.dto.UpsertResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;

@Component
public class SupabaseRpcClient {

    private final RestTemplate restTemplate;
    private final String supabaseUrl;
    private final String secretKey;

    public SupabaseRpcClient(
            RestTemplate restTemplate,
            @Value("${supabase.url}") String supabaseUrl,
            @Value("${supabase.secretKey}") String secretKey
    ) {
        this.restTemplate = restTemplate;
        this.supabaseUrl = supabaseUrl;
        this.secretKey = secretKey;
    }

    public UpsertResult upsertLotCountIfChanged(String lotId, int occupied) {
        String url = supabaseUrl + "/rest/v1/rpc/upsert_lot_count_if_changed";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Supabase REST expects BOTH
        headers.set("apikey", secretKey);
        headers.setBearerAuth(secretKey);

        // Params must match function arg names
        Map<String, Object> body = Map.of(
                "p_lot_id", lotId,
                "p_occupied", occupied,
                "p_timestamp", Instant.now().toString()
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<UpsertResult> response =
                    restTemplate.exchange(url, HttpMethod.POST, entity, UpsertResult.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new RuntimeException("Supabase RPC failed: " + response.getStatusCode());
            }
            return response.getBody();

        } catch (RestClientResponseException ex) {
            // helpful error body from Supabase
            throw new RuntimeException("Supabase RPC error: " + ex.getRawStatusCode() + " " + ex.getResponseBodyAsString(), ex);
        }
    }
}