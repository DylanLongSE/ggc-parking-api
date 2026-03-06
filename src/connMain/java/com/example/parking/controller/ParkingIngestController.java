package com.example.parking.controller;

import com.example.parking.dto.CountIngestRequest;
import com.example.parking.dto.IngestResponse;
import com.example.parking.service.IngestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/lots")
public class ParkingIngestController {

    private final IngestService ingestService;
    private final String piSharedSecret;

    public ParkingIngestController(IngestService ingestService,
                                   @Value("${ingest.piSharedSecret}") String piSharedSecret) {
        this.ingestService = ingestService;
        this.piSharedSecret = piSharedSecret;
    }

    @PostMapping("/{lotId}/counts")
    public IngestResponse postCount(
            @PathVariable String lotId,
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody CountIngestRequest body
    ) {
        // Expect: Authorization: Bearer <secret>
        String token = null;
        if (authorization != null && authorization.toLowerCase().startsWith("bearer ")) {
            token = authorization.substring(7).trim();
        }

        if (token == null || !token.equals(piSharedSecret)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid ingestion token");
        }

        // extra sanity checks (optional)
        if (body.getOccupied() == null || body.getOccupied() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "occupied must be >= 0");
        }

        return ingestService.ingest(lotId, body.getOccupied());
    }
}