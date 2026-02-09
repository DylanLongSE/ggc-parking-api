package edu.ggc.parking.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    /**
     * Simple endpoint to verify the service is running.
     *
     * @return "pong" if the service is up
     */
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
