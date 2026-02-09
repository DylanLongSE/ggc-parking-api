package edu.ggc.parking.api.controller;

import edu.ggc.parking.api.dto.CountUpdateRequest;
import edu.ggc.parking.api.dto.ParkingStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Controller for handling parking lot status updates and queries.
 */
@RestController
@RequestMapping("/api/v1/lots")
public class ParkingController {

    private final Map<String, LatestSnapshot> latestByLot = new ConcurrentHashMap<>();

    /**
     * Endpoint for devices to post count updates for a specific parking lot.
     *
     * @param lotId the ID of the parking lot
     * @param body  the count update request containing car count and optional timestamp
     * @return a simple status response
     */
    @PostMapping("/{lotId}/counts")
    public ResponseEntity<?> postCount(@PathVariable String lotId, @RequestBody CountUpdateRequest body) {
        Instant ts = (body.getTimestamp() != null) ? body.getTimestamp() : Instant.now();
        latestByLot.put(lotId, new LatestSnapshot(body.getCarCount(), ts));
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    /**
     * Endpoint for clients to query the current status of a parking lot.
     *
     * @param lotId the ID of the parking lot
     * @return the current parking status including car count and timestamp
     */
    @GetMapping("/{lotId}/status")
    public ResponseEntity<ParkingStatusResponse> getStatus(@PathVariable String lotId) {
        LatestSnapshot snap = latestByLot.get(lotId);

        int carCount = (snap != null) ? snap.carCount : 0;
        Instant asOf = (snap != null) ? snap.asOf : Instant.EPOCH;

        // Capacity optional for now (add later)
        Integer capacity = null;
        Double percentFull = null;

        return ResponseEntity.ok(new ParkingStatusResponse(lotId, carCount, capacity, percentFull, asOf));
    }

    private record LatestSnapshot(int carCount, Instant asOf) {
    }
}
